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
@Table(name = "utilizador")
@NamedQueries({
    @NamedQuery(name = "Utilizador.findAll", query = "SELECT u FROM Utilizador u")
    , @NamedQuery(name = "Utilizador.findByIdUser", query = "SELECT u FROM Utilizador u WHERE u.idUser = :idUser")
    , @NamedQuery(name = "Utilizador.findByEmail", query = "SELECT u FROM Utilizador u WHERE u.email = :email")
    , @NamedQuery(name = "Utilizador.findByPassword", query = "SELECT u FROM Utilizador u WHERE u.password = :password")
    , @NamedQuery(name = "Utilizador.findByNome", query = "SELECT u FROM Utilizador u WHERE u.nome = :nome")
    , @NamedQuery(name = "Utilizador.findByAge", query = "SELECT u FROM Utilizador u WHERE u.age = :age")
    , @NamedQuery(name = "Utilizador.findByGenre", query = "SELECT u FROM Utilizador u WHERE u.genre = :genre")})
public class Utilizador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    private Integer idUser;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "age")
    @Temporal(TemporalType.DATE)
    private Date age;
    @Basic(optional = false)
    @Column(name = "genre")
    private String genre;
    @JoinColumn(name = "id_rank", referencedColumnName = "id_rank")
    @ManyToOne(optional = false)
    private Ranking idRank;
    @OneToMany(mappedBy = "utiIdUser")
    private Collection<Utilizador> utilizadorCollection;
    @JoinColumn(name = "uti_id_user", referencedColumnName = "id_user")
    @ManyToOne
    private Utilizador utiIdUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<Category> categoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<Trophy> trophyCollection;

    public Utilizador() {
    }

    public Utilizador(Integer idUser) {
        this.idUser = idUser;
    }

    public Utilizador(Integer idUser, String email, String password, String nome, Date age, String genre) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.age = age;
        this.genre = genre;
    }
    
    public Utilizador(String email, String password, String nome, Date age, String genre) {
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.age = age;
        this.genre = genre;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Ranking getIdRank() {
        return idRank;
    }

    public void setIdRank(Ranking idRank) {
        this.idRank = idRank;
    }

    public Collection<Utilizador> getUtilizadorCollection() {
        return utilizadorCollection;
    }

    public void setUtilizadorCollection(Collection<Utilizador> utilizadorCollection) {
        this.utilizadorCollection = utilizadorCollection;
    }

    public Utilizador getUtiIdUser() {
        return utiIdUser;
    }

    public void setUtiIdUser(Utilizador utiIdUser) {
        this.utiIdUser = utiIdUser;
    }

    public Collection<Category> getCategoryCollection() {
        return categoryCollection;
    }

    public void setCategoryCollection(Collection<Category> categoryCollection) {
        this.categoryCollection = categoryCollection;
    }

    public Collection<Trophy> getTrophyCollection() {
        return trophyCollection;
    }

    public void setTrophyCollection(Collection<Trophy> trophyCollection) {
        this.trophyCollection = trophyCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilizador)) {
            return false;
        }
        Utilizador other = (Utilizador) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Utilizador[ idUser=" + idUser + " ]";
    }
    
}
