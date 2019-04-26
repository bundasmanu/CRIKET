/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cricketdto;

import java.io.Serializable;

/**
 *
 * @author gustavo
 */
public class UserDTO implements Serializable{
    
    private String name;
    private String email;
    private String password;
    
    public UserDTO(){/*NECESSARIO PORQUE É SERIALIZABLE*/
        
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "name=" + name + ", email=" + email + '}';
    }
    
    
}
