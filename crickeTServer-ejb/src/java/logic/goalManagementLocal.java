/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.GoalDTO;
import entities.Goal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface goalManagementLocal {
    
    List<GoalDTO> selectAllGoalsFromAnUser(String email);
    
    List<GoalDTO> selectAllNotDoneGoalsFromAnUser(String email);
    
    List<GoalDTO> selectAllDoneGoalsFromAnUser(String email);
    
    List<GoalDTO> selectAllGoalsFromUserByClicks(String email);
    
    boolean createGoal(GoalDTO newGoalDTO);
    
    boolean editGoal(GoalDTO editGoalDTO);
    
    boolean removeGoal(String email, Integer id);
    
    boolean recoveryDoneGoal(Integer id);
    
    Future<Integer> getNextValueGoal(String email);
    
    GoalDTO findGoalDTOById(int id);
    
    boolean increaseCurrentValue(GoalDTO goal);
    
    boolean decreaseCurrentValue(GoalDTO goal);
    
    boolean goalIsEnd(GoalDTO goal);
    
    /*APLICADO QUANDO É CLICADA A SETA PARA CIMA*/
    boolean downOrderValue(GoalDTO goal);
    
    /*APLICADO QUANDO É CLICADA A SETA PARA BAIXO*/
    boolean upOrderValue(GoalDTO goal);
    
    boolean increaseClickFlag(GoalDTO goal);
    
    List<GoalDTO> getGoalsBetweenTwoDates(String email,Date d1, Date d2);
    
    List<GoalDTO> orderGoalsBetweenDate(String email);
    
    boolean setGoalAsDone(Goal goal);
    
    List<GoalDTO> processGoalsFilter(String filterName, String filterSinceDate, String filterUntilDate);


}
