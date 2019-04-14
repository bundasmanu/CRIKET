/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Utilizador;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gustavo
 */
@Stateless
public class UtilizadorFacade extends AbstractFacade<Utilizador> implements UtilizadorFacadeLocal {

    @PersistenceContext(unitName = "crickeTServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilizadorFacade() {
        super(Utilizador.class);
    }
    
    @Override
    public Utilizador findByEmail(String email) {
        
        Utilizador verifica_encontrado=null;
        try{
            Query qu= this.em.createNamedQuery("Utilizador.findByEmail");
            qu.setParameter("email", email);
            verifica_encontrado= (Utilizador) qu.getSingleResult();
        }catch(Exception e){
            System.out.println(""+e.getMessage());
            return null;
        }
        return verifica_encontrado;
    }
    
}
