/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BridgeLogicController.BridgeLocal;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.RequestDispatcher;
import utils.Utils;

/**
 *
 * @author bruno
 */

@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
	
    private String password;
    private String passwordConfirmation;
    private String email;
    private String clientName;
    private Date birth;
    private String birthTmp; //temporary var... Just to receive the date from datepicker
    private LocalDate birthLocalDate;
    private String gender;
    private Boolean isLogged;
    
    
    //@EJB
    //private UsersManagerLocal userManager;
    
    @EJB
    BridgeLocal bridge;
    
    public SessionController() {
        // do nothing
    }
    
    @PostConstruct
    private void init() {
        isLogged = false;
    }
    
    public String processSignIn() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        //SignInValue value = userManager.signIn(username, password);
        
        boolean result = this.bridge.getCricket().validateLogin(email, password);
        
        
        if(result){    
            context.getExternalContext().getSessionMap().put("user", email);
            this.isLogged = true;
            return "dashboard?faces-redirect=true";
        }
        else
        {
            Utils.throwMessage("Wrong e-mail or password.");
            return "index";
        }
    }
    
    public String process_SignUp(){
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        boolean result = this.signUp();     
        
        if(result){    
            context.getExternalContext().getSessionMap().put("user", email);
            this.isLogged = true;
            return "dashboard?faces-redirect=true";
        }
        else
        {
            Utils.throwMessage("This user already exists.");
            return "index";
        }
        
    }
    
    public boolean signUp(){
        
        try{
            System.out.println("\n\n\n\n\n\n\n String: " + birthTmp);
            
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            
            LocalDate localDate = LocalDate.parse(birthTmp, formatter);
            System.out.println("\n\n\n\n\n\n\n LocalDate date: " + localDate);
            
            return true;
//return bridge.getCricket().signUp(clientName, password, email, gender, birth);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public void validateIfLoggedUser(ComponentSystemEvent event){
				
	FacesContext fc = FacesContext.getCurrentInstance();
	
        String username = (String) fc.getExternalContext().getSessionMap().get("user");
        
        //TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(username == null || username.isEmpty())
        {
            ConfigurableNavigationHandler nav 
		   = (ConfigurableNavigationHandler) 
			fc.getApplication().getNavigationHandler();
		
		nav.performNavigation("/index");
        }	
    }	
    
    public String processLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.isLogged = false;
        return "index?faces-redirect=true";
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getBirth() {
        return birth;
    }

    public String getGender() {
        return gender;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean isLogged) {
        this.isLogged = isLogged;
    }

    public String getBirthTmp() {
        return birthTmp;
    }

    public void setBirthTmp(String birthTmp) {
        this.birthTmp = birthTmp;
    }
    
}
