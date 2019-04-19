/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Trophy;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface TrophyFacadeLocal {

    void create(Trophy trophy);

    void edit(Trophy trophy);

    void remove(Trophy trophy);

    Trophy find(Object id);

    List<Trophy> findAll();

    List<Trophy> findRange(int[] range);

    int count();
    
}
