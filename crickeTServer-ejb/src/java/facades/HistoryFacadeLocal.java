/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.History;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface HistoryFacadeLocal {

    void create(History history);

    void edit(History history);

    void remove(History history);

    History find(Object id);

    List<History> findAll();

    List<History> findRange(int[] range);

    int count();
    
}
