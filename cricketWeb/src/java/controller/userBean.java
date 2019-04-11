/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cricketdto.UserDTO;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author gustavo
 */
@Named(value = "userBean")
@ManagedBean
@SessionScoped
public class userBean implements Serializable{
    
    UserDTO u;
    
    public userBean() {
        //Do Nothing. We must create the constructor since the classe is serializable
    }
    
    /*EXAMPLE OF GETTING VALUE FROM A USER THAT HAS LOGGED IN SYSTEM*/
    public String myLoginName(){
        return (String)SessionContext.getInstance().getAttribute("cli");
    }
    
    /*EXAMPLE OF SETTING AN ATTRIBUTE FROM AN USER IN SESSIONCONTEXT CLASS*/
    public void addValue(){
        SessionContext.getInstance().setAttribute("userName", u.getName()); /*u.getName--> is the value, setted in textbox, in frontend,--> u.Name--> applies get and set*/
    }

    
}
