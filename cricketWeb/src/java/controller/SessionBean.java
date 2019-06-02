/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BridgeLogicController.BridgeLocal;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import cricketdto.UserDTO;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import utils.Utils;

/**
 *
 * @author bruno
 */
@Named("sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;

    private String password;
    private String passwordConfirmation;
    private String email;
    private String clientName;
    private String birthTmp;
    private String gender;
    UserDTO user;

    @EJB
    private BridgeLocal bridge;

    @Inject
    GoalBean gg;

    public SessionBean() {
        // do nothing
    }

    private void setOrderFlag(FacesContext context) {
        /*RESTAURO DA FLAG DE ORDENACAO DO NOVO OBJETIVO QUE O UTILIZADOR PODE CRIAR*/
        ValueExpression vex
                = context.getApplication().getExpressionFactory()
                        .createValueExpression(context.getELContext(),
                                "#{goalBean}", GoalBean.class);

        GoalBean goalBean = (GoalBean) vex.getValue(context.getELContext());
        this.gg = goalBean;
        this.gg.nextValueOrderGoal = this.bridge.getCricket().getNextValueFromGoalOrder(email);
    }

    public String processSignIn() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();
        
        boolean result = this.bridge.getCricket().validateLogin(email, password);
 
        if (result) {
            context.getExternalContext().getSessionMap().put("user", email);

            setOrderFlag(context);  

            return "dashboard?faces-redirect=true";
        } else {
            Utils.throwMessage("Wrong e-mail or password.");
            return "login";
        }
    }

    public String process_SignUp() {

        FacesContext context = FacesContext.getCurrentInstance();

        boolean result = this.signUp();

        if (result) {
            context.getExternalContext().getSessionMap().put("user", email);
            setOrderFlag(context);
            return "dashboard?faces-redirect=true";
        } else {
            Utils.throwMessage("This user already exists.");
            return "login";
        }
    }

    public String editUser(String email){
        
        user = bridge.getCricket().findUserbyEmail(email);
           
        if(user == null){
            Utils.throwMessage("Error, Could´t find the user.");
            return "dashboard";
        }
        return "editProfile";
    }
    
    public String process_EditUser() {
        boolean result = false;

        result = bridge.getCricket().editUser(this.getEmail(), user.getPassword(), user.getName(), user.getGender(), user.getAge()); 
        if (result) {
            return "dashboard?faces-redirect=true";
        } else {
            return "login";
        }

    }

    public boolean signUp() {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date birth = formatter.parse(this.birthTmp);
            return bridge.getCricket().signUp(clientName, password, email, gender, birth);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //used before render the html page...
    public void validateIfLoggedUser(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();

        String username = (String) fc.getExternalContext().getSessionMap().get("user");

        if (username == null || username.isEmpty()) {
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("/login");
        }
    }

    //used before render the html page...
    //if user is logged and want go to "index" return to dashboard
    public void validateIfUserIsLogged(ComponentSystemEvent event) {
        FacesContext fc = FacesContext.getCurrentInstance();

        String email = (String) fc.getExternalContext().getSessionMap().get("user");

        if (email != null) {
            ConfigurableNavigationHandler nav
                    = (ConfigurableNavigationHandler) fc.getApplication().getNavigationHandler();

            nav.performNavigation("/dashboard");
        }
    }

    public Boolean getIsLogged() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String username = (String) fc.getExternalContext().getSessionMap().get("user");

        if (username == null) {
            return false;
        }

        return !username.isEmpty();
    }

    public String processLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true";
    }
    
    
    public void redirectSignUp() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("registerUser.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthTmp() {
        return birthTmp;
    }

    public void setBirthTmp(String birthTmp) {
        this.birthTmp = birthTmp;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
    
}