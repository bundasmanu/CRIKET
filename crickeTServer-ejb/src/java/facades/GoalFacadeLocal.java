/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Goal;
import entities.Utilizador;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface GoalFacadeLocal {

    void create(Goal goal);

    void edit(Goal goal);

    void remove(Goal goal);

    Goal find(Object id);

    Goal findByName(String name_goal);
    
    List<Goal> getGoalsBetweenDates(Utilizador u, Date d1, Date d2);
    
    List<Goal> getCompleteGoalsFromAnUser(Utilizador u);
    
    List<Goal> getIncompleteGoalsFromAnUser(Utilizador u);
    
    List<Goal> getDailyGoals();
    
    List<Goal> getWeeklyGoals();
    
    List<Goal> getMonthlyGoals();
    
    List<Goal> getYearGoals();

    List<Goal> findAll();

    List<Goal> findRange(int[] range);

    int count();
    
}
