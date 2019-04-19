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
            /*PARA APARECER AS CATEGORIAS DE SEGUIDA*/
            exist_user.getCategoryCollection().add(exist);
            this.user.edit(exist_user);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean removeCategory(String email, String name){
        
        try{
            
            /*USER VERIFICAR SE O USER EXISTE*/
            Utilizador a=this.user.findByEmail(email);
            
            if(a==null){
                return false;
            }
            
            /*VERIFICAR SE EXISTE ALGUMA CATEGORIA A ELIMINAR COM AQUELE NOME*/
            Category c=this.cat.findByName(name);
            
            /*SENAO EXISTE NENHUMA CATEGORIA COM AQUELE NOME RETORNA FALSE*/
            if(c==null){
                return false;
            }
            
            /*ATUALIZACAO DO UTILIZADOR, DAS SUAS CATEGORIAS*/
            a.getCategoryCollection().remove(c);
            
            /*REMOVE A CATEGORIA DA TABELA, E APAGA CONSEQUENTEMENTE OS SEUS OBJETIVOS, DEVIDO AO ATRIBUTO CASCADE, DEFINIDO NA COLECÇÃO DE OBJETIVOS*/
            this.cat.remove(c);
            
            this.user.edit(a);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean editCategory(String email, CategoryDTO c){
        
        try{
            
            /*VERIFICAR INICIALMENTE SE O UTILIZADOR EXISTE*/
            Utilizador a=this.user.findByEmail(email);
            
            if(a==null){
                return false;
            }
            
            /*CASO A CATEGORIA SEJA NULL, SAI*/
            if(c==null){
                return false;
            }
           
            Category ca=this.cat.find(c.getIdCategory());
            
            if(ca==null){
                return false;
            }
            
            /*ESTOU A ALTERAR A PROPRIA REFERENCIA, LOGO BASTA FAZER EDIT DO UTILIZADOR, QUE A CATEGORIA JA ESTA EDITADA*/
            
            if(ca.getDescript().equals(c.getDescript())==false){
                ca.setDescript(c.getDescript());
            }
            
            if(ca.getNome().equals(c.getNome())==false){
                ca.setNome(c.getNome());
            }
            
            this.cat.edit(ca);
            
            this.user.edit(a);
            
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
        
        try{
            
            /*VERIFICAR PRIMEIRO SE EXISTE O UTILIZADOR*/
            Utilizador u=this.user.findByEmail(emailOfLoggedUser);
            
            if(u==null){
                return new ArrayList<>();
            }
            
            List<CategoryDTO> categoryList=new ArrayList<CategoryDTO>();
            
            if(u.getCategoryCollection().isEmpty()==true){
                return new ArrayList<>();
            }
            
            for(Category c : u.getCategoryCollection()){
                /*LA DENTRO JA FAZ O SET DOS GOALS*/
                CategoryDTO x=DTOFactory.getCategoryDTO(c);
                categoryList.add(x);
            }
            
            return categoryList;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
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

    @Override
    public boolean save(Category category) {
        try{
            cat.edit(category);
            return true;
        }catch(Exception e){
            return false;
        }        
    }
    
}
