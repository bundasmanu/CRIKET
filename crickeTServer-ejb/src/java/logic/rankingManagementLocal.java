/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import entities.Goal;
import javax.ejb.Local;

/**
 *
 * @author gustavo
 */
@Local
public interface rankingManagementLocal {
    
    boolean createRanking(String name, int minP);
    String findRankUser(String email);
    boolean getDailyStrike(Goal g);
    String verifyRank(String email,String rank);
    int definePont(Goal g, int value);
}
