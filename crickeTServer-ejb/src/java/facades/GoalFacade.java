/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Goal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gustavo
 */
@Stateless
public class GoalFacade extends AbstractFacade<Goal> implements GoalFacadeLocal {

    @PersistenceContext(unitName = "crickeTServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GoalFacade() {
        super(Goal.class);
    }

    @Override
    public Goal findByName(String name_goal) {
        Goal verifica_encontrado=null;
        try{
            Query qu= this.em.createNamedQuery("Goal.findByNome");
            qu.setParameter("nome", name_goal);
            verifica_encontrado=(Goal) qu.getSingleResult();
        }catch(Exception e){
            System.out.println(""+e.getMessage());
            return null;
        }
        return verifica_encontrado;
    }
    
}
