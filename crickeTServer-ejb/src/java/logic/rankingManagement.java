/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Goal;
import entities.Ranking;
import entities.Utilizador;
import facades.CategoryFacadeLocal;
import facades.GoalFacadeLocal;
import facades.RankingFacadeLocal;
import facades.UtilizadorFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import Utils.*;
import cricketdto.*;

/**
 *
 * @author gustavo
 */
@Singleton
public class rankingManagement implements rankingManagementLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    RankingFacadeLocal rankL;
    
    @EJB
    UtilizadorFacadeLocal ut;
    
    @EJB
    GoalFacadeLocal goal;
    
    @EJB
    CategoryFacadeLocal cat;
    
    @EJB
    trophyManagementLocal trophys;
    
    @Override
    public boolean createRanking(String name, int minP){
        
        try{
            
            /*VERIFICAR SE O RANKING JA EXISTE*/
            Ranking ret_r=this.rankL.findByName(name);
            
            /*SE JA EXISTE RETORNA LOGO*/
            if(ret_r!=null){
                return false;
            }
            
            /*SENAO EXISTE, CRIA*/
            ret_r=new Ranking(name, "", minP);
            this.rankL.create(ret_r);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }

    @Override
    public String findRankUser(String email) {
       try{
           //verifica o user logado
           Utilizador u= this.ut.findByEmail(email);
           if(u==null){
               return "";
           }
           
           Ranking rank= this.rankL.findByName(u.getIdRank().getNome());
           if(rank==null){
               return "";
           }
           return rank.getNome();
       }catch(Exception e){
           System.out.println(""+e.getMessage());
           return "";
       }
    }
    
    public boolean changeRankOfAnUser(Utilizador u){
        
        try{
            
            List<Ranking> allRank=this.rankL.findAll();
            
            if(allRank==null){
                return false;
            }
            
            Ranking userRank=u.getIdRank();
            
            for(int i=allRank.size()-1;i>=0;i--){
                if(u.getCurrentpoints()>=allRank.get(i).getMinpoints() &&  allRank.get(i)!=userRank){
                    u.setIdRank(allRank.get(i));
                    return true;
                }
            }
            
            return false;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public boolean editUserRank(Utilizador u){
        
        try{
            
            if(u==null){
                return false;
            }
            
            this.ut.edit(u);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean getDailyStrike(Goal g){
        
        try{
            
            if(g==null){
                return false;
            }
            
            /*VAI BUSCAR TODOS OS GOALS, IGUAIS A ESTE*/
            List<Goal> similarGoals=this.goal.getGoalsWithSameNameAndLogdate(g);
            
            if(similarGoals==null){
                return false;
            }
                 
            int strikeValue=0;
            
            /*OBTEM VALOR DE STRIKE*/
            strikeValue=this.getStrikeValue(similarGoals);
            
            int numberPointsWin;
            
            System.out.println("Strike value: " + strikeValue);
            
            if(strikeValue==0){/*NAO CUMPRIU*/
                if(g.getStatus().equals(Config.POSITIVE)==true){
                    g.getIdCategory().getIdUser().setCurrentpoints(g.getIdCategory().getIdUser().getCurrentpoints()+0);/*SO PARA O PESSOAL PERCEBER*/
                }
                else{
                    if(g.getIdCategory().getIdUser().getCurrentpoints()>0)
                        g.getIdCategory().getIdUser().setCurrentpoints(g.getIdCategory().getIdUser().getCurrentpoints()+(this.definePont(g,-1)));/*MEDIANTE A FREQUENCIA DESSE OBJETIVO RETIRA OS SEUS PONTOS*/
                }
            }   
            else{/*CASO HAJA STRIKE DE PELO MENOS UM APLICA O AUMENTO DOS SEUS PONTOS*/
                numberPointsWin=this.definePont(g, strikeValue);
                g.getIdCategory().getIdUser().setCurrentpoints(g.getIdCategory().getIdUser().getCurrentpoints()+numberPointsWin);
            }
            
            /*VERIFICACAO DOS TROFEUS*/
            this.checkCompleteTrophies(g, strikeValue);
            
            this.changeRankOfAnUser(g.getIdCategory().getIdUser());
            
            /*EDICAO DAS VARIAS ENTIDADES*/
            
            this.goal.edit(g);
            this.cat.edit(g.getIdCategory());
            this.ut.edit(g.getIdCategory().getIdUser());
            
            System.out.println("\nNAO ESTOIROU EDICAO ENTIDADES\n");
            
           /*SO PARA TIRAR O ERRO*/
           return true; 
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public int definePont(Goal g, int value){
        
        try{

            if (g.getFrequency().equals("DAILY") == true || g.getFrequency().equals(Config.NEVER) == true) {
                return 1 * value;
            }
            if (g.getFrequency().equals("WEEKLY") == true) {
                return 2 * value;
            } else if (g.getFrequency().equals("MONTHLY") == true) {
                return 3 * value;
            } else { /*YEARLY*/
                return 4 * value;
            }
         
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
    public int getStrikeValue(List<Goal> goals){
        
        try{
            
            /*LISTA DE GOLOS*/
            
            int length=goals.size();
            int lastIndex=length;
            
            System.out.println("\nDENTRO STRIKE VALUE 1\n");
            
            for(int i=length-1;i>=0; i--){
                
                if(goals.get(i).getCurrentvalue()< goals.get(i).getTotalvalue() && goals.get(i).getStatus().equals(Config.POSITIVE)){/*ULTIMA VEZ QUE NAO CUMPRIU*/        
                    lastIndex=i;
                }
                if (goals.get(i).getCurrentvalue() >= goals.get(i).getTotalvalue() && goals.get(i).getStatus().equals(Config.NEGATIVE)) {/*ULTIMA VEZ QUE CUMPRIU*/ 
                    lastIndex = i;
                }
            }
            
            System.out.println("\nDENTRO STRIKE VALUE 2\n");
            
            if((length-1)==lastIndex){/*CASO NAO CUMPRA O ULTIMO*/
                return -1;
            }
            
            System.out.println("\nDENTRO STRIKE VALUE 2.2\n");

            
            int pointsBetweenLastIndex=length-lastIndex;
            
            if(pointsBetweenLastIndex==0){/*SE CUMPRE APENAS O ULTIMO*/
                return 1;
            }
            
            System.out.println("\nDENTRO STRIKE VALUE 3\n");
            
            return pointsBetweenLastIndex;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
    public boolean checkCompleteTrophies(Goal g, int strikeValue){
        
        try{
            
            /*GET ALL TROPHIES FROM AN USER*/
            List<TrophyDTO> trophiesUser=this.trophys.getAllTrophiesFromUser(g.getIdCategory().getIdUser().getEmail());
            
            if(g.getFrequency().equals(Config.NEVER)==true){
                if(strikeValue==1 && trophiesUser.contains(new TrophyDTO(Config.TROPHY1, Config.TROPHY1DESC))==false)/*override do equals com base no nome*/{
                    this.trophys.createTrophy(g.getIdCategory().getIdUser().getEmail(), new TrophyDTO(Config.TROPHY1, Config.TROPHY1DESC));
                }
            }
            if(g.getFrequency().equals(Config.DAILY)==true){
                if(strikeValue==1 && trophiesUser.contains(new TrophyDTO(Config.TROPHY2, Config.TROPHY2DESC))==false)/*override do equals com base no nome*/{
                    this.trophys.createTrophy(g.getIdCategory().getIdUser().getEmail(), new TrophyDTO(Config.TROPHY2, Config.TROPHY2DESC));
                }
            }
            if(g.getFrequency().equals(Config.NEVER)==false){
                if(strikeValue==2 && trophiesUser.contains(new TrophyDTO(Config.TROPHY3, Config.TROPHY3DESC))==false)/*override do equals com base no nome*/{
                    this.trophys.createTrophy(g.getIdCategory().getIdUser().getEmail(), new TrophyDTO(Config.TROPHY3, Config.TROPHY3DESC));
                }
            }
            if(g.getFrequency().equals(Config.NEVER)==false){
                if(strikeValue==3 && trophiesUser.contains(new TrophyDTO(Config.TROPHY3, Config.TROPHY3DESC))==false)/*override do equals com base no nome*/{
                    this.trophys.createTrophy(g.getIdCategory().getIdUser().getEmail(), new TrophyDTO(Config.TROPHY3, Config.TROPHY3DESC));
                }
            }
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public String verifyRank(String email,String rank) {
        String nome_rank="";
        Utilizador u= this.ut.findByEmail(email);
        if(u==null){
            return null;
        }
        
        Ranking r= this.rankL.findByName(rank);
        if(r==null){
            return "erro";
        }
        
        if(r.getNome().equals("Begginer")){
            nome_rank="<img src=\"https://img.icons8.com/metro/26/000000/numerical-sorting-21.png\"/>:Begginer";
        }else if(r.getNome().equals("Amador")){
            nome_rank="<img src=\"https://img.icons8.com/color/48/000000/numerical-sorting-21.png\"/>Amador";
        }
        else if(r.getNome().equals("Intermedio")){
           nome_rank="Intermedio";
        }
        else if(r.getNome().equals("Expert")){
            nome_rank="Expert";
        }
        
        return nome_rank;
    }
    
}
