/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BridgeLogicController.BridgeLocal;
import Utils.Config;
import cricketdto.GoalDTO;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "goalBean")
@ManagedBean
@SessionScoped
public class GoalBean implements Serializable{

    @EJB
    BridgeLocal bridge;
    
    
    public GoalBean() {
    }
    
    public List<GoalDTO> getAllGoals() {
        try {
            List<GoalDTO> goals = new ArrayList();
            
            GoalDTO goal = new GoalDTO();
            goal.setName("Goal example");
            goal.setDesc("goal desc");
            goal.setFinalValue(12);
            goal.setTotalValue(20);
            goal.setType(Config.POSITIVE);
            goal.setStatus(Config.RECURRENT);            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
            Date finalDate = formatter.parse("17/04/2019");
            goal.setFinalDate(finalDate);
            
            goals.add(goal);
            goals.add(goal);

            return goals;
        } catch (Exception ex) {
            return new ArrayList();
        }
    }
    
    
}
