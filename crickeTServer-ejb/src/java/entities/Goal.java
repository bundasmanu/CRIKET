/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "goal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Goal.findAll", query = "SELECT g FROM Goal g")
    , @NamedQuery(name = "Goal.findByIdGoal", query = "SELECT g FROM Goal g WHERE g.idGoal = :idGoal")
    , @NamedQuery(name = "Goal.findByNome", query = "SELECT g FROM Goal g WHERE g.nome = :nome")
    , @NamedQuery(name = "Goal.findByDescript", query = "SELECT g FROM Goal g WHERE g.descript = :descript")
    , @NamedQuery(name = "Goal.findByFrequency", query = "SELECT g FROM Goal g WHERE g.frequency = :frequency")
    , @NamedQuery(name = "Goal.findByTipo", query = "SELECT g FROM Goal g WHERE g.tipo = :tipo")
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
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "descript")
    private String descript;
    @Basic(optional = false)
    @Column(name = "frequency")
    private String frequency;
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Basic(optional = true)
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
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    @ManyToOne(optional = false)
    private Category idCategory;

    public Goal() {
    }

    public Goal(Integer idGoal) {
        this.idGoal = idGoal;
    }

    public Goal(Integer idGoal, String nome, String descript, String frequency, String tipo, String status, Date finaldate, int totalvalue, int currentvalue, boolean favorite, Date logdate, int flagClickControl, int flagOrder) {
        this.idGoal = idGoal;
        this.nome = nome;
        this.descript = descript;
        this.frequency = frequency;
        this.tipo = tipo;
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

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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

    public Category getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Category idCategory) {
        this.idCategory = idCategory;
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
