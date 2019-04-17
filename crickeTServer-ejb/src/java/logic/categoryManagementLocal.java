/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.CategoryDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface categoryManagementLocal {
    
    boolean createCategory(String name, String desc, String email);
    boolean removeCategory(String email, String name);
    void initializeCategories(String email);
    List<CategoryDTO> getAllCategoriesFromLoggedUser(String emailOfLoggedUser);
}
