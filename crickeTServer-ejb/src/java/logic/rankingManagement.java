/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Goal;
import entities.Ranking;
import entities.Utilizador;
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
    
    public int getDailyStrike(Goal g){
        
        try{
            
            if(g==null){
                return -1;
            }
            
            /*VAI BUSCAR TODOS OS GOALS, IGUAIS A ESTE*/
            List<Goal> similarGoals=this.goal.getGoalsWithSameNameAndLogdate(g);
            
            if(similarGoals==null){
                return -1;
            }
            
            int length=similarGoals.size();
            
            if(similarGoals.isEmpty()==true){
                if(g.getStatus().equals("POSITIVE")==true){
                    if(g.getCurrentvalue()>=g.getTotalvalue()){
                        /*DA PONTOS*/
                    }
                    else{
                        /*RETIRA PONTOS*/
                    }
                }
                else{/*NEGATIVOS*/
                    if (g.getCurrentvalue() < g.getTotalvalue()) {
                        /*DA PONTOS*/
                    } else {
                        /*RETIRA PONTOS*/
                    }
                }

            }
            
           
           /*SO PARA TIRAR O ERRO*/
           return 500; 
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
    public int definePoints(Goal g){
        
        try{
            
            int valueStrike=this.getDailyStrike(g);
            
            if(valueStrike==-1){
                return -1;
            }
            
            if(g.getStatus().equals("POSITIVE")==true){
                
                if(g.getFrequency().equals("DAILY")==true){
                    return 1*valueStrike;
                }
                if(g.getFrequency().equals("WEEKLY")==true){
                    return 2*valueStrike;
                }
                else if(g.getFrequency().equals("MONTHLY")==true){
                    return 3*valueStrike;
                }
                else{
                    return 4*valueStrike;
                }
            }
            else{ /*SE FOR NEGATIVO*/
                
                if(g.getFrequency().equals("DAILY")==true){
                    return 1*valueStrike;
                }
                else if(g.getFrequency().equals("WEEKLY")==true){
                    return 2*valueStrike;
                }
                else if(g.getFrequency().equals("MONTHLY")==true){
                    return 3*valueStrike;
                }
                else{
                    return 4*valueStrike;
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
                if(goals.get(i).getCurrentvalue()< goals.get(i).getTotalvalue()){
                    lastIndex=i;
                }
            }
            
            if(length==lastIndex){
                return 1;
            }
            
            int pointsBetweenLastIndex=length-lastIndex;
            
            return lastIndex;
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
                if(goals.get(i).getCurrentvalue()>= goals.get(i).getTotalvalue()){
                    lastIndex=i;
                }
            }
            
            int pointsBetweenLastIndex=length-lastIndex;
            
            return lastIndex;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
}
