/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.CategoryDTO;
import cricketdto.GoalDTO;
import cricketdto.UserDTO;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author gustavo
 */
@Singleton
public class cricketManager implements cricketManagerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /*INJECCAO DE DEPENDENCIAS PARA OS EJB QUE IRAO CONTER A LOGICA*/
    @EJB
    userManagementLocal userManager;

    @EJB
    goalManagementLocal goalManager;

    @EJB
    historyManagementLocal historyManager;

    @EJB
    categoryManagementLocal categoryManager;
    
    @EJB
    rankingManagementLocal rankManager;

    @Override
    public boolean validateLogin(String email, String pass) {
        return this.userManager.validateLogin(email, pass);
    }

    @Override
    public boolean signUp(String username, String pass, String email, String gender, Date birth) {
        return userManager.signUp(username, pass, email, gender, birth);
    }

    @Override
    public boolean createCategory(String name, String desc, String email) {
        return this.categoryManager.createCategory(name, desc, email);
    }

    @Override
    public boolean removeCategory(String email, Integer id){
        return this.categoryManager.removeCategory(email,id);
    }

    @Override
    public List<CategoryDTO> getAllCategoriesFromLoggedUser(String emailOfLoggedUser) {
        return this.categoryManager.getAllCategoriesFromLoggedUser(emailOfLoggedUser);
    }

    @Override
    public List<GoalDTO> selectAllGoalsFromAnUser(String email) {
        return this.goalManager.selectAllGoalsFromAnUser(email);
    }
    
    @Override
    public List<GoalDTO> selectAllGoalsFromUserByClicks(String email){
        return this.goalManager.selectAllGoalsFromUserByClicks(email);
    }

    @Asynchronous
    @Override
    public Future<Integer> getNextValueFromGoalOrder(String email) {
        return this.userManager.getNextValueFromGoalOrder(email);
    }

    @Override
    public CategoryDTO findCategoryDTOById(Integer id) {
        return this.categoryManager.findCategoryDTOById(id);
    }

    @Override
    public boolean addGoal(GoalDTO goalDTO) {
        return this.goalManager.createGoal(goalDTO);
    }

    @Override
    public boolean editGoal(GoalDTO goalDTO) {
        return this.goalManager.editGoal(goalDTO);
    }
    
    @Override
    public boolean removeGoal(String email, Integer id) {
        return this.goalManager.removeGoal(email, id);
    }
    
    @Asynchronous
    @Override
    public Future<Integer> getNextValueGoal(String email){
        return this.goalManager.getNextValueGoal(email);
    }

    @Override
    public GoalDTO findGoalDTOById(int id) {
        return goalManager.findGoalDTOById(id);
    }
    
    @Override
    public boolean increaseCurrentValue(GoalDTO goal){
        return this.goalManager.increaseCurrentValue(goal);
    }
    
    @Override
    public boolean decreaseCurrentValue(GoalDTO goal){
        return this.goalManager.decreaseCurrentValue(goal);
    }
     
    @Override
    public boolean goalIsEnd(GoalDTO goal){
        return this.goalManager.goalIsEnd(goal);
    }
    
    @Override
    public boolean increaseClickFlag(GoalDTO goal){
        return this.goalManager.increaseClickFlag(goal);
    }

    @Override
    public boolean editCategory(String email, CategoryDTO c) {
        return this.categoryManager.editCategory(email, c);
    }
    
     @Override
    public boolean editUser(String email, String password) {
        return this.userManager.editUser(email, password);
    }

    @Override
    public UserDTO findUserById(Integer id) {
       return this.userManager.findUserById(id);   
    }
    
    @Override
    public List<GoalDTO> getGoalsBetweenTwoDates(String email, Date d1, Date d2){
        return this.goalManager.getGoalsBetweenTwoDates(email, d1, d2);
    }
    
    @Override
    public List<CategoryDTO> getCategorysFromUserOrderedByName(String email){
        return this.categoryManager.getCategorysFromUserOrderedByName(email);
    }
    
    @Override
    public List<GoalDTO> orderGoalsBetweenDate(String email){
        return this.goalManager.orderGoalsBetweenDate(email);
    }

    @Override
    public String findRankUser(String email) {
        return this.rankManager.findRankUser(email);
    }

    @Override
    public List<GoalDTO> selectAllNotDoneGoalsFromAnUser(String email) {
        return this.goalManager.selectAllNotDoneGoalsFromAnUser(email);
    }

    @Override
    public List<GoalDTO> selectAllDoneGoalsFromAnUser(String email) {
        return this.goalManager.selectAllDoneGoalsFromAnUser(email);
    }

    @Override
    public boolean recoveryDoneGoal(Integer id) {
        return this.goalManager.recoveryDoneGoal(id);
    }

    @Override
    public boolean upOrderValue(GoalDTO goal) {
        return this.goalManager.upOrderValue(goal);
    }

    @Override
    public boolean downOrderValue(GoalDTO goal) {
        return this.goalManager.downOrderValue(goal);
    }
    
}
