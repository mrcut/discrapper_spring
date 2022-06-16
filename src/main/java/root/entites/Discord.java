
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
public class Discord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discord_id")
    private Integer discordId;
    
    @Column(name = "discord_channel", unique=true, nullable=false)
    private String discordChannel;
    
    @Column(name = "discord_nom", unique=true, nullable=false)
    private String discordNom;
    
    @Column(name = "discord_lien", unique=true, nullable=false)
    private String discordLien;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "discordId")
    private Collection<Message> messageCollection;

    public Discord() {
    }

    public Discord(Integer discordId) {
        this.discordId = discordId;
    }

    public Discord(Integer discordId, String discordChannel, String discordNom, String discordLien) {
        this.discordId = discordId;
        this.discordChannel = discordChannel;
        this.discordNom = discordNom;
        this.discordLien = discordLien;
    }

    public Integer getDiscordId() {
        return discordId;
    }

    public void setDiscordId(Integer discordId) {
        this.discordId = discordId;
    }

    public String getDiscordChannel() {
        return discordChannel;
    }

    public void setDiscordChannel(String discordChannel) {
        this.discordChannel = discordChannel;
    }

    public String getDiscordNom() {
        return discordNom;
    }

    public void setDiscordNom(String discordNom) {
        this.discordNom = discordNom;
    }

    public String getDiscordLien() {
        return discordLien;
    }

    public void setDiscordLien(String discordLien) {
        this.discordLien = discordLien;
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
        hash += (discordId != null ? discordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Discord)) {
            return false;
        }
        Discord other = (Discord) object;
        if ((this.discordId == null && other.discordId != null) || (this.discordId != null && !this.discordId.equals(other.discordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Discord{" + "discordId=" + discordId + ", discordChannel=" + discordChannel + ", discordNom=" + discordNom + ", discordLien=" + discordLien + '}';
    }


    
}
