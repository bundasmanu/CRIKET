/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.ejb.EJB;
import javax.ejb.Singleton;

/**
 *
 * @author gustavo
 */
@Singleton
public class cricketManager implements cricketManagerLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    /*INJECCAO DE DEPENDENCIAS PARA OS EJB QUE IRAO CONTER A LOGICA*/
    
    @EJB
    userManagementLocal user;
    
    @EJB
    goalManagementLocal goal;
    
    @EJB
    historyManagementLocal history;
    
    
}
