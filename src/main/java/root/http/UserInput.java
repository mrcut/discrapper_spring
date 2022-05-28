package root.http;

public class UserInput {

	private String email;
	private String mdp;
	private String nom;
	private String prenom;
	private String tel;
	private String discord;
	private String role;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMdp() {
		return mdp;
	}
	
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getDiscord() {
		return discord;
	}
	
	public void setDiscord(String discord) {
		this.discord = discord;
	}
	
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserInput [email=" + email + ", mdp=" + mdp + ", nom=" + nom + ", role=" + role + "]";
	}

	
	
}
