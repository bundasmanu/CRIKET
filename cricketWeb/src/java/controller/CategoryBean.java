/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import BridgeLogicController.*;
import cricketdto.CategoryDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author gustavo
 */
@Named(value = "categoryBean")
@ManagedBean
@SessionScoped
public class CategoryBean implements Serializable{

    /**
     * Creates a new instance of CategoryBean
     */
    
    String name_c;
    String desc_c;
    
    @EJB
    BridgeLocal bridge;
    
    
    public CategoryBean() {
    }
    
    /*ACCAO DO BOTAO DE CRIAR CATEGORIA*/
    public String createCategory(){
        
//        try{
//            
//            /*TENTATIVA DE CRIAR UMA CATEGORIA*/
//            boolean return_create=this.bridge.getCricket().createCategory(this.name_c, this.desc_c);
//            
//            if(return_create==false){
//                return "index.xhtml";
//            }
//            
//            return "dashboard.xhtml";
//            
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//            return "index.xhtml";
//        }
        return "";
    }
    
    /*ACCAO DO BOTAO DE CRIAR CATEGORIA*/
    public String removeCategory(){
        
        try{
            
            /*TENTATIVA DE REMOVER UMA CATEGORIA*/
            boolean return_remove=this.bridge.getCricket().removeCategory(this.name_c);
            
            if(return_remove==false){
                return "index.xhtml";
            }
            
            return "dashboard.xhtml";
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return "index.xhtml";
        }
        
    }
    
    
        public List<CategoryDTO> getAllCategoriesFromLoggedUser() 
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            String emailOfLoggedUser = (String) fc.getExternalContext().getSessionMap().get("user");
            return bridge.getCricket().getAllCategoriesFromLoggedUser(emailOfLoggedUser);
        }
    
    
    
}
