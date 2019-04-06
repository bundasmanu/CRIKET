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

/**
 *
 * @author gustavo
 */
@Named(value = "userBean")
@ManagedBean
@SessionScoped
public class userBean implements Serializable{


    public userBean() { /*CONSTRUTOR VAZIO ESSENCIAL, POR CAUSA DE SER SERIALIZABLE*/
    
    }
    
}
