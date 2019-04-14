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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "USER")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    , @NamedQuery(name = "User.findByIdUser", query = "SELECT u FROM User u WHERE u.idUser = :idUser")
    , @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")
    , @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password")
    , @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name")
    , @NamedQuery(name = "User.findByAge", query = "SELECT u FROM User u WHERE u.age = :age")
    , @NamedQuery(name = "User.findByGenre", query = "SELECT u FROM User u WHERE u.genre = :genre")})
public class User implements Serializable {

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
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "age")
    @Temporal(TemporalType.DATE)
    private Date age;
    @Basic(optional = false)
    @Column(name = "genre")
    private String genre;
    @JoinTable(name = "dispoe", joinColumns = {
        @JoinColumn(name = "id_user", referencedColumnName = "id_user")}, inverseJoinColumns = {
        @JoinColumn(name = "id_standard", referencedColumnName = "id_standard")})
    @ManyToMany
    private Collection<StandardGoal> standardGoalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<Goal> goalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<History> historyCollection;
    @OneToMany(mappedBy = "useIdUser")
    private Collection<User> userCollection;
    @JoinColumn(name = "use_id_user", referencedColumnName = "id_user")
    @ManyToOne
    private User useIdUser;
    @JoinColumn(name = "id_rank", referencedColumnName = "id_rank")
    @ManyToOne(optional = false)
    private Rank idRank;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUser")
    private Collection<Trophy> trophyCollection;

    public User() {
    }

    public User(Integer idUser) {
        this.idUser = idUser;
    }

    public User(Integer idUser, String email, String password, String name, Date age, String genre) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Collection<StandardGoal> getStandardGoalCollection() {
        return standardGoalCollection;
    }

    public void setStandardGoalCollection(Collection<StandardGoal> standardGoalCollection) {
        this.standardGoalCollection = standardGoalCollection;
    }

    public Collection<Goal> getGoalCollection() {
        return goalCollection;
    }

    public void setGoalCollection(Collection<Goal> goalCollection) {
        this.goalCollection = goalCollection;
    }

    public Collection<History> getHistoryCollection() {
        return historyCollection;
    }

    public void setHistoryCollection(Collection<History> historyCollection) {
        this.historyCollection = historyCollection;
    }

    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
    }

    public User getUseIdUser() {
        return useIdUser;
    }

    public void setUseIdUser(User useIdUser) {
        this.useIdUser = useIdUser;
    }

    public Rank getIdRank() {
        return idRank;
    }

    public void setIdRank(Rank idRank) {
        this.idRank = idRank;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ idUser=" + idUser + " ]";
    }
    
}
