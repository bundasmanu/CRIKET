/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Ranking;
import facades.RankingFacadeLocal;
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
    
}
