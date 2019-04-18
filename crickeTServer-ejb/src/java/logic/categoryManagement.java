/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.CategoryDTO;
import entities.Category;
import entities.DTOFactory;
import entities.Utilizador;
import facades.CategoryFacadeLocal;
import facades.UtilizadorFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton; 

/**
 *
 * @author gustavo
 */
@Singleton
public class categoryManagement implements categoryManagementLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    CategoryFacadeLocal cat;
    
    @EJB
    UtilizadorFacadeLocal user;
    
    @EJB
    BeginManagementLocal beg;
    
    @Override
    public boolean createCategory(String name, String desc, String email){
        
        try{
                    
            Utilizador exist_user=this.user.findByEmail(email);
            
            /*SE JA EXISTE A CATEGORIA, OU NAO EXISTE USER RETORNA*/
            if(exist_user==null){
                return false;
            }
            
            /*VERIFICAR SE O USER TEM JA UMA CATEGORIA CRIADA COM AQUELE NOME, SE JA TIVER RETORNA LOGO*/
            for(Category c :exist_user.getCategoryCollection()){
                if(c.getNome().equals(name)==true){
                    return false;
                }
            }
            
            /*COMO A REFERENCIA PARA O OBJETO EXIST ESTA A NULL, POSSO PEGAR NELE*/
            Category exist=new Category(name, desc);
            exist.setIdUser(exist_user); 
            
            this.cat.create(exist);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean removeCategory(String name){
        
        try{
            
            /*VERIFICAR SE EXISTE ALGUMA CATEGORIA A ELIMINAR COM AQUELE NOME*/
            Category c=this.cat.findByName(name);
            
            /*SENAO EXISTE NENHUMA CATEGORIA COM AQUELE NOME RETORNA FALSE*/
            if(c==null){
                return false;
            }
            
            /*REMOVE A CATEGORIA DA TABELA, E APAGA CONSEQUENTEMENTE OS SEUS OBJETIVOS, DEVIDO AO ATRIBUTO CASCADE, DEFINIDO NA COLECÇÃO DE OBJETIVOS*/
            this.cat.remove(c);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public void initializeCategories(String email){
        
        try{
            
            for(String j: this.beg.getLista_cat()){
                this.createCategory(j, "", email);
            }          

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    @Override
    public List<CategoryDTO> getAllCategoriesFromLoggedUser(String emailOfLoggedUser) {
        
        List<CategoryDTO> categoryDTOList = new ArrayList();
        
        Utilizador user=this.user.findByEmail(emailOfLoggedUser);
        
        if(user==null){
            return new ArrayList<>();
        }

        for(Category categoryTmp :user.getCategoryCollection()){
            categoryDTOList.add(DTOFactory.getCategoryDTO(categoryTmp));
        }
        
        return categoryDTOList;
    }

    @Override
    public CategoryDTO findCategoryDTOById(Integer id) {
        Category category = cat.find(id);
        
        if(category == null)
            return null;
        
        return DTOFactory.getCategoryDTO(category);
    }

    @Override
    public Category findCategoryById(Integer id) {
        Category category = cat.find(id);
        return category;
    }
    
}
