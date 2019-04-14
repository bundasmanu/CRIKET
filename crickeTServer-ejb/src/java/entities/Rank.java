/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gustavo
 */
@Entity
@Table(name = "rank")
@NamedQueries({
    @NamedQuery(name = "Rank.findAll", query = "SELECT r FROM Rank r")
    , @NamedQuery(name = "Rank.findByIdRank", query = "SELECT r FROM Rank r WHERE r.idRank = :idRank")
    , @NamedQuery(name = "Rank.findByName", query = "SELECT r FROM Rank r WHERE r.name = :name")
    , @NamedQuery(name = "Rank.findByDesc", query = "SELECT r FROM Rank r WHERE r.desc = :desc")
    , @NamedQuery(name = "Rank.findByMinpoints", query = "SELECT r FROM Rank r WHERE r.minpoints = :minpoints")})
public class Rank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rank")
    private Integer idRank;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "DESC")
    private String desc;
    @Basic(optional = false)
    @Column(name = "minpoints")
    private int minpoints;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRank")
    private Collection<User> userCollection;

    public Rank() {
    }

    public Rank(Integer idRank) {
        this.idRank = idRank;
    }

    public Rank(Integer idRank, String name, String desc, int minpoints) {
        this.idRank = idRank;
        this.name = name;
        this.desc = desc;
        this.minpoints = minpoints;
    }

    public Integer getIdRank() {
        return idRank;
    }

    public void setIdRank(Integer idRank) {
        this.idRank = idRank;
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

    public int getMinpoints() {
        return minpoints;
    }

    public void setMinpoints(int minpoints) {
        this.minpoints = minpoints;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRank != null ? idRank.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rank)) {
            return false;
        }
        Rank other = (Rank) object;
        if ((this.idRank == null && other.idRank != null) || (this.idRank != null && !this.idRank.equals(other.idRank))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rank[ idRank=" + idRank + " ]";
    }
    
}
