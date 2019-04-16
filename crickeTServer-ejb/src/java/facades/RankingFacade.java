/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Category;
import entities.Ranking;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author gustavo
 */
@Stateless
public class RankingFacade extends AbstractFacade<Ranking> implements RankingFacadeLocal {

    @PersistenceContext(unitName = "crickeTServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RankingFacade() {
        super(Ranking.class);
    }
    
    @Override
    public Ranking findByName(String name){
        
        try{
            Ranking r=null;
            Query qu= this.em.createNamedQuery("Ranking.findByNome");
            qu.setParameter("nome", name);
            /*SO PODE EXISTIR UMA COMPANHIA COM AQUELE NOME*/
            r= (Ranking) qu.getSingleResult();
            
            return r;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Ranking> findMultipleNames(List<String> x){
        
        try{
            
            Query query = em.createQuery("select r FROM Ranking r WHERE r.nome IN :nomes");
            query.setParameter("nomes", x);
            List<Ranking> rank=(List<Ranking>)query.getResultList();
            
            return rank;
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
}
