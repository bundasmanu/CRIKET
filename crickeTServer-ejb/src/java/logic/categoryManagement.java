/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Category;
import facades.CategoryFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author gustavo
 */
@Singleton
public class categoryManagement implements categoryManagementLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    CategoryFacadeLocal cat;
    
    @Override
    public boolean createCategory(String name, String desc){
        
        try{
            
            /*VERIFICAR INICIALMENTE SE NAO EXISTE NENHUM CATEGORIA COM AQUELE NOME*/
            Category exist=this.cat.findByName(name);
            
            /*SE JA EXISTE RETORNA*/
            if(exist!=null){
                return false;
            }
            
            /*COMO A REFERENCIA PARA O OBJETO EXIST ESTA A NULL, POSSO PEGAR NELE*/
            exist=new Category(name, desc);
            
            this.cat.create(exist);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
    @Override
    public boolean removeCategory(String name){
        
        try{
            
            /*VERIFICAR SE EXISTE ALGUMA CATEGORIA A ELIMINAR COM AQUELE NOME*/
            Category c=this.cat.findByName(name);
            
            /*SENAO EXISTE NENHUMA CATEGORIA COM AQUELE NOME RETORNA FALSE*/
            if(c==null){
                return false;
            }
            
            /*REMOVE A CATEGORIA DA TABELA, E APAGA CONSEQUENTEMENTE OS SEUS OBJETIVOS, DEVIDO AO ATRIBUTO CASCADE, DEFINIDO NA COLECÇÃO DE OBJETIVOS*/
            this.cat.remove(c);
            
            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        
    }
    
}
