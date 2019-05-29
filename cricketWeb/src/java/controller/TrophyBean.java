/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BridgeLogicController.BridgeLocal;
import cricketdto.CategoryDTO;
import cricketdto.TrophyDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author stefan
 */
@Named(value = "trophyBean")
@Dependent
public class TrophyBean implements Serializable {

    TrophyDTO trophyDTO;

    @EJB
    BridgeLocal bridge;

    @Inject
    SessionBean s;

    public TrophyBean() {
    }

    public List<TrophyDTO> getAllDoneTrophies() {
        FacesContext fc = FacesContext.getCurrentInstance();
        String emailOfLoggedUser = (String) fc.getExternalContext().getSessionMap().get("user");

        List<TrophyDTO> list = bridge.getCricket().allDoneTrophies(emailOfLoggedUser);

        return list;
    }

    public TrophyDTO getTrophyDTO() {
        return trophyDTO;
    }

    public void setTrophyDTO(TrophyDTO trophyDTO) {
        this.trophyDTO = trophyDTO;
    }

    public List<String> catch3Trophies(){
        List<TrophyDTO> lista_trofeus= this.getAllDoneTrophies();
        List<String> lista_auxiliar= new ArrayList<String>();
        for(int i=0;i<lista_trofeus.size();i++){
            if(i==3){
                return lista_auxiliar;
            }
            String url="https://img.icons8.com/color/48/000000/gold-medal.png";
            lista_auxiliar.add(url);
        }
        return lista_auxiliar;
    }
}
