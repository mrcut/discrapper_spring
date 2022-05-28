
package root.entites;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;
    
    @Column(name = "message_content", unique = true, nullable=false)
    private String messageContent;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "messageCollection")
    private Collection<Utilisateur> utilisateurCollection;
    
    @JoinColumn(name = "categorie_id", referencedColumnName = "categorie_id")
    @ManyToOne(optional = false)
    private Categorie categorieId;
    
    @JoinColumn(name = "discord_id", referencedColumnName = "discord_id")
    @ManyToOne(optional = false)
    private Discord discordId;

    public Message() {
    }

    public Message(Integer messageId) {
        this.messageId = messageId;
    }

    public Message(Integer messageId, String messageContent) {
        this.messageId = messageId;
        this.messageContent = messageContent;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Collection<Utilisateur> getUtilisateurCollection() {
        return utilisateurCollection;
    }

    public void setUtilisateurCollection(Collection<Utilisateur> utilisateurCollection) {
        this.utilisateurCollection = utilisateurCollection;
    }

    public Categorie getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Categorie categorieId) {
        this.categorieId = categorieId;
    }

    public Discord getDiscordId() {
        return discordId;
    }

    public void setDiscordId(Discord discordId) {
        this.discordId = discordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (messageId != null ? messageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.messageId == null && other.messageId != null) || (this.messageId != null && !this.messageId.equals(other.messageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Message{" + "messageId=" + messageId + ", messageContent=" + messageContent + ", categorieId=" + categorieId + ", discordId=" + discordId + '}';
    }


    
}
