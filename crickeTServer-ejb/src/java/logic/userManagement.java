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
import java.util.List;

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

}
