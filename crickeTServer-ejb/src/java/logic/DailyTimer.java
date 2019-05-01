/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Category;
import entities.Goal;
import facades.CategoryFacadeLocal;
import facades.GoalFacadeLocal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author gustavo
 */
@Singleton
public class DailyTimer implements DailyTimerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Resource
    private SessionContext cont;
    
    private TimerService t;
    
    String dailyTimer="Daily"; /*VARIAVEL QUE CONTROLA O TIMER ESPECIFICO*/
    
    @EJB
    GoalFacadeLocal goals;
    
    @EJB
    CategoryFacadeLocal cat;
    
    @PostConstruct
    public void startCrono(){
        CancelTimers();
        t= cont.getTimerService();
    }
    
    @PreDestroy
    public void para(){
        CancelTimers();
    }
    
    @Override
    public void CancelTimers(){
        TimerService timers_em_execucao= this.cont.getTimerService();
        Collection<Timer> cronos= timers_em_execucao.getTimers();
        /*APAGAR TODOS OS TIMERS*/
        for(Timer x : cronos){
            if(x.getInfo().equals(this.dailyTimer)==true){
                x.cancel();
            }
        }
    }
    
    @Schedule(hour="0", dayOfWeek ="*"  , info="RunsEveryDay")
    public void timeoutEveryDay() { 
        
    }
    
    /*METODOS CHAMADOS NO TIMEOUT*/
    
    public boolean testDailyGoals(){
        
        try{
            
            /*OBTENCAO DOS OBJETIVOS DAILY*/
            List<Goal> listDailyGoals=this.goals.getDailyGoals();
            
            if(listDailyGoals==null){/*HOUVE ERRO*/
                return false;
            }
            
            if(listDailyGoals.isEmpty()==true){/*ESTA VAZIA, MAS ESTA TUDO BEM*/
                return true;
            }
            
            /*VERIFICAR PARA ESTE GOALS, SE O OBJETIVO FOI OU NAO CUMPRIDO, E É NECESSÁRIO CRIAR OUTRO, COM OS MSM DADOS DESTE*/
            
            for(Goal g : listDailyGoals){
                if(g.getFlagdone()==Boolean.FALSE){
                    this.createCloneGoal(g);
                    /*ATUALIZA A FLAG*/
                    g.setFlagdone(Boolean.TRUE);
                    this.goals.edit(g);
                    this.cat.edit(g.getIdCategory());
                }
            }
            
            return true;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public boolean testWeeklyGoals(){
        
        try {

            /*OBTENCAO DOS OBJETIVOS WEEEKLY*/
            List<Goal> listWeeklyGoals = this.goals.getWeeklyGoals();

            if (listWeeklyGoals == null) {/*HOUVE ERRO*/
                return false;
            }

            if (listWeeklyGoals.isEmpty() == true) {/*ESTA VAZIA, MAS ESTA TUDO BEM*/
                return true;
            }

            /*VERIFICAR PARA ESTE GOALS, SE O OBJETIVO FOI OU NAO CUMPRIDO, E É NECESSÁRIO CRIAR OUTRO, COM OS MSM DADOS DESTE*/
            for (Goal g : listWeeklyGoals) {
                if (g.getFlagdone() == Boolean.FALSE && this.getDaysBetweenDates(this.getDateTimeNow(), g.getLogdate())==7) { /*VERIFICAR SE JÁ PASSARAM 7 DIAS DESDE A SUA DATA DE CRIACAO*/
                    this.createCloneGoal(g);
                    /*ATUALIZA A FLAG*/
                    g.setFlagdone(Boolean.TRUE);
                    this.goals.edit(g);
                    this.cat.edit(g.getIdCategory());
                }
            }

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public boolean testMonthlyGoals(){
        
        try {

            /*OBTENCAO DOS OBJETIVOS MONTHLY*/
            List<Goal> listMonthlyGoals = this.goals.getMonthlyGoals();

            if (listMonthlyGoals == null) {/*HOUVE ERRO*/
                return false;
            }

            if (listMonthlyGoals.isEmpty() == true) {/*ESTA VAZIA, MAS ESTA TUDO BEM*/
                return true;
            }

            /*VERIFICAR PARA ESTE GOALS, SE O OBJETIVO FOI OU NAO CUMPRIDO, E É NECESSÁRIO CRIAR OUTRO, COM OS MSM DADOS DESTE*/
            for (Goal g : listMonthlyGoals) {
                if (g.getFlagdone() == Boolean.FALSE) {
                    /*COMPARAR COM O MES ATUAL, VISTO QUE IMAGINE SE QUE FOI SUBMETIDO O OBJETIVO EM DEZEMBRO, E JA ESTAMOS NO ANO A SEGUIR A COMPARAR*/
                    int daysDistance=this.getDaysOnAMonth(g.getLogdate());
                    if(this.getDaysBetweenDates(g.getLogdate(), this.getDateTimeNow())==daysDistance){
                        this.createCloneGoal(g);
                        /*ATUALIZA A FLAG*/
                        g.setFlagdone(Boolean.TRUE);
                        this.goals.edit(g);
                        this.cat.edit(g.getIdCategory());
                    }

                }
            }

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public boolean testYearGoals(){
        
        try {

            /*OBTENCAO DOS OBJETIVOS MONTHLY*/
            List<Goal> listYearlyGoals = this.goals.getYearGoals();

            if (listYearlyGoals == null) {/*HOUVE ERRO*/
                return false;
            }

            if (listYearlyGoals.isEmpty() == true) {/*ESTA VAZIA, MAS ESTA TUDO BEM*/
                return true;
            }

            /*VERIFICAR PARA ESTE GOALS, SE O OBJETIVO FOI OU NAO CUMPRIDO, E É NECESSÁRIO CRIAR OUTRO, COM OS MSM DADOS DESTE*/
            for (Goal g : listYearlyGoals) {
                if (g.getFlagdone() == Boolean.FALSE) {
                    /*COMPARAR COM O MES ATUAL, VISTO QUE IMAGINE SE QUE FOI SUBMETIDO O OBJETIVO EM DEZEMBRO, E JA ESTAMOS NO ANO A SEGUIR A COMPARAR*/
                    if ((this.isLeapYear(this.getDateTimeNow()) == true && this.getDaysBetweenDates(this.getDateTimeNow(), g.getLogdate()) == 366) || (this.isLeapYear(this.getDateTimeNow()) == false && this.getDaysBetweenDates(this.getDateTimeNow(), g.getLogdate()) == 365)) {
                        this.createCloneGoal(g);
                        /*ATUALIZA A FLAG*/
                        g.setFlagdone(Boolean.TRUE);
                        this.goals.edit(g);
                        this.cat.edit(g.getIdCategory());
                    }

                }
            }

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public boolean createCloneGoal(Goal g){
        
        try{
            
            Goal x=new Goal(g.getNome(), g.getDescript(), g.getFrequency(), g.getStatus(), g.getTotalvalue(), g.getCurrentvalue(), g.getFavorite(), g.getLogdate(), g.getFlagClickControl(),g.getFlagOrder() , Boolean.FALSE);
            Category c=g.getIdCategory();
            
            x.setIdCategory(c);
            
            c.getGoalCollection().add(x);
            
            this.goals.create(g);
            
            this.cat.edit(c);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public int getDaysBetweenDates(Date d1, Date d2){
        
        try{
            
            long timeDiff=d1.getTime()-d2.getTime();
            
            long daysDiff=TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
            
            int convertDaysDiff= (int) daysDiff;
            
            return convertDaysDiff;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
    public Date getDateTimeNow(){
        
        try{
            
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = LocalDate.now().format(formatterLocalDate);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
            Date actualDate = formatter.parse(formattedString);
            
            return actualDate;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
   
    public boolean isLeapYear(Date d1){
        
        try{

            if ((d1.getYear() % 4 == 0) && d1.getYear() % 100 != 0) {
                return true;
            } else if ((d1.getYear() % 4 == 0) && (d1.getYear() % 100 == 0) && (d1.getYear() % 400 == 0)) {
                return true;
            } else {
                return false;
            }
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    public int getDaysOnAMonth(Date d1){
        
        try {

            if (d1.getMonth()== 2 && this.isLeapYear(d1)==false) {
                return 28;
            } 
            else if(d1.getMonth()== 2 && this.isLeapYear(d1)==true){
                return 29;
            }
            else if (d1.getMonth()==1 || d1.getMonth()==3 || d1.getMonth()==5 || d1.getMonth()==7 || d1.getMonth()==8 || d1.getMonth()==10 || d1.getMonth()==12) {
                return 31;
            } else {
                return 30;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
        
    }
    
}
