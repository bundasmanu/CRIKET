/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.GoalDTO;
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
    
    boolean createGoal(GoalDTO newGoalDTO);
    
    boolean editGoal(GoalDTO editGoalDTO);
    
    boolean removeGoal(String email, Integer id);
    
    Future<Integer> getNextValueGoal(String email);
    
    GoalDTO findGoalDTOById(int id);
}
