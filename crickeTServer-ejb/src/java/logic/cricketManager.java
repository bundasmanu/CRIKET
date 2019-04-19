/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.CategoryDTO;
import cricketdto.GoalDTO;
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
    public boolean removeCategory(String email, String name){
        return this.categoryManager.removeCategory(email,name);
    }

    @Override
    public List<CategoryDTO> getAllCategoriesFromLoggedUser(String emailOfLoggedUser) {
        return this.categoryManager.getAllCategoriesFromLoggedUser(emailOfLoggedUser);
    }

    @Override
    public List<GoalDTO> selectAllGoalsFromAnUser(String email) {
        return this.goalManager.selectAllGoalsFromAnUser(email);
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
    
}
