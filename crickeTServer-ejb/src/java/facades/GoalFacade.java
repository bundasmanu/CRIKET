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
import cricketdto.*;
import Utils.*;
import javax.persistence.TemporalType;

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
            
            String cmdDate="select g from Goal g inner join Category c on (c=g.idCategory) inner join Utilizador u on (u= c.idUser) where g.finaldate> d1 and g.finaldate<d2 order by g.flagOrder";
            Query qu= this.em.createQuery(cmdDate);
            List<Goal> listGoals= (List<Goal>) qu.getResultList();
            
            return listGoals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Goal> getCompleteGoalsFromAnUser(Utilizador u){
        
        try{
            
            String cmdCompleteGoals="select g from Goal g inner join Category c on c=g.idCategory inner join Utilizador u on u=c.idUser where g.flagdone= TRUE";
            Query qu=this.em.createQuery(cmdCompleteGoals);
            List<Goal> listCompleteGoals= (List<Goal>) qu.getResultList();
            
            return listCompleteGoals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Goal> getIncompleteGoalsFromAnUser(Utilizador u){
        
        try{
            
            String cmdCompleteGoals="select g from Goal g inner join Category c on c=g.idCategory inner join Utilizador u on u=c.idUser where g.currentValue<g.totalValue and g.frequency != 'NEVER' ";
            Query qu=this.em.createQuery(cmdCompleteGoals);
            List<Goal> listCompleteGoals= (List<Goal>) qu.getResultList();
            
            return listCompleteGoals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Goal> getDailyGoals(){
        
        try{
            
            String dailyQuery="select g from Goal g where g.frequency= ?1 and g.flagdone= ?2";
            Query qu=this.em.createQuery(dailyQuery);
            qu.setParameter(1, Config.DAILY);
            qu.setParameter(2, Boolean.FALSE);
            List<Goal> dailyGoals= (List<Goal>) qu.getResultList();
            
            return dailyGoals;
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Goal> getWeeklyGoals(){
       
        try {

            String weeklyQuery = "select g from Goal g where g.frequency= ?1 and g.flagdone= ?2";
            Query qu = this.em.createQuery(weeklyQuery);
            qu.setParameter(1, "Weekly");
            qu.setParameter(2, Boolean.FALSE);
            List<Goal> weeklyGoals = (List<Goal>) qu.getResultList();

            return weeklyGoals;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Goal> getMonthlyGoals(){
        
        try {

            String monthlyQuery = "select g from Goal g where g.frequency= ?1 and g.flagdone= ?2";
            Query qu = this.em.createQuery(monthlyQuery);
            qu.setParameter(1, "Monthly");
            qu.setParameter(2, Boolean.FALSE);
            List<Goal> monthlyGoals = (List<Goal>) qu.getResultList();

            return monthlyGoals;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Goal> getYearGoals(){
        
        try {

            String yearlyQuery = "select g from Goal g where g.frequency= ?1 and g.flagdone= ?2";
            Query qu = this.em.createQuery(yearlyQuery);
            qu.setParameter(1, "Yearly");
            qu.setParameter(2, Boolean.FALSE);
            List<Goal> yearlyGoals = (List<Goal>) qu.getResultList();

            return yearlyGoals;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Goal> getGoalsWithSameNameAndLogdate(Goal g){
        
        try{
            
            String goalsQuery = "select g from Goal g where g.nome='"+g.getNome()+"' and g.logdate= :date  order by g.logfinaldate ASC";
            Query qu = this.em.createQuery(goalsQuery);
            qu.setParameter("date", g.getLogdate(),TemporalType.TIMESTAMP);
            List<Goal> goals = (List<Goal>) qu.getResultList();
            
            return goals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
}
