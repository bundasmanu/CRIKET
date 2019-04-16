/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Ranking;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface RankingFacadeLocal {

    void create(Ranking ranking);

    void edit(Ranking ranking);

    void remove(Ranking ranking);

    Ranking find(Object id);

    List<Ranking> findAll();

    List<Ranking> findRange(int[] range);
    
    Ranking findByName(String name);
    
    List<Ranking> findMultipleNames(List<String> x);

    int count();
    
}
