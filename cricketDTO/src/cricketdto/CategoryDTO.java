/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cricketdto; 

import java.io.Serializable; 
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author bruno
 */
public class CategoryDTO implements Serializable, Comparable<CategoryDTO>{
    
    private Integer idCategory;
    private String nome;
    private String descript;
    private UserDTO idUser;
    private List<GoalDTO> goals;

    public CategoryDTO() {
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
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

    public UserDTO getIdUser() {
        return idUser;
    }

    public void setIdUser(UserDTO idUser) {
        this.idUser = idUser;
    }

    public List<GoalDTO> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalDTO> goals) {
        this.goals = goals;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idCategory);
        hash = 79 * hash + Objects.hashCode(this.nome);
        hash = 79 * hash + Objects.hashCode(this.descript);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoryDTO other = (CategoryDTO) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.descript, other.descript)) {
            return false;
        }
        if (!Objects.equals(this.idCategory, other.idCategory)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "CategoryDTO{" + "idCategory=" + idCategory + ", nome=" + nome + ", descript=" + descript + ", idUser=" + idUser + '}';
    }
    
    @Override
    public int compareTo(CategoryDTO c){
        
        if (c == null) {
            throw new NullPointerException();/*TRATA A EXCECAO, ONDE É INVOCADO O COMPARE TO (NUM COLLECTIONS.SORT NESTE CASO)*/
        }
        
        return this.getNome().compareTo(c.getNome());
        
    }
    
}
