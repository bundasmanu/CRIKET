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
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface cricketManagerLocal {
    
    boolean signUp(String username, String pass, String email, String gender, Date birth);
    
    boolean validateLogin(String email,String pass);
    
    boolean createCategory(String name, String desc, String email);
    
    boolean removeCategory(String email, String name);
    
    List<GoalDTO> selectAllGoalsFromAnUser(String email);
    
    @Asynchronous
    Future<Integer> getNextValueFromGoalOrder(String email);
    List<CategoryDTO> getAllCategoriesFromLoggedUser(String emailOfLoggedUser);
    
    CategoryDTO findCategoryDTOById(Integer id);
    
    boolean addGoal(GoalDTO goalDTO);
    
    boolean editGoal(GoalDTO goalDTO);
    
    boolean removeGoal(String email,Integer id);
    
    Future<Integer> getNextValueGoal(String email);
    
}
