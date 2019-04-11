/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author gustavo
 */

public class SessionContext { 
    private static SessionContext instance; 
    
    public static SessionContext getInstance(){ 
        if (instance == null){ 
            instance = new SessionContext(); 
        } 
        return instance;
    } 
    
    private SessionContext(){
    } 
    
    private ExternalContext currentExternalContext(){ 
        if (FacesContext.getCurrentInstance() == null){ 
            throw new RuntimeException("FacesContext can’t be called outside of a HTTP request"); 
        }else{ 
            return FacesContext.getCurrentInstance().getExternalContext(); } 
    } 
    
    public void closeSession(){ 
        currentExternalContext().invalidateSession(); 
    } 
    
    public Object getAttribute(String name){ 
        return currentExternalContext().getSessionMap().get(name); 
    } 
    
    public void setAttribute(String name, Object value){ 
        currentExternalContext().getSessionMap().put(name, value); 
    } 
    
   
}
