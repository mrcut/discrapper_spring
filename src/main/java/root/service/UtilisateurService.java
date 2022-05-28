package root.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import root.entites.Utilisateur;
import root.http.LoginReponse;
import root.http.UserUpdate;

public interface UtilisateurService {
	
	public LoginReponse login(String email, String password) throws Exception;
	
	public String executeScript() throws IOException;
	
	public Utilisateur createUser(String email, String mdp, String nom, String prenom,
			String tel, String discord, String role) throws Exception;
	
	public Utilisateur editProfile(Utilisateur usr, String newNom, String newPrenom,
			String newTel, String newDiscord);
	
	public List<Utilisateur> getAllUsers();
	
	public Optional<Utilisateur> getUserById(int id);

	public void deleteUser(int id) throws Exception;
	
	public Utilisateur updateUser(Integer id, UserUpdate usr) throws Exception;
	
}
