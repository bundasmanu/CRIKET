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
import java.time.LocalTime;
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
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author gustavo
 */
@Singleton
@Startup
public class DailyTimer implements DailyTimerLocal {

    @Resource
    private TimerService timerService;
    static String timerName = "IntervalTimer_Info";
    
    @EJB
    GoalFacadeLocal goals;
    
    @EJB
    CategoryFacadeLocal cat;
    
    @EJB
    goalManagementLocal goalM;
    
    @PostConstruct
    private void init() {
        timerService.createTimer(1000, secondsToMiliSeconds(60), timerName);
    }
    
    @Timeout
    public void incrementDate(Timer timer) {
        
        LocalTime atualTime = LocalTime.now();
        LocalTime minTime = LocalTime.of(00, 00, 00, 00000);
        LocalTime maxTime = LocalTime.of(00, 01, 00, 00000);

        
        System.out.println("\n\n Date: " + atualTime);
        if(atualTime.isAfter(minTime) && atualTime.isBefore(maxTime))
        {
            System.err.println("\nENTREI NO TIMER\n");
            this.testDailyGoals();
            this.testWeeklyGoals();
            this.testMonthlyGoals();
            this.testYearGoals();
        }
    }
    
    
    private long secondsToMiliSeconds(long seconds){
        return seconds * 1000;
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
            for (Goal g : listDailyGoals) {
                if (g.getFlagdone() == Boolean.FALSE) {
                    
                    /*TERMINA OBJETIVO*/
                    this.goalM.setGoalAsDone(g);
                    
                    /*APENAS CRIA CLONE, CASO O FINAL DATE SEJA NULL, OU CASO ESTE NAO SEJA NULL, A SUA DATA FINAL, SEJA SUPERIOR A DATA ATUAL*/
                    if ((g.getFinaldate() != null) && this.getDateTimeNow().before(g.getFinaldate()) || (g.getFinaldate() == null)) { 
                        this.createCloneGoal(g);
                    }
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

            if (listWeeklyGoals.isEmpty()) {/*ESTA VAZIA, MAS ESTA TUDO BEM*/
                return true;
            }
            /*VERIFICAR PARA ESTE GOALS, SE O OBJETIVO FOI OU NAO CUMPRIDO, E É NECESSÁRIO CRIAR OUTRO, COM OS MSM DADOS DESTE*/
            for (Goal g : listWeeklyGoals) {
                if (g.getFlagdone()) {
                    continue;
                }
                /*VERIFICAR SE JÁ PASSARAM 7 DIAS DESDE A SUA DATA DE CRIACAO*/
                if (this.getDaysBetweenDates(this.getDateTimeNow(),g.getLogdate())%7 == 0) {
                    if ((g.getFinaldate() != null) && this.getDateTimeNow().before(g.getFinaldate()) || (g.getFinaldate() == null)) {
                        this.createCloneGoal(g);
                    }
                    this.goalM.setGoalAsDone(g);
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
                if (g.getFlagdone()) {
                    continue;
                }
                /*VERIFICAR SE JÁ PASSARAM 7 DIAS DESDE A SUA DATA DE CRIACAO*/
                int daysDistance = this.getDaysOnAMonth(g.getLogdate());
                if (this.getDaysBetweenDates(this.getDateTimeNow(),g.getLogdate())%daysDistance == 0) {
                    if ((g.getFinaldate() != null) && this.getDateTimeNow().before(g.getFinaldate()) || (g.getFinaldate() == null)) {
                        this.createCloneGoal(g);
                    }
                    this.goalM.setGoalAsDone(g);
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
                if (g.getFlagdone()== false && g.getCurrentvalue() >= g.getTotalvalue()) {
                    /*COMPARAR COM O MES ATUAL, VISTO QUE IMAGINE SE QUE FOI SUBMETIDO O OBJETIVO EM DEZEMBRO, E JA ESTAMOS NO ANO A SEGUIR A COMPARAR*/
                    if ((this.isLeapYear(this.getDateTimeNow()) == true && this.getDaysBetweenDates(this.getDateTimeNow(), g.getLogfinaldate()) == 366) || (this.isLeapYear(this.getDateTimeNow()) == false && this.getDaysBetweenDates(this.getDateTimeNow(), g.getLogdate()) == 365)) {
                        if (this.getDateTimeNow().before(g.getFinaldate())) {
                            this.createCloneGoal(g);
                        }
                        this.goalM.setGoalAsDone(g);
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

            Goal x=new Goal(g.getNome(), g.getDescript(), g.getFrequency(), g.getStatus(), g.getTotalvalue(), 0 , g.getFavorite(), g.getLogdate(), g.getFlagClickControl(),g.getFlagOrder() , Boolean.FALSE);
            x.setFinaldate(g.getFinaldate());
            x.setLogfinaldate(null);
            Category c=g.getIdCategory();
            
            x.setIdCategory(c);
            
            c.getGoalCollection().add(x);
            
            this.goals.create(g);
            
            this.cat.edit(c);
            System.err.println("\nCRIADO CLONE\n");
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
            System.out.println("\n\n daysDiff: " + daysDiff);
            return convertDaysDiff;
        }
        catch(Exception e){
            System.out.println("ERROR: " + e.toString());
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

    @Override
    public void CancelTimers() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
