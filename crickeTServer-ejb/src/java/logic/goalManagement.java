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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    GoalFacadeLocal goalFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    UtilizadorFacadeLocal ut;

    @EJB
    categoryManagementLocal categoryManagement;

    @EJB
    CategoryFacadeLocal ca;
    
    @EJB
    rankingManagementLocal rankManagementLocal;

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
    public List<GoalDTO> selectAllNotDoneGoalsFromAnUser(String email) {

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
                        if(!g.getFlagdone())
                        {
                            GoalDTO gt = dt.getGoalDTO(g);
                            retorno_goals_user.add(gt);
                        }
                    }
                }
            }
        }

        /*ORDENACAO DOS GOALS CONSOANTE, A FLAG, E DE ACORDO COM O COMPARABLE*/
        Collections.sort(retorno_goals_user);

        return retorno_goals_user;
    }
    
    @Override
    public List<GoalDTO> selectAllDoneGoalsFromAnUser(String email) {

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
                        if(g.getFlagdone())
                        {
                            GoalDTO gt = dt.getGoalDTO(g);
                            retorno_goals_user.add(gt);
                        }
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
            Goal goalToEdit = this.goalFacade.find(editGoalDTO.getId_goal());

            Category newCat = this.ca.find(editGoalDTO.getIdCategory());

            if (goalFacade == null || newCat == null) {
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
            goalToEdit.setFrequency(editGoalDTO.getFrequency());
            
            //process the category 
            Category actualCategory = goalToEdit.getIdCategory();
            if(!actualCategory.getIdCategory().equals(newCat.getIdCategory()))
            {
                goalToEdit.setIdCategory(newCat);
                actualCategory.getGoalCollection().remove(goalToEdit);
                newCat.getGoalCollection().add(goalToEdit);    
                categoryManagement.save(actualCategory);
            }
            
            categoryManagement.save(newCat);
            this.goalFacade.edit(goalToEdit);

            return true;
        } catch (Exception e) {
            System.out.println("Mensagem: " + e.getMessage());

            return false;
        }
    }

    @Override
    public boolean removeGoal(String email, Integer id) {
        try {
            //find goal by id
            Goal g = this.goalFacade.find(id);

            if (g == null) {
                return false;
            }
            
            Category category = g.getIdCategory();
            category.getGoalCollection().remove(g);
            categoryManagement.save(category);
            
            //remove the goal
            this.goalFacade.remove(g);
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
        Goal goalTmp = goalFacade.find(id);
        
        if(goalTmp == null)
            return null;
        return DTOFactory.getGoalDTO(goalTmp);
    }
    
    @Override
    public boolean increaseCurrentValue(GoalDTO goal){
        
        try{
            
            Goal goalI=this.goalFacade.find(goal.getId_goal());            
            if(goalI==null){
                return false;
            }
            
            if(goalI.getCurrentvalue() + 1 <= goalI.getTotalvalue())
            {
                goalI.setCurrentvalue(goalI.getCurrentvalue()+1);
                this.goalFacade.edit(goalI);
            }
            
            //if the goal isn't setted as done... set it as done
            if(!goalI.getFlagdone() && goalI.getCurrentvalue() == goalI.getTotalvalue() && goalI.getFrequency().equals(Config.NEVER))
                this.setGoalAsDone(goalI);
            
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
            Goal goalI=this.goalFacade.find(goal.getId_goal());
            
            if(goalI==null){
                return false;
            }
            
            if(goalI.getCurrentvalue() - 1 >= 0)
            {
                goalI.setCurrentvalue(goalI.getCurrentvalue()-1);
                this.goalFacade.edit(goalI);
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
            Goal gT=this.goalFacade.find(goal.getId_goal());
            
            /*PARA NAO CONFUNDIR O FALSE, DE NAO EXISTE COM ESTOIRO*/
            if(gT==null){
                throw new Exception();
            }
            
            //not the best way to get the actual date... but it's functional :D
            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = LocalDate.now().format(formatterLocalDate);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");   
            Date actualDate = formatter.parse(formattedString);
            
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
         
        if(date1 == null || date2==null) //since it can be null (not defined final date)
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
            List<Goal> listOfGoals=this.goalFacade.findAllAndOrderByFlag();
            
            if(listOfGoals==null){
                return false;
            }
            
            /*VERIFICAR SE EXISTE O GOAL*/
            for(Goal g : listOfGoals){
                if(g.getIdGoal().equals(goal.getId_goal())==true){
                    /*TROCAR O VALOR DO ORDER DESTE ELEMENTO COM O ELEMENTO QUE ESTAVA ATRAS*/
                    
                    int posGoal=listOfGoals.indexOf(g);
                    Goal increaseGoal=listOfGoals.get(posGoal+1);
                    if(increaseGoal!=null){
                        int tmp = g.getFlagOrder();
                        g.setFlagOrder(increaseGoal.getFlagOrder());
                        increaseGoal.setFlagOrder(tmp);
                        this.goalFacade.edit(increaseGoal);
                        this.goalFacade.edit(g);

                    }
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
            List<Goal> listOfGoals=this.goalFacade.findAllAndOrderByFlag();
            
            if(listOfGoals==null){
                return false;
            }
            
            /*VERIFICAR SE EXISTE O GOAL*/
            for(Goal g : listOfGoals){
                if(g.getIdGoal().equals(goal.getId_goal())==true){
                    /*TROCAR O VALOR DO ORDER DESTE ELEMENTO COM O ELEMENTO QUE ESTA À FRENTE*/
                    int posGoal=listOfGoals.indexOf(g);
                    Goal decreaseGoal=listOfGoals.get(posGoal-1);
                    if(decreaseGoal!=null){
                        int tmp = g.getFlagOrder();
                        g.setFlagOrder(decreaseGoal.getFlagOrder());
                        decreaseGoal.setFlagOrder(tmp);
                        this.goalFacade.edit(decreaseGoal);
                        this.goalFacade.edit(g);
                    }
                    
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
            
            Goal goalClick=this.goalFacade.find(goal.getId_goal());            
            
            if(goalClick==null){
                return false;
            }
            
            goalClick.setFlagClickControl(goalClick.getFlagClickControl()+1);
            
            this.goalFacade.edit(goalClick); 
            
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
            
            List<Goal> getListOfGoalsBetweenDates=this.goalFacade.getGoalsBetweenDates(u,d1, d2);
            
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

    @Override
    public boolean setGoalAsDone(Goal goalTmp) {
       
        try {

            if (goalTmp == null) {
                return false;
            }
            
            goalTmp.setFlagdone(true);

            DateTimeFormatter formatterLocalDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedString = LocalDate.now().format(formatterLocalDate);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date actualDate = formatter.parse(formattedString);

            goalTmp.setLogfinaldate(actualDate);

            goalFacade.edit(goalTmp);
            categoryManagement.save(goalTmp.getIdCategory());
            
            System.out.println("\nENTROU SET GOAL AS DONE\n");
            
            /*DEPOIS DE UM OBJETIVO ESTAR DONE, VAMOS VERIFICAR O RANKING DE UM UTILIZADOR*/
            rankManagementLocal.getDailyStrike(goalTmp);

            return true;

        } catch (ParseException ex) {
            Logger.getLogger(goalManagement.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            
    }

    @Override
    public boolean recoveryDoneGoal(Integer id) {
        
        try {
            Goal goalToRecover = this.goalFacade.find(id);

            Category cat = goalToRecover.getIdCategory();

            if (goalFacade == null || cat == null) {
                return false;
            }

            goalToRecover.setCurrentvalue(0);
            goalToRecover.setLogfinaldate(null);
            goalToRecover.setFlagdone(false);
            
            
            
            categoryManagement.save(cat);
            this.goalFacade.edit(goalToRecover);

            return true;
        } catch (Exception e) {
            System.out.println("Mensagem: " + e.getMessage());

            return false;
        }
        
        
    }

    @Override
    public List<GoalDTO> processGoalsFilter(String filterName, String filterSinceDate, String filterUntilDate) {
        List<Goal> filteredGoals = goalFacade.findAllNotDonePurchasesOfUser(filterName, filterSinceDate, filterUntilDate);
        List<GoalDTO> goalDTOList;
        if(filteredGoals == null)
            return null;
        goalDTOList = new ArrayList();
        
        for(Goal g : filteredGoals){
            GoalDTO gt = dt.getGoalDTO(g);
            goalDTOList.add(gt);
        }
        return goalDTOList;
    }
     
}
