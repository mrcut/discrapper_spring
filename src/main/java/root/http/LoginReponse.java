package root.http;

public class LoginReponse {
	
	private int id;
	private String nom;
	private String prenom;
	private String discord;
	private String role;
	private String token;
	private String tel;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "LoginReponse [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", discord=" + discord + ", role="
				+ role + ", token=" + token + ", tel=" + tel + "]";
	}

	

	
	
}
