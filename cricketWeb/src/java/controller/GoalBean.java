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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import utils.Utils;

@Named(value = "goalBean")
@ManagedBean
@SessionScoped
public class GoalBean implements Serializable{

    @EJB
    BridgeLocal bridge;
    
    private GoalDTO goalDTOTemp;
    Future<Integer> nextValueOrderGoal;
    
    @Inject
    SessionBean su;
    
    String finalDateGoalTmp;
    
    @PostConstruct
    private void init() {
        this.goalDTOTemp = new GoalDTO();
    }
  
    public GoalBean() {
    }
    
    public List<GoalDTO> getAllGoals() {
        try {
//            List<GoalDTO> goals = new ArrayList();
//            
//            GoalDTO goal = new GoalDTO();
//            goal.setName("Goal example");
//            goal.setDesc("goal desc");
//            goal.setCurrentValue(12);
//            goal.setTotalValue(20);
//            goal.setType(Config.POSITIVE);
//            goal.setStatus(Config.RECURRENT);            
//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
//            Date finalDate = formatter.parse("17/04/2019");
//            goal.setFinalDate(finalDate);
//           
//            /*EXEMPLO DE UTILIZACAO DESTE VALOR*/
//            //goal.setFlag_order(nextValueOrderGoal.get());
//            
//            goals.add(goal);
//            goals.add(goal);
//
//            return goals;

           return this.bridge.getCricket().selectAllGoalsFromAnUser(this.su.getEmail());


        } catch (Exception ex) {
            return new ArrayList();
        }
    }
    
    public String processAddGoal()
    {
        boolean result = false;
           
        try {
        
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
            Date finalDateGoal;
            finalDateGoal = formatter.parse(this.finalDateGoalTmp);
             
            goalDTOTemp.setFinalDate(finalDateGoal);
            
            Date logDate = Date.from(Instant.now());
            goalDTOTemp.setLogDate(logDate);
            
        
            System.out.println("" + goalDTOTemp);


            result = bridge.getCricket().addGoal(goalDTOTemp);

            if(result)
            {
                //Utils.throwMessage("Success Adding the New Goal");
                return "dashboard?faces-redirect=true";
            }
            else
            {
                Utils.throwMessage("Error");
                return "createGoal";
            }

        } catch (ParseException ex) {
            Logger.getLogger(GoalBean.class.getName()).log(Level.SEVERE, null, ex);
            Utils.throwMessage("Error");
            return "createGoal";
        }
    }

    public GoalDTO getGoalDTOTemp() {
        return goalDTOTemp;
    }

    public void setGoalDTOTemp(GoalDTO goalDTOTemp) {
        this.goalDTOTemp = goalDTOTemp;
    }

    public String getFinalDateGoalTmp() {
        return finalDateGoalTmp;
    }

    public void setFinalDateGoalTmp(String finalDateGoalTmp) {
        this.finalDateGoalTmp = finalDateGoalTmp;
    }
    
}
