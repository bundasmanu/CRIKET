/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.CategoryDTO;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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
    
    boolean removeCategory(String name);
    
    List<CategoryDTO> getAllCategoriesFromLoggedUser(String emailOfLoggedUser);
    
}
