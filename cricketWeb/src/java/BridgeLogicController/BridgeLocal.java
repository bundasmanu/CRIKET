/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BridgeLogicController;

import javax.ejb.Local;
import logic.cricketManagerLocal;

/**
 *
 * @author gustavo
 */
@Local
public interface BridgeLocal {
    public cricketManagerLocal getCricket(); 
}
