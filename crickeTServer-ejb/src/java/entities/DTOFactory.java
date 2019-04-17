/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import cricketdto.CategoryDTO;
import cricketdto.GoalDTO;
import cricketdto.UserDTO;


public class DTOFactory{
    
    public CategoryDTO getCategoryDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        
        
        return categoryDTO;
    }
    
    public GoalDTO getGoalDTO(Goal goal){
        GoalDTO goalDTO = new GoalDTO();
        
        return goalDTO;
    }
    
    public UserDTO getUserDTO(Utilizador utilizador){
        UserDTO userDTO = new UserDTO();
        
        return userDTO;
    }
    
}
