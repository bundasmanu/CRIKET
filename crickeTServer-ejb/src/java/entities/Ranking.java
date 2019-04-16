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
@Table(name = "ranking")
@NamedQueries({
    @NamedQuery(name = "Ranking.findAll", query = "SELECT r FROM Ranking r")
    , @NamedQuery(name = "Ranking.findByIdRank", query = "SELECT r FROM Ranking r WHERE r.idRank = :idRank")
    , @NamedQuery(name = "Ranking.findByNome", query = "SELECT r FROM Ranking r WHERE r.nome = :nome")
    , @NamedQuery(name = "Ranking.findByDescript", query = "SELECT r FROM Ranking r WHERE r.descript = :descript")
    , @NamedQuery(name = "Ranking.findByMinpoints", query = "SELECT r FROM Ranking r WHERE r.minpoints = :minpoints")})
public class Ranking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rank")
    private Integer idRank;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "descript")
    private String descript;
    @Basic(optional = false)
    @Column(name = "minpoints")
    private int minpoints;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRank")
    private Collection<Utilizador> utilizadorCollection;

    public Ranking() {
    }

    public Ranking(Integer idRank) {
        this.idRank = idRank;
    }

    public Ranking(Integer idRank, String nome, String descript, int minpoints) {
        this.idRank = idRank;
        this.nome = nome;
        this.descript = descript;
        this.minpoints = minpoints;
    }
    
    public Ranking(String nome, String descript, int minpoints) {
        this.nome = nome;
        this.descript = descript;
        this.minpoints = minpoints;
    }
    
    public Ranking(String nome, int minpoints) {
        this.nome = nome;
        this.minpoints = minpoints;
    }
    
    public Integer getIdRank() {
        return idRank;
    }

    public void setIdRank(Integer idRank) {
        this.idRank = idRank;
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

    public int getMinpoints() {
        return minpoints;
    }

    public void setMinpoints(int minpoints) {
        this.minpoints = minpoints;
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
        hash += (idRank != null ? idRank.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ranking)) {
            return false;
        }
        Ranking other = (Ranking) object;
        if ((this.idRank == null && other.idRank != null) || (this.idRank != null && !this.idRank.equals(other.idRank))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ranking[ idRank=" + idRank + " ]";
    }
    
}
