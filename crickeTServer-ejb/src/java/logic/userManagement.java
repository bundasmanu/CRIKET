/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import facades.RankingFacadeLocal;
import facades.UtilizadorFacadeLocal;
import java.util.Date;
import entities.*;
import facades.*; 
import javax.ejb.EJB;
import javax.ejb.Singleton;
import entities.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;

/**
 *
 * @author gustavo
 */
@Singleton
public class userManagement implements userManagementLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    UtilizadorFacadeLocal user;
    
    @EJB
    RankingFacadeLocal ranks;
    
    @EJB
    categoryManagementLocal cat;
    
    @Override
    public boolean signUp(String username, String pass, String email, String gender, Date birth){
        
        try{
            
            /*VERIFICAR PRIMEIRO SE JA EXISTE ALGUM USER, COM ESSE EMAIL*/
            Utilizador x=user.findByEmail(email);
            
            if(x!=null){
                return false;
            }
            
            /*OBTENCAO DA PRIMEIRA INSTANCIA, PRIMEIRO NIVEL DO RANK*/
            List<Ranking> r=ranks.findAll();
            if(r.isEmpty()==true){
                return false;
            }
            Ranking novo_ad=r.get(0);
            
            /*É CRIADA ENTAO UMA LINHA PARA O NOVO USER*/
            
            Utilizador newUser= new Utilizador(email, pass, username, birth, gender);
            newUser.setIdRank(novo_ad);
                    
            user.create(newUser);
            
            /*SE CHEGAR AQUI É PORQUE CRIOU USER, ENTAO BASTA CHAMAR A CRIACAO DE ENTIDADES GENERICAS PARA UM USER*/
            this.cat.initializeCategories(email);
            
            return true;
        }
        catch(Exception e){
            System.out.println("Estoiro");
            return false;
        }
        
    }

    @Override
    public boolean validateLogin(String email, String pass) {
        try {
            //verify if the email of a specific user exists
            Utilizador return_user = this.user.findByEmail(email);

            //if not exist
            if (return_user == null || (return_user != null && return_user.getPassword().equals(pass) == false)) {
                return false;
            }

        } catch (Exception e) {
            System.out.println("" + e.getMessage()); 
            return false;
        }
        return true;
    }
    
    @Asynchronous
    public Future<Integer> getNextValueFromGoalOrder(String email){
        
        try{
            
            /*VERIFICAR INICIALMENTE SE O USER EXISTE*/
            Utilizador exist=this.user.findByEmail(email);
            
            if(exist==null){
                return new AsyncResult<>(-1);
            }
            
            Collection<Category> catCollect=exist.getCategoryCollection();
            
            if(catCollect.isEmpty()==true){
                return new AsyncResult<>(1);/*SENAO EXISTIR NENHUM OBJETIVO, O PRIMEIRO VALOR DE ORDEM DO OBJETIVO É 1*/
            }
            
            /*PERCORRER ARRAYLIST E VERIFICAR QUAL O ELEMENTO MÁXIMO DE ORDEM*/
            List<Integer> OrderValues=new ArrayList<Integer>();
            for(Category c : catCollect){
                if(c.getGoalCollection().isEmpty()==false){
                    for(Goal g : c.getGoalCollection()){
                        OrderValues.add(g.getFlagOrder());
                    }
                }
            }
            
            /*OBTENCAO DO VALOR MAXIMO DE GOAL*/
            Integer maxV = Collections.max(OrderValues);
            
            return new AsyncResult<>(maxV+1);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return new AsyncResult<>(-1);
        }
        
    }

}
