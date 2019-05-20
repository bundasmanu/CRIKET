/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BridgeLogicController.BridgeLocal;
import Utils.Config;
import static com.oracle.wls.shaded.org.apache.xalan.lib.ExsltDatetime.date;
import cricketdto.GoalDTO;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.String.format;
import static java.lang.String.format;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.AsyncResult;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import utils.Utils;

@Named(value = "goalBean")
@ManagedBean
@SessionScoped
public class GoalBean implements Serializable {

    @EJB
    BridgeLocal bridge;

    private String actualDate;
    private GoalDTO goalDTOTemp;
    Future<Integer> nextValueOrderGoal;
    Future<Integer> idGoal;

    @Inject
    SessionBean su;

    String finalDateGoalTmp;

    @PostConstruct
    private void init() {
        this.goalDTOTemp = new GoalDTO();
    }

    public GoalBean() {
    }

    public List<GoalDTO> getAllNotDoneGoals() {
        try {
            return this.bridge.getCricket().selectAllNotDoneGoalsFromAnUser(this.su.getEmail());
        } catch (Exception ex) {
            return new ArrayList();
        }
    }

    public List<GoalDTO> getAllDoneGoals() {
        try {
            return this.bridge.getCricket().selectAllDoneGoalsFromAnUser(this.su.getEmail());

        } catch (Exception ex) {
            return new ArrayList();
        }
    }

    public String addGoal() {
        this.goalDTOTemp = new GoalDTO();
        this.finalDateGoalTmp = "";
        return "createGoal";
    }

    public String processAddGoal() {
        boolean result = false;

        try {
            //convert the selected date from user (if he defined)
            if (!finalDateGoalTmp.isEmpty()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date finalDateGoal = formatter.parse(this.finalDateGoalTmp);
                //actual date
                LocalDate dateToday = LocalDate.now();
                java.util.Date dateTodayConverted = new SimpleDateFormat("yyyy-MM-dd").parse(dateToday.toString());

                if (finalDateGoal.compareTo(dateTodayConverted) >= 0) { 
                    goalDTOTemp.setFinalDate(finalDateGoal);
                } else {
                    throw new Exception("Date need to be greater than current date");
                }

            } else {
                finalDateGoalTmp = null;
                goalDTOTemp.setFinalDate(null);
            }

            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String formattedString = LocalDate.now().format(formatterLocalDate);
            //LocalDate actualDate = LocalDate.now();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date actualDate = formatter.parse(formattedString);

            goalDTOTemp.setLogDate(actualDate);

            //define the value of the flag_order
            goalDTOTemp.setFlag_order(nextValueOrderGoal.get());

            //add goal
            result = bridge.getCricket().addGoal(goalDTOTemp);

            if (result) {
                //generate new value of flag order
                FacesContext fc = FacesContext.getCurrentInstance();
                String email = (String) fc.getExternalContext().getSessionMap().get("user");
                nextValueOrderGoal = this.bridge.getCricket().getNextValueFromGoalOrder(email);

                /*ATUALIZAR NOVO VALOR DO ID, PARA QUE SEJA POSSIVEL ADICIONAR NOVO GOAL DPS*/
                this.idGoal = this.bridge.getCricket().getNextValueGoal(this.su.getEmail());
                this.goalDTOTemp = new GoalDTO();
                this.finalDateGoalTmp = "";
                //Utils.throwMessage("Success Adding the New Goal");
                return "dashboard?faces-redirect=true?";
            } else {
                Utils.throwMessage("Error");
                return "createGoal";
            }

        } catch (Exception ex) {
            Utils.throwMessage("Error: " + ex);
            System.out.println("ERROR: " + ex);
            return "createGoal";
        }
    }

    public String processRemoveGoal(int idGoalSelected) throws InterruptedException, ExecutionException {
        boolean result = false;

        System.out.println("" + goalDTOTemp);

        //atenção ,para removerem têm por enquanto de colocar o id_goal que pretendem remover à "mão"
        //depois mudo isto para o método do getidgoal().
        //vou ver como faço o reload para ele remover automaticamente 
        result = bridge.getCricket().removeGoal(this.su.getEmail(), idGoalSelected);
        if (result) {

            //Utils.throwMessage("Success Adding the New Goal");
            return "/index?faces-redirect=true?";

        } else {
            Utils.throwMessage("Error");
            return "dashboard";
        }

    }

    public String editGoal(int idGoal) {

        goalDTOTemp = bridge.getCricket().findGoalDTOById(idGoal);
        
        if (goalDTOTemp == null) {
            Utils.throwMessage("Error. Couln't find the goal.");
            return "dashboard";
        }

        if (goalDTOTemp.getFinalDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            finalDateGoalTmp = formatter.format(goalDTOTemp.getFinalDate());
        }

        return "editGoal";
    }

    public String processEditGoal() {

        boolean result = false;

        try {
            //convert the selected date from user (if he defined)
            if (!finalDateGoalTmp.isEmpty()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date finalDateGoal = formatter.parse(finalDateGoalTmp);
                goalDTOTemp.setFinalDate(finalDateGoal);
            } else {
                goalDTOTemp.setFinalDate(null);
            }

            result = bridge.getCricket().editGoal(goalDTOTemp);
            if (result) {
                this.goalDTOTemp = new GoalDTO();
                this.finalDateGoalTmp = "";
                return "/dashboard?faces-redirect=true?";
            } else {
                Utils.throwMessage("Error");
                return "editGoal";
            }

        } catch (Exception ex) {
            Utils.throwMessage("Error");
            return "editGoal";
        }

    }

    public String increaseCurrentValue(GoalDTO goalDTO) {
        boolean result = bridge.getCricket().increaseCurrentValue(goalDTO);

        if (!result) {
            Utils.throwMessage("Error incrementing the current value of the selected goal.");
        }
        return "dashboard";
    }

    public String decreaseCurrentValue(GoalDTO goalDTO) {
        boolean result = bridge.getCricket().decreaseCurrentValue(goalDTO);

        if (!result) {
            Utils.throwMessage("Error decreasing the current value of the selected goal.");
        }
        return "dashboard";
    }

    public String goalWasEnddedSucessfully(GoalDTO goalDTO) {
        boolean result = bridge.getCricket().goalIsEnd(goalDTO);

        if (result && goalDTO.getStatus().equals(Config.POSITIVE)) {
            return "background-color: " + Config.BACKGROUND_SUCCESS_COLOR_GOAL + ";";
        } else if (result && goalDTO.getStatus().equals(Config.NEGATIVE)) {
            return "background-color: " + Config.BACKGROUND_UNSUCCESS_COLOR_GOAL + ";";
        } else {
            return "bg-light text-dark";
        }
    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
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

    public String processRecoverDoneGoal(int idGoalSelected) throws InterruptedException, ExecutionException {
        boolean result = false;

        result = bridge.getCricket().recoveryDoneGoal(idGoalSelected);
        if (result) {

            //Utils.throwMessage("Success Adding the New Goal");
            return "/indexHistory?faces-redirect=true?";

        } else {
            Utils.throwMessage("Error");
            return "indexHistory";
        }

    }

}
