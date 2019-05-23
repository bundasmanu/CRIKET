/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cricketdto;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author gustavo
 */
public class TrophyDTO implements Serializable{
    
    private String nome;
    private String desc;
    
    public TrophyDTO(){
        
    }
    
    public TrophyDTO(String n, String d){
        this.nome=n;
        this.desc=d;
    }

    public String getNome() {
        return nome;
    }

    public String getDesc() {
        return desc;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /*HASHCODE E EQUALS BASEADOS NO NOME, COM COMPARADOR*/
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nome);
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
        final TrophyDTO other = (TrophyDTO) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    
}
