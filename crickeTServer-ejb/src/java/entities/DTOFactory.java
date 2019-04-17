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
    
    
    public DTOFactory(){
        
    }
    
    public static CategoryDTO getCategoryDTO(Category category){
        
        try{
            
            CategoryDTO categoryDTO = new CategoryDTO();
            
            if(category==null){
                return null;
            }
            
            categoryDTO.setNome(category.getNome());
            categoryDTO.setDescript(category.getDescript());
            categoryDTO.setIdCategory(category.getIdCategory());
            categoryDTO.setIdUser(getUserDTO(category.getIdUser()));
            
            return categoryDTO;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    
    public static GoalDTO getGoalDTO(Goal goal){
        
        try{
            
            GoalDTO goalDTO = new GoalDTO();
            
            if(goal==null){
                return null;
            }
            
            goalDTO.setName(goal.getNome());
            goalDTO.setDesc(goal.getDescript());
            goalDTO.setFavorite(goal.getFavorite());
            goalDTO.setType(goal.getTipo());
            goalDTO.setFinalDate(goal.getFinaldate());
            goalDTO.setTotalValue(goal.getTotalvalue());
            goalDTO.setCurrentValue(goal.getCurrentvalue());
            goalDTO.setLogDate(goal.getLogdate());
            goalDTO.setFlagClick(goal.getFlagClickControl());
            goalDTO.setFlag_order(goal.getFlagOrder());
            goalDTO.setId_goal(goal.getIdGoal());
            goalDTO.setStatus(goal.getStatus());
            goalDTO.setCategoryDTO(getCategoryDTO(goal.getIdCategory()));
        
        return goalDTO;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    
    public static UserDTO getUserDTO(Utilizador utilizador){
        
        try{
                    
            UserDTO userDTO = new UserDTO();
        
            if(utilizador==null){
                return null;
            }
            
            userDTO.setEmail(utilizador.getEmail());
            userDTO.setName(utilizador.getNome());
            
            return userDTO;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    
}
