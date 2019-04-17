/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.GoalDTO;
import entities.Category;
import entities.DTOFactory;
import entities.Goal;
import entities.Utilizador;
import facades.UtilizadorFacadeLocal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author gustavo
 */
@Singleton
public class goalManagement implements goalManagementLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    UtilizadorFacadeLocal ut;
    
    private DTOFactory dt= new DTOFactory();
    
    @Override
    public List<GoalDTO> selectAllGoalsFromAnUser(String email){
        
        /*VERIFICAR INICIALMENTE SE O USER EXISTE*/
        Utilizador u=this.ut.findByEmail(email);
        
        if(u==null){
            return null;
        }
        
        /*COMO O UTILIZADOR EXISTE BASTA RETORNAR TODOS OS SEUS OBJETIVOS*/
        Collection<Category> catUser = u.getCategoryCollection();

        List<GoalDTO> retorno_goals_user=new ArrayList<GoalDTO>();
        
        if (catUser.isEmpty() != true) {
            for (Category x : catUser) {
                if (x.getGoalCollection().isEmpty() != true) {
                    for(Goal g : x.getGoalCollection()){
                        GoalDTO gt=dt.getGoalDTO(g);
                        retorno_goals_user.add(gt);
                    }
                }
            }
        }
        
        /*ORDENACAO DOS GOALS CONSOANTE, A FLAG, E DE ACORDO COM O COMPARABLE*/
        Collections.sort(retorno_goals_user);
        
        return retorno_goals_user;
    }
    
}
