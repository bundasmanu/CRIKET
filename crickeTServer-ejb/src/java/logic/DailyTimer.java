/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import facades.GoalFacadeLocal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
    
    public void testDailyGoals(){
        
    }
    
    public void testWeeklyGoals(){
        
    }
    
    public void testMonthlyGoals(){
        
    }
    
    public void testYearGoals(){
        
    }
    
}
