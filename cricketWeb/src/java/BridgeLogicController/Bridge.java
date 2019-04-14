/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BridgeLogicController;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.cricketManagerLocal;

/**
 *
 * @author gustavo
 */
@Singleton
public class Bridge implements BridgeLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    private cricketManagerLocal cricket;

    public cricketManagerLocal getCricket() {
        return cricket;
    }
    
}
