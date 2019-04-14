/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.StandardGoal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface StandardGoalFacadeLocal {

    void create(StandardGoal standardGoal);

    void edit(StandardGoal standardGoal);

    void remove(StandardGoal standardGoal);

    StandardGoal find(Object id);

    List<StandardGoal> findAll();

    List<StandardGoal> findRange(int[] range);

    int count();
    
}
