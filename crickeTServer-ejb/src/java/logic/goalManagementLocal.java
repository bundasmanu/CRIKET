/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.GoalDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface goalManagementLocal {
    
    List<GoalDTO> selectAllGoalsFromAnUser(String email);
    
}
