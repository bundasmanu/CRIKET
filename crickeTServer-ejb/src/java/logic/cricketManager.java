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
    userManagementLocal user;
    
    @EJB
    goalManagementLocal goal;
    
    @EJB
    historyManagementLocal history;
    
    @EJB
    categoryManagementLocal category;

    @Override
    public boolean validateLogin(String email, String pass) {
       return this.user.validateLogin(email, pass);
    }
    
    @Override
    public boolean signUp(String username, String pass, String email, String gender, Date birth){
        return user.signUp(username, pass, email, gender, birth);
    }
    
    @Override
    public boolean createCategory(String name, String desc,String email){
        return this.category.createCategory(name, desc,email);
    }
    
    @Override
    public boolean removeCategory(String name){
        return this.category.removeCategory(name);
    }

    @Override
    public List<CategoryDTO> getAllCategoriesFromLoggedUser(String emailOfLoggedUser) {
        return this.category.getAllCategoriesFromLoggedUser(emailOfLoggedUser);
    }
    
    @Override
    public List<GoalDTO> selectAllGoalsFromAnUser(String email){
        return this.goal.selectAllGoalsFromAnUser(email);
    }
    
    @Asynchronous
    @Override
    public Future<Integer> getNextValueFromGoalOrder(String email){
        return this.user.getNextValueFromGoalOrder(email);
    }
    
}
