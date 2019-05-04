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
    
    public boolean verifyIncreaseRankOfAnUser(Utilizador u){
        
        try{
            
            List<Ranking> allRank=this.rankL.findAll();
            
            if(allRank==null){
                return false;
            }
            
            Ranking userRank=u.getIdRank();
            
            if(u.getCurrentpoints()>=userRank.getMinpoints()){
                for(Ranking r : allRank){
                    if(r.getMinpoints()<u.getCurrentpoints() && r!=userRank){
                        u.setIdRank(r);
                    }
                }
            }
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public boolean verifyDescreaseRankOfAnUser(Utilizador u){
        
        try {

            List<Ranking> allRank = this.rankL.findAll();

            if (allRank == null) {
                return false;
            }

            Ranking userRank = u.getIdRank();

            if (u.getCurrentpoints() < userRank.getMinpoints()) {
                for (Ranking r : allRank) {
                    if (r.getMinpoints() > u.getCurrentpoints() && r!=userRank) {
                        u.setIdRank(r);
                        break;
                    }
                }
            }

            return true;
        } catch (Exception e) {
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
            
            if(g.getStatus().equals("POSITIVE")==true){
                strikeValue=this.getPositiveStrikeValue(similarGoals);
            }
            else{
                strikeValue=this.getNegativeStrikeValue(similarGoals);
            }
            
            int numberPointsWin;
            
            if(strikeValue==0){/*NAO CUMPRIU*/
                if(g.getStatus().equals("POSITIVE")==true){
                    g.getIdCategory().getIdUser().setCurrentpoints(g.getIdCategory().getIdUser().getCurrentpoints()+0);
                }
                else{
                    g.getIdCategory().getIdUser().setCurrentpoints(g.getIdCategory().getIdUser().getCurrentpoints()-(this.definePont(g,-1)));/*MEDIANTE A FREQUENCIA DESSE OBJETIVO RETIRA OS SEUS PONTOS*/
                    this.verifyDescreaseRankOfAnUser(g.getIdCategory().getIdUser());
                }
            }
            else{/*CASO HAJA STRIKE DE PELO MENOS UM APLICA O AUMENTO DOS SEUS PONTOS*/
                numberPointsWin=this.definePont(g, strikeValue);
                g.getIdCategory().getIdUser().setCurrentpoints(g.getIdCategory().getIdUser().getCurrentpoints()+numberPointsWin);
                this.verifyIncreaseRankOfAnUser(g.getIdCategory().getIdUser());
            }
            
            /*EDICAO DAS VARIAS ENTIDADES*/
            
            this.goal.edit(g);
            this.cat.edit(g.getIdCategory());
            this.ut.edit(g.getIdCategory().getIdUser());
            
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
            
            if(g.getStatus().equals("POSITIVE")==true){
                
                if(g.getFrequency().equals("DAILY")==true){
                    return 1*value;
                }
                if(g.getFrequency().equals("WEEKLY")==true){
                    return 2*value;
                }
                else if(g.getFrequency().equals("MONTHLY")==true){
                    return 3*value;
                }
                else{
                    return 4*value;
                }
            }
            else{ /*SE FOR NEGATIVO*/
                
                if(g.getFrequency().equals("DAILY")==true){
                    return 1*value;
                }
                else if(g.getFrequency().equals("WEEKLY")==true){
                    return 2*value;
                }
                else if(g.getFrequency().equals("MONTHLY")==true){
                    return 3*value;
                }
                else{
                    return 4*value;
                }
                
            }
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
    public int getPositiveStrikeValue(List<Goal> goals){
        
        try{
            
            /*LISTA DE GOLOS*/
            
            int length=goals.size();
            int lastIndex=length;
            
            for(int i=length-1;i>=0; --i){
                if(goals.get(i).getCurrentvalue()< goals.get(i).getTotalvalue()){/*ULTIMA VEZ QUE NAO CUMPRIU*/
                    lastIndex=i;
                }
            }
            
            if((length-1)==lastIndex){/*CASO NAO CUMPRA O ULTIMO*/
                return 0;
            }
            
            int pointsBetweenLastIndex=length-lastIndex;
            
            if(pointsBetweenLastIndex==0){/*SE CUMPRE APENAS O ULTIMO*/
                return 1;
            }
            
            return pointsBetweenLastIndex;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
     public int getNegativeStrikeValue(List<Goal> goals){
        
        try{
            
            /*LISTA DE GOLOS*/
            
            int length=goals.size();
            int lastIndex=length;
            
            for(int i=length-1;i>=0; --i){
                if(goals.get(i).getCurrentvalue()>= goals.get(i).getTotalvalue()){/*ULTIMA VEZ QUE CUMPRIU*/
                    lastIndex=i;
                }
            }
            
            if ((length - 1) == lastIndex) {
                return 0;
            }
            
            int pointsBetweenLastIndex=length-lastIndex;
            
            if (pointsBetweenLastIndex == 0) {/*SE CUMPRE APENAS O ULTIMO*/
                return 1;
            }
            
            return lastIndex;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
}
