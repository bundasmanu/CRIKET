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
    , @NamedQuery(name = "Trophy.findByNome", query = "SELECT t FROM Trophy t WHERE t.nome = :nome")
    , @NamedQuery(name = "Trophy.findByDescript", query = "SELECT t FROM Trophy t WHERE t.descript = :descript")
    , @NamedQuery(name = "Trophy.findByValor", query = "SELECT t FROM Trophy t WHERE t.valor = :valor")})
public class Trophy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_trophy")
    private Integer idTrophy;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "descript")
    private String descript;
    @Basic(optional = false)
    @Column(name = "valor")
    private int valor;
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    @ManyToOne(optional = false)
    private Utilizador idUser;

    public Trophy() {
    }

    public Trophy(Integer idTrophy) {
        this.idTrophy = idTrophy;
    }

    public Trophy(Integer idTrophy, String nome, String descript, int valor) {
        this.idTrophy = idTrophy;
        this.nome = nome;
        this.descript = descript;
        this.valor = valor;
    }
    
    public Trophy(String nome, String descript) {
        this.nome = nome;
        this.descript = descript;
        this.valor = 0;
    }

    public Integer getIdTrophy() {
        return idTrophy;
    }

    public void setIdTrophy(Integer idTrophy) {
        this.idTrophy = idTrophy;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Utilizador getIdUser() {
        return idUser;
    }

    public void setIdUser(Utilizador idUser) {
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
