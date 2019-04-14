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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gustavo
 */
@Entity
@Table(name = "standard_goal")
@NamedQueries({
    @NamedQuery(name = "StandardGoal.findAll", query = "SELECT s FROM StandardGoal s")
    , @NamedQuery(name = "StandardGoal.findByIdStandard", query = "SELECT s FROM StandardGoal s WHERE s.idStandard = :idStandard")
    , @NamedQuery(name = "StandardGoal.findByNome", query = "SELECT s FROM StandardGoal s WHERE s.nome = :nome")
    , @NamedQuery(name = "StandardGoal.findByDescript", query = "SELECT s FROM StandardGoal s WHERE s.descript = :descript")
    , @NamedQuery(name = "StandardGoal.findByTipo", query = "SELECT s FROM StandardGoal s WHERE s.tipo = :tipo")
    , @NamedQuery(name = "StandardGoal.findByStatus", query = "SELECT s FROM StandardGoal s WHERE s.status = :status")
    , @NamedQuery(name = "StandardGoal.findByTotalvalue", query = "SELECT s FROM StandardGoal s WHERE s.totalvalue = :totalvalue")
    , @NamedQuery(name = "StandardGoal.findByFlagClickControl", query = "SELECT s FROM StandardGoal s WHERE s.flagClickControl = :flagClickControl")
    , @NamedQuery(name = "StandardGoal.findByFlagOrder", query = "SELECT s FROM StandardGoal s WHERE s.flagOrder = :flagOrder")})
public class StandardGoal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_standard")
    private Integer idStandard;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "descript")
    private String descript;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @Column(name = "totalvalue")
    private int totalvalue;
    @Basic(optional = false)
    @Column(name = "flag_click_control")
    private int flagClickControl;
    @Basic(optional = false)
    @Column(name = "flag_order")
    private int flagOrder;
    @ManyToMany(mappedBy = "standardGoalCollection")
    private Collection<Utilizador> utilizadorCollection;

    public StandardGoal() {
    }

    public StandardGoal(Integer idStandard) {
        this.idStandard = idStandard;
    }

    public StandardGoal(Integer idStandard, String nome, String descript, String tipo, String status, int totalvalue, int flagClickControl, int flagOrder) {
        this.idStandard = idStandard;
        this.nome = nome;
        this.descript = descript;
        this.tipo = tipo;
        this.status = status;
        this.totalvalue = totalvalue;
        this.flagClickControl = flagClickControl;
        this.flagOrder = flagOrder;
    }

    public Integer getIdStandard() {
        return idStandard;
    }

    public void setIdStandard(Integer idStandard) {
        this.idStandard = idStandard;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(int totalvalue) {
        this.totalvalue = totalvalue;
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

    public Collection<Utilizador> getUtilizadorCollection() {
        return utilizadorCollection;
    }

    public void setUtilizadorCollection(Collection<Utilizador> utilizadorCollection) {
        this.utilizadorCollection = utilizadorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStandard != null ? idStandard.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StandardGoal)) {
            return false;
        }
        StandardGoal other = (StandardGoal) object;
        if ((this.idStandard == null && other.idStandard != null) || (this.idStandard != null && !this.idStandard.equals(other.idStandard))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.StandardGoal[ idStandard=" + idStandard + " ]";
    }
    
}
