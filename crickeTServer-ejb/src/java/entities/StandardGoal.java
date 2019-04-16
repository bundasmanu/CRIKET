/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    , @NamedQuery(name = "StandardGoal.findByFlagOrder", query = "SELECT s FROM StandardGoal s WHERE s.flagOrder = :flagOrder")
    , @NamedQuery(name = "StandardGoal.findByMinweight", query = "SELECT s FROM StandardGoal s WHERE s.minweight = :minweight")
    , @NamedQuery(name = "StandardGoal.findByMaxweight", query = "SELECT s FROM StandardGoal s WHERE s.maxweight = :maxweight")
    , @NamedQuery(name = "StandardGoal.findByMinage", query = "SELECT s FROM StandardGoal s WHERE s.minage = :minage")
    , @NamedQuery(name = "StandardGoal.findByMaxage", query = "SELECT s FROM StandardGoal s WHERE s.maxage = :maxage")
    , @NamedQuery(name = "StandardGoal.findByMinheight", query = "SELECT s FROM StandardGoal s WHERE s.minheight = :minheight")
    , @NamedQuery(name = "StandardGoal.findByMaxheight", query = "SELECT s FROM StandardGoal s WHERE s.maxheight = :maxheight")
    , @NamedQuery(name = "StandardGoal.findByGenre", query = "SELECT s FROM StandardGoal s WHERE s.genre = :genre")})
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
    @Basic(optional = false)
    @Column(name = "minweight")
    private double minweight;
    @Basic(optional = false)
    @Column(name = "maxweight")
    private double maxweight;
    @Basic(optional = false)
    @Column(name = "minage")
    private int minage;
    @Basic(optional = false)
    @Column(name = "maxage")
    private int maxage;
    @Basic(optional = false)
    @Column(name = "minheight")
    private double minheight;
    @Basic(optional = false)
    @Column(name = "maxheight")
    private double maxheight;
    @Basic(optional = false)
    @Column(name = "genre")
    private String genre;

    public StandardGoal() {
    }

    public StandardGoal(Integer idStandard) {
        this.idStandard = idStandard;
    }

    public StandardGoal(Integer idStandard, String nome, String descript, String tipo, String status, int totalvalue, int flagClickControl, int flagOrder, double minweight, double maxweight, int minage, int maxage, double minheight, double maxheight, String genre) {
        this.idStandard = idStandard;
        this.nome = nome;
        this.descript = descript;
        this.tipo = tipo;
        this.status = status;
        this.totalvalue = totalvalue;
        this.flagClickControl = flagClickControl;
        this.flagOrder = flagOrder;
        this.minweight = minweight;
        this.maxweight = maxweight;
        this.minage = minage;
        this.maxage = maxage;
        this.minheight = minheight;
        this.maxheight = maxheight;
        this.genre = genre;
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

    public double getMinweight() {
        return minweight;
    }

    public void setMinweight(double minweight) {
        this.minweight = minweight;
    }

    public double getMaxweight() {
        return maxweight;
    }

    public void setMaxweight(double maxweight) {
        this.maxweight = maxweight;
    }

    public int getMinage() {
        return minage;
    }

    public void setMinage(int minage) {
        this.minage = minage;
    }

    public int getMaxage() {
        return maxage;
    }

    public void setMaxage(int maxage) {
        this.maxage = maxage;
    }

    public double getMinheight() {
        return minheight;
    }

    public void setMinheight(double minheight) {
        this.minheight = minheight;
    }

    public double getMaxheight() {
        return maxheight;
    }

    public void setMaxheight(double maxheight) {
        this.maxheight = maxheight;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
