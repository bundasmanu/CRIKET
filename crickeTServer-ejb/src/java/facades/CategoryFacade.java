/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Category;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author bruno
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> implements CategoryFacadeLocal {

    @PersistenceContext(unitName = "crickeTServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    @Override
    public Category findByName(String name){
        
        Category c=null;
        try{
            
            Query qu= this.em.createNamedQuery("Category.findByNome");
            qu.setParameter("nome", name);
            /*SO PODE EXISTIR UMA COMPANHIA COM AQUELE NOME*/
            c= (Category) qu.getSingleResult();
            
            return c;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
    @Override
    public List<Category> findMultipleNames(List<String> x){
        
        try{
            
            Query query = em.createQuery("select c FROM Category c WHERE c.nome IN :nomes");
            query.setParameter("nomes", x);
            List<Category> cat=(List<Category>)query.getResultList();
            
            return cat;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
    }
    
}
