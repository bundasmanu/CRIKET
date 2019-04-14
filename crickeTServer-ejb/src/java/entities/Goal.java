/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gustavo
 */
@Entity
@Table(name = "goal")
@NamedQueries({
    @NamedQuery(name = "Goal.findAll", query = "SELECT g FROM Goal g")
    , @NamedQuery(name = "Goal.findByIdGoal", query = "SELECT g FROM Goal g WHERE g.idGoal = :idGoal")
    , @NamedQuery(name = "Goal.findByName", query = "SELECT g FROM Goal g WHERE g.name = :name")
    , @NamedQuery(name = "Goal.findByDesc", query = "SELECT g FROM Goal g WHERE g.desc = :desc")
    , @NamedQuery(name = "Goal.findByType", query = "SELECT g FROM Goal g WHERE g.type = :type")
    , @NamedQuery(name = "Goal.findByStatus", query = "SELECT g FROM Goal g WHERE g.status = :status")
    , @NamedQuery(name = "Goal.findByFinaldate", query = "SELECT g FROM Goal g WHERE g.finaldate = :finaldate")
    , @NamedQuery(name = "Goal.findByTotalvalue", query = "SELECT g FROM Goal g WHERE g.totalvalue = :totalvalue")
    , @NamedQuery(name = "Goal.findByCurrentvalue", query = "SELECT g FROM Goal g WHERE g.currentvalue = :currentvalue")
    , @NamedQuery(name = "Goal.findByFavorite", query = "SELECT g FROM Goal g WHERE g.favorite = :favorite")
    , @NamedQuery(name = "Goal.findByLogdate", query = "SELECT g FROM Goal g WHERE g.logdate = :logdate")
    , @NamedQuery(name = "Goal.findByFlagClickControl", query = "SELECT g FROM Goal g WHERE g.flagClickControl = :flagClickControl")
    , @NamedQuery(name = "Goal.findByFlagOrder", query = "SELECT g FROM Goal g WHERE g.flagOrder = :flagOrder")})
public class Goal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_goal")
    private Integer idGoal;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "DESC")
    private String desc;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "finaldate")
    @Temporal(TemporalType.DATE)
    private Date finaldate;
    @Basic(optional = false)
    @Column(name = "totalvalue")
    private int totalvalue;
    @Basic(optional = false)
    @Column(name = "currentvalue")
    private int currentvalue;
    @Basic(optional = false)
    @Column(name = "favorite")
    private boolean favorite;
    @Basic(optional = false)
    @Column(name = "logdate")
    @Temporal(TemporalType.DATE)
    private Date logdate;
    @Basic(optional = false)
    @Column(name = "flag_click_control")
    private int flagClickControl;
    @Basic(optional = false)
    @Column(name = "flag_order")
    private int flagOrder;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User idUser;
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    @ManyToOne(optional = false)
    private Category idCategory;
    @JoinColumn(name = "id_history", referencedColumnName = "id_history")
    @ManyToOne
    private History idHistory;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGoal")
    private Collection<History> historyCollection;

    public Goal() {
    }

    public Goal(Integer idGoal) {
        this.idGoal = idGoal;
    }

    public Goal(Integer idGoal, String name, String desc, String type, String status, Date finaldate, int totalvalue, int currentvalue, boolean favorite, Date logdate, int flagClickControl, int flagOrder) {
        this.idGoal = idGoal;
        this.name = name;
        this.desc = desc;
        this.type = type;
        this.status = status;
        this.finaldate = finaldate;
        this.totalvalue = totalvalue;
        this.currentvalue = currentvalue;
        this.favorite = favorite;
        this.logdate = logdate;
        this.flagClickControl = flagClickControl;
        this.flagOrder = flagOrder;
    }

    public Integer getIdGoal() {
        return idGoal;
    }

    public void setIdGoal(Integer idGoal) {
        this.idGoal = idGoal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFinaldate() {
        return finaldate;
    }

    public void setFinaldate(Date finaldate) {
        this.finaldate = finaldate;
    }

    public int getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(int totalvalue) {
        this.totalvalue = totalvalue;
    }

    public int getCurrentvalue() {
        return currentvalue;
    }

    public void setCurrentvalue(int currentvalue) {
        this.currentvalue = currentvalue;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Date getLogdate() {
        return logdate;
    }

    public void setLogdate(Date logdate) {
        this.logdate = logdate;
    }

    public int getFlagClickControl() {
        return flagClickControl;
    }

    public void setFlagClickControl(int flagClickControl) {
        this.flagClickControl = flagClickControl;
    }

    public int getFlagOrder() {
        return flagOrder;
    }

    public void setFlagOrder(int flagOrder) {
        this.flagOrder = flagOrder;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Category getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Category idCategory) {
        this.idCategory = idCategory;
    }

    public History getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(History idHistory) {
        this.idHistory = idHistory;
    }

    public Collection<History> getHistoryCollection() {
        return historyCollection;
    }

    public void setHistoryCollection(Collection<History> historyCollection) {
        this.historyCollection = historyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGoal != null ? idGoal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Goal)) {
            return false;
        }
        Goal other = (Goal) object;
        if ((this.idGoal == null && other.idGoal != null) || (this.idGoal != null && !this.idGoal.equals(other.idGoal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Goal[ idGoal=" + idGoal + " ]";
    }
    
}
