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
import javax.inject.Inject;
import utils.Utils;

/**
 *
 * @author gustavo
 */
@Named(value = "categoryBean")
@SessionScoped
public class CategoryBean implements Serializable {

    CategoryDTO categoryDTO;

    @EJB
    BridgeLocal bridge;

    @Inject
    SessionBean s;

    public CategoryBean() {
    }

    public CategoryDTO findCategoryDTOById(Integer id) {
        return bridge.getCricket().findCategoryDTOById(id);
    }

    /*ACCAO DO BOTAO DE CRIAR CATEGORIA*/
    public String createCategory() {
        this.categoryDTO = new CategoryDTO();

        return "createCategory";
    }

    public String processAddCategory() {

        try {

            /*TENTATIVA DE CRIAR UMA CATEGORIA*/
            boolean return_create = this.bridge.getCricket().createCategory(this.categoryDTO.getNome(), this.categoryDTO.getDescript(), this.s.getEmail());

            if (return_create == false) {
                return "index.xhtml";
            }

            return "indexCategories.xhtml";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "index.xhtml";
        }
    }

    public String editCategory(int idCategory) {
        this.categoryDTO = bridge.getCricket().findCategoryDTOById(idCategory);
        if (categoryDTO == null) {
            Utils.throwMessage("Error. Couldn't find the category");
        }
        return "editCategory";
    }

    public String processEditCategory() {
        boolean result = false;
        try {

            /*TENTATIVA DE CRIAR UMA CATEGORIA*/
            boolean return_create = this.bridge.getCricket().editCategory(this.s.getEmail(), this.categoryDTO);
            if (return_create) {
                this.categoryDTO = new CategoryDTO();
                return "/indexCategories?faces-redirect=true?";
            } else {
                Utils.throwMessage("Error");
                return "/indexCategories?faces-redirect=true?";
            }

        } catch (Exception e) {
            Utils.throwMessage("Error");
            return "editCategory";
        }
    }

    public String removeCategory(int idCategory) {
        this.categoryDTO = bridge.getCricket().findCategoryDTOById(idCategory);
        if (categoryDTO == null) {
            Utils.throwMessage("Error. Couldn't find the category");
        }
        return "removeCategory";
    }

    /*ACCAO DO BOTAO DE CRIAR CATEGORIA*/
    public String processRemoveCategory(int id) {
        boolean result = false;
        try {
            result = this.bridge.getCricket().removeCategory(this.s.getEmail(), id);
            if (result) {
                return "/indexCategories.xhtml?faces-redirect=true?";
            } else {
                Utils.throwMessage("Error removing the category. The category wasn't found or exist goals using the category.");
                return "indexCategories.xhtml";
            }
        } catch (Exception e) {
            Utils.throwMessage("Error");
            return "removeCategory";
        }

    }

    public List<CategoryDTO> getAllCategoriesFromLoggedUser() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String emailOfLoggedUser = (String) fc.getExternalContext().getSessionMap().get("user");

        List<CategoryDTO> list = bridge.getCricket().getAllCategoriesFromLoggedUser(emailOfLoggedUser);
        
        return list;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

}
