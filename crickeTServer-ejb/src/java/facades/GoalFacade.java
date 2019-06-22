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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    public List<Goal> findAllAndOrderByFlag(String email) {
        
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<Goal> goal = cq.from(Goal.class);
        
        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        //Adding predicates in case of parameter not being null
        predicates.add(cb.like(goal.get("idCategory").get("idUser").get("email"), email));
        //just show the goals which are not setted as done. A done goal is listed only when we want see the history
        predicates.add(
                  cb.equal(goal.get("flagdone"), false));
        
        cq.orderBy(cb.asc(goal.get("flagOrder")));

        //query itself
        cq.select(goal)
                .where(predicates.toArray(new Predicate[]{}));
        //execute query and do something with result
        return em.createQuery(cq).getResultList();
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

    @Override
    public List<Goal> findAllNotDonePurchasesOfUser(String filterName, String filterSinceDate, String filterUntilDate) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<Goal> goal = cq.from(Goal.class);
        
        //Constructing list of parameters
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        //Adding predicates in case of parameter not being null
        if (filterSinceDate != null && !filterSinceDate.isEmpty()) {
            
            try{
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date sinceDateGoal = formatter.parse(filterSinceDate);
                
                predicates.add(cb.greaterThanOrEqualTo(goal.<Date>get("logdate"), sinceDateGoal));
                
            }catch(Exception e){
                System.out.println("Error parsing data. Error: " + e);
            }
        }
    
        //Adding predicates in case of parameter not being null
        if (filterUntilDate != null && !filterUntilDate.isEmpty()) {
            
            try{

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date untilDateGoal = formatter.parse(filterUntilDate);
                
                        
                predicates.add(cb.lessThanOrEqualTo(goal.<Date>get("logdate"), untilDateGoal));
            }catch(Exception e){
                System.out.println("Error parsing data. Error: " + e);
            }   
        }
        
        //Adding predicates in case of parameter not being null
        if (filterName != null && !filterName.isEmpty()) {
            predicates.add(
                    cb.like(goal.get("nome"), filterName));
        }
        
        //just show the goals which are not setted as done. A done goal is listed only when we want see the history
        predicates.add(
                  cb.equal(goal.get("flagdone"), false));

        //query itself
        cq.select(goal)
                .where(predicates.toArray(new Predicate[]{}));
        //execute query and do something with result
        return em.createQuery(cq).getResultList();
        
    }
    
}
