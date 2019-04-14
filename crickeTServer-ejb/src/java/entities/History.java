/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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

/**
 *
 * @author gustavo
 */
@Entity
@Table(name = "history")
@NamedQueries({
    @NamedQuery(name = "History.findAll", query = "SELECT h FROM History h")
    , @NamedQuery(name = "History.findByIdHistory", query = "SELECT h FROM History h WHERE h.idHistory = :idHistory")
    , @NamedQuery(name = "History.findByType", query = "SELECT h FROM History h WHERE h.type = :type")})
public class History implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_history")
    private Integer idHistory;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "idHistory")
    private Collection<Goal> goalCollection;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User idUser;
    @JoinColumn(name = "id_goal", referencedColumnName = "id_goal")
    @ManyToOne(optional = false)
    private Goal idGoal;

    public History() {
    }

    public History(Integer idHistory) {
        this.idHistory = idHistory;
    }

    public History(Integer idHistory, String type) {
        this.idHistory = idHistory;
        this.type = type;
    }

    public Integer getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Integer idHistory) {
        this.idHistory = idHistory;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<Goal> getGoalCollection() {
        return goalCollection;
    }

    public void setGoalCollection(Collection<Goal> goalCollection) {
        this.goalCollection = goalCollection;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Goal getIdGoal() {
        return idGoal;
    }

    public void setIdGoal(Goal idGoal) {
        this.idGoal = idGoal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHistory != null ? idHistory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof History)) {
            return false;
        }
        History other = (History) object;
        if ((this.idHistory == null && other.idHistory != null) || (this.idHistory != null && !this.idHistory.equals(other.idHistory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.History[ idHistory=" + idHistory + " ]";
    }
    
}
