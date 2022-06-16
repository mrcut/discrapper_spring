
package root.entites;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Categorie implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categorie_id")
    private Integer categorieId;
    
    @Column(name = "categorie_nom", unique=true, nullable=false)
    private String categorieNom;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorieId")
    private Collection<Message> messageCollection;

    public Categorie() {
    }

    public Categorie(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public Categorie(Integer categorieId, String categorieNom) {
        this.categorieId = categorieId;
        this.categorieNom = categorieNom;
    }

    public Integer getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Integer categorieId) {
        this.categorieId = categorieId;
    }

    public String getCategorieNom() {
        return categorieNom;
    }

    public void setCategorieNom(String categorieNom) {
        this.categorieNom = categorieNom;
    }

    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categorieId != null ? categorieId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Categorie)) {
            return false;
        }
        Categorie other = (Categorie) object;
        if ((this.categorieId == null && other.categorieId != null) || (this.categorieId != null && !this.categorieId.equals(other.categorieId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Categorie{" + "categorieId=" + categorieId + ", categorieNom=" + categorieNom + '}';
    }

    
    
}
