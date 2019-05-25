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
public class UserDTO implements Serializable {

    private String name;
    private String email;
    private String gender;
    private String Age;
    private String password;
    private int currentPoints;

    public UserDTO() {/*NECESSARIO PORQUE É SERIALIZABLE*/

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

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return Age;
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }
    
    public int getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "name=" + name + ", email=" + email + '}';
    }

}
