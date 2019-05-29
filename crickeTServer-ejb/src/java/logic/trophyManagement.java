/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import facades.TrophyFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import cricketdto.*;
import entities.Trophy;
import entities.*;
import facades.UtilizadorFacadeLocal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gustavo
 */
@Singleton
public class trophyManagement implements trophyManagementLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    TrophyFacadeLocal trophy;

    @EJB
    UtilizadorFacadeLocal utilizador;

    @Override
    public boolean createTrophy(String email, TrophyDTO t) {

        try {

            Utilizador u = this.utilizador.findByEmail(email);

            if (u == null) {
                return false;
            }

            Trophy tro = new Trophy(t.getNome(), t.getDesc());
            tro.setIdUser(u);

            this.trophy.create(tro);

            u.getTrophyCollection().add(tro);
            this.utilizador.edit(u);

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }

    @Override
    public List<TrophyDTO> getAllTrophiesFromUser(String email) {

        try {

            Utilizador u = this.utilizador.findByEmail(email);

            if (u == null) {
                return null;
            }

            List<TrophyDTO> listTrophiesDTO = new ArrayList<TrophyDTO>();

            for (Trophy t : u.getTrophyCollection()) {
                TrophyDTO x = DTOFactory.getTrophyDTO(t);
                listTrophiesDTO.add(x);
            }

            return listTrophiesDTO;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

}
