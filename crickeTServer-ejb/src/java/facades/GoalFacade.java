/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Goal;
import entities.Utilizador;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author bruno
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
    
    @Override
    public List<Goal> getGoalsBetweenDates(Utilizador u, Date d1, Date d2){
        
        try{
            
            String cmdDate="select g from Goal g inner join Category c on (c=g.idCategory) inner join Utilizador u on (u= c.idUser) where g.finaldate> d1 and g.finaldate<d2";
            Query qu= this.em.createQuery(cmdDate);
            List<Goal> listGoals= (List<Goal>) qu.getResultList();
            
            return listGoals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    public List<Goal> getCompleteGoalsFromAnUser(Utilizador u){
        
        try{
            
            String cmdCompleteGoals="select g from Goal g inner join Category c on c=g.idCategory inner join Utilizador u on u=c.idUser where g.currentValue>=g.totalValue";
            Query qu=this.em.createQuery(cmdCompleteGoals);
            List<Goal> listCompleteGoals= (List<Goal>) qu.getResultList();
            
            return listCompleteGoals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    public List<Goal> getIncompleteGoalsFromAnUser(Utilizador u){
        
        try{
            
            String cmdCompleteGoals="select g from Goal g inner join Category c on c=g.idCategory inner join Utilizador u on u=c.idUser where g.currentValue<g.totalValue and g.finalDate< to_char( CURRENT_DATE, 'DD/MM/YYYY') as re_format ";
            Query qu=this.em.createQuery(cmdCompleteGoals);
            List<Goal> listCompleteGoals= (List<Goal>) qu.getResultList();
            
            return listCompleteGoals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
}
