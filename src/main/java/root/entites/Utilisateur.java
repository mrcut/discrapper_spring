
package root.entites;

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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utilisateur_id")
    private Integer utilisateurId;
    
    @Column(name = "utilisateur_email",  unique=true, nullable=false)
    private String utilisateurEmail;
   
    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "utilisateur_mdp", nullable = false)
    private String utilisateurMdp;
    
    @Column(name = "utilisateur_nom", nullable = false)
    private String utilisateurNom;
    
    @Basic(optional = false)
    @Column(name = "utilisateur_prenom", nullable = false)
    private String utilisateurPrenom;
    
    @Column(name = "utilisateur_tel")
    private String utilisateurTel;
    
    @Column(name = "utilisateur_discord", unique=true)
    private String utilisateurDiscord;
    
    @Column(name = "utilisateur_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date utilisateurDate;
    
    @Column(name = "utilisateur_role", nullable = false)
    private String utilisateurRole;
    
    @JsonIgnore
    @JoinTable(name = "utilisateurmessage", joinColumns = {
        @JoinColumn(name = "utilisateur_id", referencedColumnName = "utilisateur_id")}, inverseJoinColumns = {
        @JoinColumn(name = "message_id", referencedColumnName = "message_id")})
    @ManyToMany
    private Collection<Message> messageCollection;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilisateur")
    private Collection<Token> tokenCollection;

    public Utilisateur() {
    }

    public Utilisateur(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    

    public Utilisateur(String utilisateurEmail, String utilisateurMdp) {
		this.utilisateurEmail = utilisateurEmail;
		this.utilisateurMdp = utilisateurMdp;
	}

	public Utilisateur(Integer utilisateurId, String utilisateurEmail, String utilisateurMdp, String utilisateurNom, String utilisateurPrenom, Date utilisateurDate, String utilisateurRole) {
        this.utilisateurId = utilisateurId;
        this.utilisateurEmail = utilisateurEmail;
        this.utilisateurMdp = utilisateurMdp;
        this.utilisateurNom = utilisateurNom;
        this.utilisateurPrenom = utilisateurPrenom;
        this.utilisateurDate = utilisateurDate;
        this.utilisateurRole = utilisateurRole;
    }

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getUtilisateurEmail() {
        return utilisateurEmail;
    }

    public void setUtilisateurEmail(String utilisateurEmail) {
        this.utilisateurEmail = utilisateurEmail;
    }

    public String getUtilisateurMdp() {
        return utilisateurMdp;
    }

    public void setUtilisateurMdp(String utilisateurMdp) {
        this.utilisateurMdp = utilisateurMdp;
    }

    public String getUtilisateurNom() {
        return utilisateurNom;
    }

    public void setUtilisateurNom(String utilisateurNom) {
        this.utilisateurNom = utilisateurNom;
    }

    public String getUtilisateurPrenom() {
        return utilisateurPrenom;
    }

    public void setUtilisateurPrenom(String utilisateurPrenom) {
        this.utilisateurPrenom = utilisateurPrenom;
    }

    public String getUtilisateurTel() {
        return utilisateurTel;
    }

    public void setUtilisateurTel(String utilisateurTel) {
        this.utilisateurTel = utilisateurTel;
    }

    public String getUtilisateurDiscord() {
        return utilisateurDiscord;
    }

    public void setUtilisateurDiscord(String utilisateurDiscord) {
        this.utilisateurDiscord = utilisateurDiscord;
    }

    public Date getUtilisateurDate() {
        return utilisateurDate;
    }

    public void setUtilisateurDate(Date utilisateurDate) {
        this.utilisateurDate = utilisateurDate;
    }

    public String getUtilisateurRole() {
        return utilisateurRole;
    }

    public void setUtilisateurRole(String utilisateurRole) {
        this.utilisateurRole = utilisateurRole;
    }

    public Collection<Message> getMessageCollection() {
        return messageCollection;
    }

    public void setMessageCollection(Collection<Message> messageCollection) {
        this.messageCollection = messageCollection;
    }

    public Collection<Token> getTokenCollection() {
        return tokenCollection;
    }

    public void setTokenCollection(Collection<Token> tokenCollection) {
        this.tokenCollection = tokenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utilisateurId != null ? utilisateurId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.utilisateurId == null && other.utilisateurId != null) || (this.utilisateurId != null && !this.utilisateurId.equals(other.utilisateurId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "utilisateurId=" + utilisateurId + ", utilisateurEmail=" + utilisateurEmail + ", utilisateurNom=" + utilisateurNom + ", utilisateurPrenom=" + utilisateurPrenom + ", utilisateurTel=" + utilisateurTel + ", utilisateurDiscord=" + utilisateurDiscord + ", utilisateurDate=" + utilisateurDate + ", utilisateurRole=" + utilisateurRole + '}';
    }


    
}
