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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gustavo
 */
@Entity
@Table(name = "trophy")
@NamedQueries({
    @NamedQuery(name = "Trophy.findAll", query = "SELECT t FROM Trophy t")
    , @NamedQuery(name = "Trophy.findByIdTrophy", query = "SELECT t FROM Trophy t WHERE t.idTrophy = :idTrophy")
    , @NamedQuery(name = "Trophy.findByName", query = "SELECT t FROM Trophy t WHERE t.name = :name")
    , @NamedQuery(name = "Trophy.findByDesc", query = "SELECT t FROM Trophy t WHERE t.desc = :desc")
    , @NamedQuery(name = "Trophy.findByValue", query = "SELECT t FROM Trophy t WHERE t.value = :value")})
public class Trophy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_trophy")
    private Integer idTrophy;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "DESC")
    private String desc;
    @Basic(optional = false)
    @Column(name = "value")
    private int value;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private User idUser;

    public Trophy() {
    }

    public Trophy(Integer idTrophy) {
        this.idTrophy = idTrophy;
    }

    public Trophy(Integer idTrophy, String name, String desc, int value) {
        this.idTrophy = idTrophy;
        this.name = name;
        this.desc = desc;
        this.value = value;
    }

    public Integer getIdTrophy() {
        return idTrophy;
    }

    public void setIdTrophy(Integer idTrophy) {
        this.idTrophy = idTrophy;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrophy != null ? idTrophy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trophy)) {
            return false;
        }
        Trophy other = (Trophy) object;
        if ((this.idTrophy == null && other.idTrophy != null) || (this.idTrophy != null && !this.idTrophy.equals(other.idTrophy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Trophy[ idTrophy=" + idTrophy + " ]";
    }
    
}
