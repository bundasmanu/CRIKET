/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Utilizador;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface UtilizadorFacadeLocal {

    void create(Utilizador utilizador);

    void edit(Utilizador utilizador);

    void remove(Utilizador utilizador);

    Utilizador find(Object id);

    List<Utilizador> findAll();

    List<Utilizador> findRange(int[] range);

    Utilizador findByEmail(String email);

    int count();
    
}
