/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BridgeLogicController;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import logic.cricketManagerLocal;

/**
 *
 * @author gustavo
 */
@Stateless
public class Bridge implements BridgeLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    private cricketManagerLocal cricket;
    
    @Override
    public cricketManagerLocal getCricket() {
        return cricket;
    }
    
}
