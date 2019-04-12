/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
    private Boolean isLogged;
    
    
    //@EJB
    //private UsersManagerLocal userManager;
    
    
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
        
        boolean result = (email.equals("bruno") && password.equals("123"));
        
        if(result){    
            context.getExternalContext().getSessionMap().put("user", email);
            this.isLogged = true;
            return "dashboard?faces-redirect=true";
        }
        else
        {
            Utils.throwMessage("Wrong e-mail or password.");
            
            return "index";

            //return "index?#contentSection";
        }
    }

    public String processSignUp() {
        String message;
        boolean result;
        
        /*
        
        TUserDTO userDTO = new TUserDTO();
        userDTO.setClientName(clientName);
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setUsertype(Config.CLIENT);
        
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().getResourceBundle(context, "Bundle.properties");
            
        if(!password.equals(passwordConfirmation))
        {
            //TODO: improve this message. Need to get this from Bundle.properties
            //message = bundle.getString("ConfirmPasswordError");
            
            
            message = "The password and the password confirmation are different.";
            
            Utils.throwMessage(message);
            return "signup";
        }
        
        result = userManager.signUp(userDTO);
        
        if(!result)
        {
            message = "Username already exists or passwords are different.";
            Utils.throwMessage(message);
            return "signup";

        }
        else
        {
            message = "Sign up with sucess! Now you can sign in, after operator approval " + userDTO.getUsername() + ".";
            Utils.throwMessage(message);
            return "signin";
        }
        */
        message = "System down.";
        Utils.throwMessage(message);
        return "index";
        
    }
    
    public String processLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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

    public Boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean isLogged) {
        this.isLogged = isLogged;
    }
    
}
