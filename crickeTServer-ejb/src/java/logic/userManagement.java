/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.User;
import facades.UserFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author gustavo
 */
@Singleton
public class userManagement implements userManagementLocal {

    @EJB
    UserFacadeLocal user;

    @Override
    public boolean validateLogin(String email, String pass) {
        try {
            //verify if the email of a specific user exists
            User return_user = this.user.findByEmail(email);

            //if not exist
            if (return_user == null || (return_user != null && return_user.getPassword().equals(pass) == false)) {
                return false;
            }

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }
        return true;
    }

}
