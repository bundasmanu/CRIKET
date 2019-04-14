/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gustavo
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @PersistenceContext(unitName = "crickeTServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public User findByEmail(String email) {
        User verifica_encontrado=null;
        try{
            Query qu= this.em.createNamedQuery("User.findByEmail");
            qu.setParameter("email", email);
            verifica_encontrado=(User) qu.getSingleResult();
        }catch(Exception e){
            System.out.println(""+e.getMessage());
            return null;
        }
        return verifica_encontrado;
    }
    
    
}
