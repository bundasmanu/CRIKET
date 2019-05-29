/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BridgeLogicController.BridgeLocal;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import cricketdto.GoalDTO;
import cricketdto.RankDTO;
import cricketdto.UserDTO;
import entities.Goal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import utils.Utils;

/**
 *
 * @author bruno
 */
@Named("rankBean")
@SessionScoped
public class RankBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String nome;
    private String descript;
    private String minPoints;
    private String birthTmp;
    private int gender;
    UserDTO user;

    @EJB
    private BridgeLocal bridge;

    private RankDTO rankDTOTemp;

    @Inject
    GoalBean gg;

    @Inject
    SessionBean s;

    public RankBean() {
        // do nothing
    }

    public String processFindRank() {
        String str = "";
        String nova_string = "";
        try {
            str = bridge.getCricket().findRankUser(this.s.getEmail());
            if (str.equals("Beginner")) {
                return "https://img.icons8.com/color/48/000000/1-circle.png";

            } else if (str.equals("Amador")) {
                return "https://img.icons8.com/color/48/000000/2.png";
            } else if (str.equals("Intermedio")) {
                return "https://img.icons8.com/color/48/000000/3.png";
            } else if (str.equals("Profissional")) {
                return "https://img.icons8.com/color/48/000000/4-circle-c.png";
            }
            else if(str.equals("Expert")){
                return "https://img.icons8.com/color/48/000000/5-c.png";
            }

            return "";
        } catch (Exception ex) {
            Utils.throwMessage("Error");
            return "error";
        }

    }

    public String returnTypeOfRank() {
        String str = "";
        try {
            str = bridge.getCricket().findRankUser(this.s.getEmail());
            return str;
        } catch (Exception ex) {
            Utils.throwMessage("Error");
            return "error";
        }
    }

    public int getPointsForGoal(GoalDTO g) {

        Goal goal = bridge.getCricket().getGoalByDtoID(g.getId_goal());

        List<Goal> similarGoalsList = bridge.getCricket().getListSameGoals(goal);

        int strikValue = this.bridge.getCricket().getStrike(similarGoalsList);

        int point = bridge.getCricket().definePont(goal, strikValue);

        return point;
    }

}
