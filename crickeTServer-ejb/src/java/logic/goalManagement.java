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
import facades.CategoryFacadeLocal;
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

    @EJB
    categoryManagementLocal categoryManagement;
    
    @EJB
    CategoryFacadeLocal ca;
    
    private DTOFactory dt= new DTOFactory();
    
    @Override
    public List<GoalDTO> selectAllGoalsFromAnUser(String email) {

        /*VERIFICAR INICIALMENTE SE O USER EXISTE*/
        Utilizador u = this.ut.findByEmail(email);

        if (u == null) {
            return null;
        }

        /*COMO O UTILIZADOR EXISTE BASTA RETORNAR TODOS OS SEUS OBJETIVOS*/
        Collection<Category> catUser = u.getCategoryCollection();

        List<GoalDTO> retorno_goals_user = new ArrayList<GoalDTO>();

        if (catUser.isEmpty() != true) {
            for (Category x : catUser) {
                if (x.getGoalCollection().isEmpty() != true) {
                    for (Goal g : x.getGoalCollection()) {
                        GoalDTO gt = dt.getGoalDTO(g);
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
    public boolean createGoal(GoalDTO newGoalDTO) {

        try {
            //verify if this goal exists with the same name
            Goal goalTmp = this.goal.findByName(newGoalDTO.getName());
            Category cTmp=this.ca.find(newGoalDTO.getIdCategory());
            
            if (goalTmp != null || cTmp==null) {
                return false;
            }

            Goal newGoal = new Goal();
            newGoal.setCurrentvalue(newGoalDTO.getCurrentValue());
            newGoal.setDescript(newGoalDTO.getDesc());
            newGoal.setFavorite(newGoalDTO.getFavorite());
            newGoal.setFinaldate(newGoalDTO.getFinalDate());
            newGoal.setLogdate(newGoalDTO.getLogDate());
            newGoal.setFlagClickControl(newGoalDTO.getFlagClick());
            newGoal.setFlagOrder(newGoalDTO.getFlag_order());
            newGoal.setNome(newGoalDTO.getName());
            newGoal.setStatus(newGoalDTO.getStatus());
            newGoal.setTipo(newGoalDTO.getType());
            newGoal.setTotalvalue(newGoalDTO.getTotalValue());
            
            newGoal.setIdCategory(cTmp);

            //persist on database the respective goal
            this.goal.create(newGoal);

            return true;

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean editGoal(GoalDTO editGoalDTO) {

        try {
            Goal goal = this.goal.find(editGoalDTO.getId_goal());
            
            Category cat=this.ca.find(editGoalDTO.getIdCategory());
            
            if (goal != null && cat!=null) {
                return false;
            }

            goal.setCurrentvalue(editGoalDTO.getCurrentValue());
            goal.setDescript(editGoalDTO.getDesc());
            goal.setFavorite(editGoalDTO.getFavorite());
            goal.setFinaldate(editGoalDTO.getFinalDate());
            goal.setFlagClickControl(editGoalDTO.getFlagClick());
            goal.setFlagOrder(editGoalDTO.getFlag_order());
            goal.setIdCategory(cat);
            goal.setIdGoal(editGoalDTO.getId_goal());
            goal.setLogdate(editGoalDTO.getLogDate());
            goal.setNome(editGoalDTO.getName());
            goal.setStatus(editGoalDTO.getStatus());
            goal.setTipo(editGoalDTO.getType());
            goal.setTotalvalue(editGoalDTO.getTotalValue());

            this.goal.edit(goal);
             
            return true;
        } catch (Exception e) {
            System.out.println("Mensagem: " + e.getMessage());

            return false;
        }
    }
    
    @Override
    public boolean removeGoal(String email, Integer id) {
        try {
            //find user
            Utilizador u = this.ut.findByEmail(email);
            if (u == null) {
                return false;
            }

            //find goal by id
            Goal g = this.goal.find(id);

            if (g == null) {
                return false;
            }

            //remove the goal
            this.goal.remove(g);
            return true;
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }

    }


}
