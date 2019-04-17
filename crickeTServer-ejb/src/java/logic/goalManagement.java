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
import Utils.Config;
import cricketdto.GoalDTO;
import entities.Goal;
import facades.GoalFacadeLocal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author gustavo
 */
@Singleton
public class goalManagement implements goalManagementLocal {

    @EJB
    GoalFacadeLocal goal;

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
    
    @Override
    public boolean CreateGoal(GoalDTO new_goal) {

        try {
            
            //verify if this goal exists with the same name
            Goal g = this.goal.findByName(new_goal.getName());

            if (g != null) {
                return false;
            }

            //initially, the value of the goal in the creation is 0
            int atual_value = 0;
            g.setCurrentvalue(atual_value);

            if (new_goal.getType().equals(Config.POSITIVE)) {
                //if goal doesn't exist, so let's create an instance of the entity Goal
                g = new Goal(new_goal.getId_goal(), new_goal.getName(), new_goal.getDesc(), new_goal.getType(), new_goal.getStatus(),
                        new_goal.getFinalDate(), new_goal.getTotalValue(), new_goal.getCurrentValue(),
                        new_goal.getFavorite(), new_goal.getLogDate(), new_goal.getFlagClick(), new_goal.getFlag_order());
            } else if (new_goal.getType().equals(Config.NEGATIVE)) {
                //if goal doesn't exist, so let's create an instance of the entity Goal
                g = new Goal(new_goal.getId_goal(), new_goal.getName(), new_goal.getDesc(), new_goal.getType(), new_goal.getStatus(),
                        new_goal.getFinalDate(), new_goal.getTotalValue(), new_goal.getCurrentValue(),
                        new_goal.getFavorite(), new_goal.getLogDate(), new_goal.getFlagClick(), new_goal.getFlag_order());
            }

            //persist on database the respective goal
            this.goal.create(g);

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }
        return true;
    }
}
