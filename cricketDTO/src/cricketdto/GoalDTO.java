/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cricketdto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
    int idCategory;
    
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
    
    public boolean getFavorite(){
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
    
     public void setCurrentValue(int c_v){
         this.currentValue=c_v;
     }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
    
    /*COMPARACAO COM BASE NA FLAG DE ORDEM*/
    @Override
    public int compareTo(GoalDTO obj){
        
        Integer a= (Integer) this.getFlag_order();
        Integer b= (Integer) obj.getFlag_order();
        
        return a.compareTo(b);
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id_goal;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.desc);
        hash = 59 * hash + Objects.hashCode(this.type);
        hash = 59 * hash + Objects.hashCode(this.status);
        hash = 59 * hash + Objects.hashCode(this.finalDate);
        hash = 59 * hash + this.totalValue;
        hash = 59 * hash + this.currentValue;
        hash = 59 * hash + (this.favorite ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.logDate);
        hash = 59 * hash + this.flagClick;
        hash = 59 * hash + this.flag_order;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GoalDTO other = (GoalDTO) obj;
        if (this.id_goal != other.id_goal) {
            return false;
        }
        if (this.totalValue != other.totalValue) {
            return false;
        }
        if (this.currentValue != other.currentValue) {
            return false;
        }
        if (this.favorite != other.favorite) {
            return false;
        }
        if (this.flagClick != other.flagClick) {
            return false;
        }
        if (this.flag_order != other.flag_order) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.desc, other.desc)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.finalDate, other.finalDate)) {
            return false;
        }
        if (!Objects.equals(this.logDate, other.logDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GoalDTO{" + "id_goal=" + id_goal + ", name=" + name + ", desc=" + desc + ", type=" + type + ", status=" + status + ", finalDate=" + finalDate + ", totalValue=" + totalValue + ", currentValue=" + currentValue + ", favorite=" + favorite + ", logDate=" + logDate + ", flagClick=" + flagClick + ", flag_order=" + flag_order + '}';
    }

}
