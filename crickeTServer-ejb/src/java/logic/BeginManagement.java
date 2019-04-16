/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Category;
import entities.Ranking;
import facades.CategoryFacadeLocal;
import facades.RankingFacadeLocal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author gustavo
 */
@Startup
@Singleton
public class BeginManagement implements BeginManagementLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    RankingFacadeLocal r;
    
    @EJB
    CategoryFacadeLocal c;
    
    @EJB
    categoryManagementLocal catL;
    
    @EJB
    rankingManagementLocal rankL;
            
    List<Category> cat=null;
    List<Ranking> rank=null;
    List<String> lista_cat=null;
    List<String> lista_rank=null;
    List<Integer> lista_points_Rank=null;
    
    @PostConstruct
    public void postConstruct(){
        lista_cat=Arrays.asList("Saude","Desporto");
        lista_rank=Arrays.asList("Beginner","Amador","Intermedio","Profissional","Expert");
        lista_points_Rank=Arrays.asList(0,100,200,500,100);
        cat=new ArrayList<Category>();
        rank=new ArrayList<Ranking>();
        this.initializeCategories();
        this.initializeRankings();
    }
    
    @Override
    public void initializeRankings(){
        
        try{
            
            /*VERIFICA SE AINDA NAO EXISTEM DADOS NA TABELA RANKING*/
            List<Ranking> ret_rank=this.r.findMultipleNames(lista_rank);
            
            /*SE NAO EXISTIREM VALORES ADICIONA LA DENTRO*/
            if(ret_rank==null){
                for(String j: lista_rank){
                    this.catL.createCategory(j, "");
                }
            }
            else {
                int x = 0;
                int controla_minP=0;
                for (String j : lista_rank) {
                    for (Ranking e : ret_rank) {
                        if (e.getNome().equals(j) == true) {
                            x++;
                        }
                    }
                    if (x == 0) {/*NAO ENCONTROU, ADICIONA NA BD*/
                        this.rankL.createRanking(j, this.lista_points_Rank.get(controla_minP));
                    } else {/*SE ENCONTROU, ENTAO COLOCA O X=0*/
                        x = 0;
                    }
                    controla_minP++;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
 
    }
    
    @Override
    public void initializeCategories(){
        
        try{
            
            /*VERIFICA SE AS CATEGORIAS SAUDE E DESPORTO, ESTAO LA*/
            /*VERIFICA SE AINDA NAO EXISTEM DADOS NA TABELA RANKING*/
            List<Category> ret_cat=this.c.findMultipleNames(lista_cat);
            
            /*SE NAO EXISTIREM VALORES ADICIONA LA DENTRO*/
            if(ret_cat==null){
                for(String j: lista_cat){
                    this.catL.createCategory(j, "");
                }
            }
            else {
                int x = 0;
                for (String j : lista_cat) {
                    for (Category e : ret_cat) {
                        if (e.getNome().equals(j) == true) {
                            x++;
                        }
                    }
                    if (x == 0) {/*NAO ENCONTROU, ADICIONA NA BD*/
                        this.catL.createCategory(j, "");
                    } else {/*SE ENCONTROU, ENTAO COLOCA O X=0*/
                        x = 0;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
}
