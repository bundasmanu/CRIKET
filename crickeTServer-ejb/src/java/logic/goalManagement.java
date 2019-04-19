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
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
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

    private DTOFactory dt = new DTOFactory();

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
            Category cTmp = this.ca.find(newGoalDTO.getIdCategory());
            

            if (cTmp == null) {
                return false;
            }
            
            //if the category has goals...
            if(!cTmp.getGoalCollection().isEmpty()){
                for(Goal goalTmp: cTmp.getGoalCollection())
                {
                    if(goalTmp.getNome().equals(newGoalDTO.getName()))
                        return false;
                }
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
            newGoal.setTotalvalue(newGoalDTO.getTotalValue());
            newGoal.setFrequency(newGoalDTO.getFrequency());
            newGoal.setIdCategory(cTmp);

            //persist on database the respective goal
            //this.goal.create(newGoal);

            
            cTmp.getGoalCollection().add(newGoal);
            
            categoryManagement.save(cTmp);
            
            return true;

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean editGoal(GoalDTO editGoalDTO) {

        try {
            Goal goalToEdit = this.goal.find(editGoalDTO.getId_goal());

            Category cat = this.ca.find(editGoalDTO.getIdCategory());

            if (goal == null || cat == null) {
                return false;
            }

            goalToEdit.setCurrentvalue(editGoalDTO.getCurrentValue());
            goalToEdit.setDescript(editGoalDTO.getDesc());
            goalToEdit.setFavorite(editGoalDTO.getFavorite());
            goalToEdit.setFinaldate(editGoalDTO.getFinalDate());
            goalToEdit.setFlagClickControl(editGoalDTO.getFlagClick());
            goalToEdit.setFlagOrder(editGoalDTO.getFlag_order());
            goalToEdit.setIdGoal(editGoalDTO.getId_goal());
            goalToEdit.setLogdate(editGoalDTO.getLogDate());
            goalToEdit.setNome(editGoalDTO.getName());
            goalToEdit.setStatus(editGoalDTO.getStatus());
            goalToEdit.setTotalvalue(editGoalDTO.getTotalValue());

            System.out.println("\n\n\n goalToEdit.getIdCategory().getNome(): " + goalToEdit.getIdCategory().getNome());
            System.out.println("\n\n\n cat.getNome(): " + cat.getNome());
            

            //if the user selected anoter category (can comparing with name because name is unique)
            //
            //TODO: error changing category
            //
            if(!goalToEdit.getIdCategory().getNome().equals(cat.getNome()))
            {
                
                System.out.println("\n\n\n\naqui");
                Category atualCategory = goalToEdit.getIdCategory();
                atualCategory.getGoalCollection().remove(goalToEdit);
                categoryManagement.save(atualCategory);
                
                
                cat.getGoalCollection().add(goalToEdit);
                categoryManagement.save(cat);
                
                
                goalToEdit.setIdCategory(cat);
                
                
                
                
                //goalToEdit.setIdCategory(cat);
                //categoryManagement.save(cat);
                
            }
            this.goal.edit(goalToEdit);
            
            //categoryManagement.save(cat);

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
            
            Category category = g.getIdCategory();
            category.getGoalCollection().remove(g);
            categoryManagement.save(category);
            
            //remove the goal
            this.goal.remove(g);
            return true;
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }

    }
    
    @Asynchronous
    @Override
    public Future<Integer> getNextValueGoal(String email){
        
        try{
            
            /*VERIFICAR INICIALMENTE SE O UTILIZADOR EXISTE*/
            Utilizador u=this.ut.findByEmail(email);
            
            if(u==null){
                return new AsyncResult<>(-2);
            }
            
            /*VERIFICAR SE JA EXISTEM GOALS PARA ESSE USER*/
            Collection catUser=u.getCategoryCollection();
            if(catUser.isEmpty()==true){
                return new AsyncResult<>(1);/*NAO EXISTEM ELEMENTOS ENTAO O PRIMEIRO ELEMENTO É 1*/
            }
            
            List<Integer> goals=new ArrayList<Integer>();
            for(Category c : u.getCategoryCollection()){
                if(c.getGoalCollection().isEmpty()==false){
                    for(Goal g : c.getGoalCollection()){
                        goals.add(g.getIdGoal());
                    }
                }
            }
            
            /*SE TENHO CATEGORIAS, MAS NAO TENHO GOALS, RETORNA 1*/
            if(goals.isEmpty()==true){  
                return new AsyncResult<>(1);
            }
            
            /*OBTENCAO DO MAIOR VALOR DO GOAL*/
            Integer maxValue=Collections.max(goals);
            
            return new AsyncResult<>(maxValue+1);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new AsyncResult<>(-1);
        }
        
    }

    @Override
    public GoalDTO findGoalDTOById(int id) {
        Goal goalTmp = goal.find(id);
        
        if(goalTmp == null)
            return null;
        return DTOFactory.getGoalDTO(goalTmp);
    }
    

}
