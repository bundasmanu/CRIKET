/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BridgeLogicController.BridgeLocal;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named(value = "goalBean")
@ManagedBean
@SessionScoped
public class GoalBean implements Serializable{

    @EJB
    BridgeLocal bridge;
    
    
    public GoalBean() {
        
        
    }
}
