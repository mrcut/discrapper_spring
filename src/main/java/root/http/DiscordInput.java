package root.http;

public class DiscordInput {
	
	private String discordNom;
	private String discordLien;
	private String discordChannel;
	
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
	public String getDiscordChannel() {
		return discordChannel;
	}
	public void setDiscordChannel(String discordChannel) {
		this.discordChannel = discordChannel;
	}
	@Override
	public String toString() {
		return "DiscordInput [discordNom=" + discordNom + ", discordLien=" + discordLien + ", discordChannel="
				+ discordChannel + "]";
	}

	
	
	
}
