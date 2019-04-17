/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cricketdto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author gustavo
 */
public class GoalDTO implements Serializable, Comparable<GoalDTO> {
    
    int id_goal;
    String name;
    String desc;
    String type;
    String status;
    Date finalDate;
    int totalValue;
    int currentValue;
    boolean favorite;
    Date logDate;/*PARA QUE ERA ISTO?? - sempre que criamos um objetivo esta data e' preenchida com a data em q foi criado*/
    int flagClick;/*NUMERO CLIQUES TEM DE SER SEMPRE AUMENTADO ASSIM QUE HÁ CLIQUES NO OBJETIVO*/
    int flag_order;/*ORDEM DA FLAG--> JA NAO ME LEMBRO MUITO BEM DISTO*/
    CategoryDTO categoryDTO;
    
    public GoalDTO(){
        
    }

    public int getId_goal() {
        return id_goal;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public int getTotalValue() {
        return totalValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public Date getLogDate() {
        return logDate;
    }

    public int getFlagClick() {
        return flagClick;
    }

    public int getFlag_order() {
        return flag_order;
    }

    public void setId_goal(int id_goal) {
        this.id_goal = id_goal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public void setFlagClick(int flagClick) {
        this.flagClick = flagClick;
    }

    public void setFlag_order(int flag_order) {
        this.flag_order = flag_order;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }
    
    /*COMPARACAO COM BASE NA FLAG DE ORDEM*/
    @Override
    public int compareTo(GoalDTO obj){
        
        Integer a= (Integer) this.getFlag_order();
        Integer b= (Integer) obj.getFlag_order();
        
        return a.compareTo(b);
        
    }
    
}
