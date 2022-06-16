
package root.entites;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer tokenId;
    
    @Column(name = "token_code", unique=true, nullable=false)
    private String tokenCode;
    
    @Column(name = "token_expiration", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpiration;
    
   
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "utilisateur_id", nullable = false)
    @ManyToOne(optional = false)
    private Utilisateur utilisateur;

    public Token() {
    }

    
    
    public Token(String tokenCode, Date tokenExpiration) {
		super();
		this.tokenCode = tokenCode;
		this.tokenExpiration = tokenExpiration;
	}



	public Token(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public Token(Integer tokenId, String tokenCode, Date tokenExpiration) {
        this.tokenId = tokenId;
        this.tokenCode = tokenCode;
        this.tokenExpiration = tokenExpiration;
    }

    public Integer getTokenId() {
        return tokenId;
    }

    public void setTokenId(Integer tokenId) {
        this.tokenId = tokenId;
    }

    public String getTokenCode() {
        return tokenCode;
    }

    public void setTokenCode(String tokenCode) {
        this.tokenCode = tokenCode;
    }

    public Date getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Date tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tokenId != null ? tokenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Token)) {
            return false;
        }
        Token other = (Token) object;
        if ((this.tokenId == null && other.tokenId != null) || (this.tokenId != null && !this.tokenId.equals(other.tokenId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Token{" + "tokenId=" + tokenId + ", tokenCode=" + tokenCode + ", tokenExpiration=" + tokenExpiration + '}';
    }


}
