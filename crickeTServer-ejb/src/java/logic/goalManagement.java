/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import cricketdto.GoalDTO;
import entities.Category;
import entities.DTOFactory;
import entities.Goal;
import entities.Utilizador;
import facades.UtilizadorFacadeLocal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import Utils.Config;
import cricketdto.GoalDTO;
import entities.Goal;
import facades.CategoryFacadeLocal;
import facades.GoalFacadeLocal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author gustavo
 */
@Singleton
public class goalManagement implements goalManagementLocal {

    @EJB
    GoalFacadeLocal goal;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    UtilizadorFacadeLocal ut;

    @EJB
    categoryManagementLocal categoryManagement;

    @EJB
    CategoryFacadeLocal ca;

    private DTOFactory dt = new DTOFactory();

    @Override
    public List<GoalDTO> selectAllGoalsFromAnUser(String email) {

        /*VERIFICAR INICIALMENTE SE O USER EXISTE*/
        Utilizador u = this.ut.findByEmail(email);

        if (u == null) {
            return null;
        }

        /*COMO O UTILIZADOR EXISTE BASTA RETORNAR TODOS OS SEUS OBJETIVOS*/
        Collection<Category> catUser = u.getCategoryCollection();

        List<GoalDTO> retorno_goals_user = new ArrayList<GoalDTO>();

        if (catUser.isEmpty() != true) {
            for (Category x : catUser) {
                if (x.getGoalCollection().isEmpty() != true) {
                    for (Goal g : x.getGoalCollection()) {
                        GoalDTO gt = dt.getGoalDTO(g);
                        retorno_goals_user.add(gt);
                    }
                }
            }
        }

        /*ORDENACAO DOS GOALS CONSOANTE, A FLAG, E DE ACORDO COM O COMPARABLE*/
        Collections.sort(retorno_goals_user);

        return retorno_goals_user;
    }
    
    @Override
    public List<GoalDTO> selectAllGoalsFromUserByClicks(String email){
        
        try{
            
            Utilizador user=this.ut.findByEmail(email);
            
            if(user==null){
                return null;
            }
            
            Collection<Category> userCategories=user.getCategoryCollection();
            
            if(userCategories.isEmpty()==true){
                return null;
            }
            
            List<GoalDTO> allGoalsOfUser=new ArrayList<GoalDTO>();
            
            for(Category c : userCategories){
                if(c.getGoalCollection().isEmpty()==false){
                    for(Goal g : c.getGoalCollection()){
                        allGoalsOfUser.add(DTOFactory.getGoalDTO(g));
                    }
                }
            }
            
            if(allGoalsOfUser.isEmpty()==true){
                return null;
            }
            
            /*ORDENACAO AGORA PELO NUMERO DE CLICKS*/
            Collections.sort(allGoalsOfUser, new GoalDTO());
            
            return allGoalsOfUser;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }

    @Override
    public boolean createGoal(GoalDTO newGoalDTO) {

        try {
            //verify if this goal exists with the same name
            Category cTmp = this.ca.find(newGoalDTO.getIdCategory());
            

            if (cTmp == null) {
                return false;
            }
            
            //if the category has goals...
            if(!cTmp.getGoalCollection().isEmpty()){
                for(Goal goalTmp: cTmp.getGoalCollection())
                {
                    if(goalTmp.getNome().equals(newGoalDTO.getName()))
                        return false;
                }
            }
            
            Goal newGoal = new Goal();
            newGoal.setCurrentvalue(newGoalDTO.getCurrentValue());
            newGoal.setDescript(newGoalDTO.getDesc());
            newGoal.setFavorite(newGoalDTO.getFavorite());
            newGoal.setFinaldate(newGoalDTO.getFinalDate());
            newGoal.setLogdate(newGoalDTO.getLogDate());
            newGoal.setFlagClickControl(newGoalDTO.getFlagClick());
            newGoal.setFlagOrder(newGoalDTO.getFlag_order());
            newGoal.setNome(newGoalDTO.getName());
            newGoal.setStatus(newGoalDTO.getStatus());
            newGoal.setTotalvalue(newGoalDTO.getTotalValue());
            newGoal.setFrequency(newGoalDTO.getFrequency());
            newGoal.setIdCategory(cTmp);

            //persist on database the respective goal
            //this.goal.create(newGoal);

            
            cTmp.getGoalCollection().add(newGoal);
            
            categoryManagement.save(cTmp);
            
            return true;

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }

    }

    @Override
    public boolean editGoal(GoalDTO editGoalDTO) {

        try {
            Goal goalToEdit = this.goal.find(editGoalDTO.getId_goal());

            Category cat = this.ca.find(editGoalDTO.getIdCategory());

            if (goal == null || cat == null) {
                return false;
            }

            goalToEdit.setCurrentvalue(editGoalDTO.getCurrentValue());
            goalToEdit.setDescript(editGoalDTO.getDesc());
            goalToEdit.setFavorite(editGoalDTO.getFavorite());
            goalToEdit.setFinaldate(editGoalDTO.getFinalDate());
            goalToEdit.setFlagClickControl(editGoalDTO.getFlagClick());
            goalToEdit.setFlagOrder(editGoalDTO.getFlag_order());
            goalToEdit.setIdGoal(editGoalDTO.getId_goal());
            goalToEdit.setLogdate(editGoalDTO.getLogDate());
            goalToEdit.setNome(editGoalDTO.getName());
            goalToEdit.setStatus(editGoalDTO.getStatus());
            goalToEdit.setTotalvalue(editGoalDTO.getTotalValue());
            goalToEdit.setIdCategory(cat);

            categoryManagement.save(cat);
            this.goal.edit(goalToEdit);

            return true;
        } catch (Exception e) {
            System.out.println("Mensagem: " + e.getMessage());

            return false;
        }
    }

    @Override
    public boolean removeGoal(String email, Integer id) {
        try {
            //find user
            Utilizador u = this.ut.findByEmail(email);
            if (u == null) {
                return false;
            }

            //find goal by id
            Goal g = this.goal.find(id);

            if (g == null) {
                return false;
            }
            
            Category category = g.getIdCategory();
            category.getGoalCollection().remove(g);
            categoryManagement.save(category);
            
            //remove the goal
            this.goal.remove(g);
            return true;
        } catch (Exception e) {
            System.out.println("" + e.getMessage());
            return false;
        }

    }
    
    @Asynchronous
    @Override
    public Future<Integer> getNextValueGoal(String email){
        
        try{
            
            /*VERIFICAR INICIALMENTE SE O UTILIZADOR EXISTE*/
            Utilizador u=this.ut.findByEmail(email);
            
            if(u==null){
                return new AsyncResult<>(-2);
            }
            
            /*VERIFICAR SE JA EXISTEM GOALS PARA ESSE USER*/
            Collection catUser=u.getCategoryCollection();
            if(catUser.isEmpty()==true){
                return new AsyncResult<>(1);/*NAO EXISTEM ELEMENTOS ENTAO O PRIMEIRO ELEMENTO É 1*/
            }
            
            List<Integer> goals=new ArrayList<Integer>();
            for(Category c : u.getCategoryCollection()){
                if(c.getGoalCollection().isEmpty()==false){
                    for(Goal g : c.getGoalCollection()){
                        goals.add(g.getIdGoal());
                    }
                }
            }
            
            /*SE TENHO CATEGORIAS, MAS NAO TENHO GOALS, RETORNA 1*/
            if(goals.isEmpty()==true){  
                return new AsyncResult<>(1);
            }
            
            /*OBTENCAO DO MAIOR VALOR DO GOAL*/
            Integer maxValue=Collections.max(goals);
            
            return new AsyncResult<>(maxValue+1);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new AsyncResult<>(-1);
        }
        
    }

    @Override
    public GoalDTO findGoalDTOById(int id) {
        Goal goalTmp = goal.find(id);
        
        if(goalTmp == null)
            return null;
        return DTOFactory.getGoalDTO(goalTmp);
    }
    
    @Override
    public boolean increaseCurrentValue(GoalDTO goal){
        
        try{
            
            Goal goalI=this.goal.find(goal.getId_goal());            
            if(goalI==null){
                return false;
            }
            
            if(goalI.getCurrentvalue() + 1 <= goalI.getTotalvalue())
            {
                goalI.setCurrentvalue(goalI.getCurrentvalue()+1);
                this.goal.edit(goalI);
            }
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean decreaseCurrentValue(GoalDTO goal){
             
        try{
            Goal goalI=this.goal.find(goal.getId_goal());
            
            if(goalI==null){
                return false;
            }
            
            if(goalI.getCurrentvalue() - 1 >= 0)
            {
                goalI.setCurrentvalue(goalI.getCurrentvalue()-1);
                this.goal.edit(goalI);
            }
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean goalIsEnd(GoalDTO goal){
        
        try{
            
            /*VERIFICAR SE O GOAL EXISTE*/
            Goal gT=this.goal.find(goal.getId_goal());
            
            /*PARA NAO CONFUNDIR O FALSE, DE NAO EXISTE COM ESTOIRO*/
            if(gT==null){
                throw new Exception();
            }
            
            //not the best way to get the actual date... but it's functional :D
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = LocalDate.now().format(formatterLocalDate);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
            Date actualDate = formatter.parse(formattedString);
            
            System.out.println("\n\n\n atual date: " + actualDate);
            System.out.println("\n\n\n goal final date: " + gT.getFinaldate());

            
            /*VERIFICAR O CURRENT VALUE, E AS DATA DE CONCLUSAO*/
            if(gT.getCurrentvalue()>=gT.getTotalvalue() || isEnd(gT.getFinaldate(), actualDate)){
                return true;
            }
            
            return false;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
     public static boolean isEnd(Date date1,Date date2){
         
        if(date1 == null) //since it can be null (not defined final date)
            return false;

        if(date1.equals(date2)){
            return true;
        }
         
        if(date1.before(date2)){
            return true;
        }

        return false;
    }

    @Override 
    public boolean downOrderValue(GoalDTO goal){
        
        try{
            
            /*VERIFICAR, SE O GOAL EXISTE--> NAO ERA NECESSARIO, MAS PRONTO*/
            List<Goal> listOfGoals=this.goal.findAll();
            
            if(listOfGoals==null){
                return false;
            }
            
            /*VERIFICAR SE EXISTE O GOAL*/
            for(Goal g : listOfGoals){
                if(g.getIdGoal().equals(goal.getId_goal())==true){
                    /*TROCAR O VALOR DO ORDER DESTE ELEMENTO COM O ELEMENTO QUE ESTAVA ATRAS*/
                    g.setFlagOrder(g.getFlagOrder()-1);
                    int posGoal=listOfGoals.indexOf(g);
                    Goal increaseGoal=listOfGoals.get(posGoal-1);
                    if(increaseGoal!=null){
                        increaseGoal.setFlagOrder(increaseGoal.getFlagOrder()+1);
                        this.goal.edit(increaseGoal);
                    }
                    this.goal.edit(g);
                    return true;
                }
            }
            
            
            return false;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean upOrderValue(GoalDTO goal){
        
        try {
            
             /*VERIFICAR, SE O GOAL EXISTE--> NAO ERA NECESSARIO, MAS PRONTO*/
            List<Goal> listOfGoals=this.goal.findAll();
            
            if(listOfGoals==null){
                return false;
            }
            
            /*VERIFICAR SE EXISTE O GOAL*/
            for(Goal g : listOfGoals){
                if(g.getIdGoal().equals(goal.getId_goal())==true){
                    /*TROCAR O VALOR DO ORDER DESTE ELEMENTO COM O ELEMENTO QUE ESTA À FRENTE*/
                    g.setFlagOrder(g.getFlagOrder()+1);
                    int posGoal=listOfGoals.indexOf(g);
                    Goal decreaseGoal=listOfGoals.get(posGoal+1);
                    if(decreaseGoal!=null){
                        decreaseGoal.setFlagOrder(decreaseGoal.getFlagOrder()-1);
                        this.goal.edit(decreaseGoal);
                    }
                    this.goal.edit(g);
                    return true;
                }
            }  
            
            return false;
            
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean increaseClickFlag(GoalDTO goal){
         
        try{
            
            Goal goalClick=this.goal.find(goal.getId_goal());            
            
            if(goalClick==null){
                return false;
            }
            
            goalClick.setFlagClickControl(goalClick.getFlagClickControl()+1);
            
            this.goal.edit(goalClick); 
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public List<GoalDTO> getGoalsBetweenTwoDates(String email, Date d1, Date d2){
        
        try{
            
            /*VERIFICAR PRIMEIRO SE O UTILIZADOR EXISTE*/
            Utilizador u=this.ut.findByEmail(email);
            
            if(u==null){
                return null;
            }
            
            /*PRIMEIRO PASSAR AS DATAS PARA O FORMATO CORRETO*/
            d1=this.getDateInRightFormat(d1);
            d2=this.getDateInRightFormat(d2);
            
            if(d1==null || d2==null){
                return null;
            }
            
            List<Goal> getListOfGoalsBetweenDates=this.goal.getGoalsBetweenDates(u,d1, d2);
            
            if(getListOfGoalsBetweenDates==null){
                return null;
            }
            
            /*CONVERSAO DE GOAL, PARA GOAL DTO*/
            List<GoalDTO> listGoals=new ArrayList<GoalDTO>();
            for(Goal g : getListOfGoalsBetweenDates){
                listGoals.add(DTOFactory.getGoalDTO(g));
            }
            
            return listGoals;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    public Date getDateInRightFormat(Date d1){
        
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
    
    public List<GoalDTO> orderGoalsBetweenDate(String email){
        
        try{
            
            /*VERIFICAR SE O USER EXISTE*/
            Utilizador u=this.ut.findByEmail(email);
            
            if(u==null){
                return null;
            }
            
            if(u.getCategoryCollection().isEmpty()==true){
                return null;
            }
            
            List<GoalDTO> listGoalsOfAnUser = new ArrayList<GoalDTO>();
            for(Category c : u.getCategoryCollection()){
                if(c.getGoalCollection().isEmpty()==false){
                    for(Goal g : c.getGoalCollection()){
                        listGoalsOfAnUser.add(DTOFactory.getGoalDTO(g));    
                    }
                }
            }
            
            if(listGoalsOfAnUser.isEmpty()==true){
                return null;
            }
            
            /*ORDENA OS OBJETIVOS, UTILIZANDO A CLASSE CRIADA PARA O EFEITO*/
            List<GoalDTO> orderedListofGoalsByDate=GoalDTO.retGoalsOrderByDate(listGoalsOfAnUser);
            
            return orderedListofGoalsByDate;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
     
}
