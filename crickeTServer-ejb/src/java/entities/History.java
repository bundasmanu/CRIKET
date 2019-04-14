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
    , @NamedQuery(name = "History.findByTipo", query = "SELECT h FROM History h WHERE h.tipo = :tipo")})
public class History implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_history")
    private Integer idHistory;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "idHistory")
    private Collection<Goal> goalCollection;
    @JoinColumn(name = "id_goal", referencedColumnName = "id_goal")
    @ManyToOne(optional = false)
    private Goal idGoal;
    @JoinColumn(name = "id_utilizador", referencedColumnName = "id_utilizador")
    @ManyToOne(optional = false)
    private Utilizador idUtilizador;

    public History() {
    }

    public History(Integer idHistory) {
        this.idHistory = idHistory;
    }

    public History(Integer idHistory, String tipo) {
        this.idHistory = idHistory;
        this.tipo = tipo;
    }

    public Integer getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(Integer idHistory) {
        this.idHistory = idHistory;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Collection<Goal> getGoalCollection() {
        return goalCollection;
    }

    public void setGoalCollection(Collection<Goal> goalCollection) {
        this.goalCollection = goalCollection;
    }

    public Goal getIdGoal() {
        return idGoal;
    }

    public void setIdGoal(Goal idGoal) {
        this.idGoal = idGoal;
    }

    public Utilizador getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(Utilizador idUtilizador) {
        this.idUtilizador = idUtilizador;
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
