/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

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
