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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
           return this.bridge.getCricket().selectAllGoalsFromAnUser(this.su.getEmail());

        } catch (Exception ex) {
            return new ArrayList();
        }
    }
    
    public String processAddGoal()
    {
        boolean result = false;
           
        try {
            //convert the date from user
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
            Date finalDateGoal;
            finalDateGoal = formatter.parse(this.finalDateGoalTmp);
            
            goalDTOTemp.setFinalDate(finalDateGoal);
            
            Date logDate = Date.from(Instant.now());
            goalDTOTemp.setLogDate(logDate);
            
            //generate new value of flag order
            FacesContext fc = FacesContext.getCurrentInstance();
            String email = (String) fc.getExternalContext().getSessionMap().get("user");
            nextValueOrderGoal=this.bridge.getCricket().getNextValueFromGoalOrder(email);
            
            //define the value of the flag_order
            goalDTOTemp.setFlag_order(nextValueOrderGoal.get());
            
            //add goal
            result = bridge.getCricket().addGoal(goalDTOTemp);
            
            System.out.println("" + goalDTOTemp);
            
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

        } catch (Exception ex)
        {
            Utils.throwMessage("Error: " + ex);
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
