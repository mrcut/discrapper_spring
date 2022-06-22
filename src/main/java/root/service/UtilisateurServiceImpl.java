package root.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entites.Token;
import root.entites.Utilisateur;
import root.http.LoginReponse;
import root.http.UserUpdate;
import root.repository.MessageRepository;
import root.repository.TokenRepository;
import root.repository.UtilisateurRepository;
import root.security.PasswordEncoderService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	
	private UtilisateurRepository usrRepository;
	private PasswordEncoderService encodeur;
	private TokenRepository tokenRepository;
	private MessageRepository msgRepository;

	
	@Autowired
	public UtilisateurServiceImpl(UtilisateurRepository usrRepository, PasswordEncoderService encodeur,
			TokenRepository tokenRepository, MessageRepository msgRepository) {
		this.usrRepository = usrRepository;
		this.encodeur = encodeur;
		this.tokenRepository = tokenRepository;
		this.msgRepository = msgRepository;
	}

	
	public List<Utilisateur> getAllUsers(){		
		
		List<Utilisateur> usrs = usrRepository.findAll();
		return usrs;
	}
	
	
	public Optional<Utilisateur> getUserById(int id) {
		
		return usrRepository.findById(id);
	}
	
	
	public Utilisateur createUser(String email, String mdp, String nom, String prenom,
			String tel, String discord, String role) throws Exception {

		
		if (email == null || email.trim().isEmpty()) {
			throw new Exception("Erreur, Email obligatoire");
		}
		
		if (mdp == null || mdp.isEmpty()) {
			throw new Exception("Erreur, Mdp obligatoire");
		}
		
		if (nom == null || nom.trim().isEmpty()) {
			throw new Exception("Erreur, Nom obligatoire");
		}
		
		if (prenom == null || prenom.trim().isEmpty()) {
			throw new Exception("Erreur, Prenom obligatoire");
		}
		
		if (role == null || role.isEmpty()) {
			throw new Exception("Erreur, Role obligatoire");
		}
		
		List<Utilisateur> liste = usrRepository.findUser(email, discord);
		
		if (liste.size() > 0) {

			for (Utilisateur u : liste) {
							
				if (u.getUtilisateurEmail().equals(email)) {
					throw new Exception("Erreur, cet Email est déjà pris");
				}
						
				if (u.getUtilisateurDiscord().equals(discord)) {

					throw new Exception("Erreur, cet identifiant Discord est déjà pris");
				}
						
			}
		}
		
	
		Utilisateur usr = new Utilisateur();
		String encodedMdp = encodeur.hasher(mdp);
		Date date = new Date();

		usr.setUtilisateurEmail(email);
		usr.setUtilisateurMdp(encodedMdp);
		usr.setUtilisateurNom(nom);
		usr.setUtilisateurPrenom(prenom);
		usr.setUtilisateurTel(tel);
		usr.setUtilisateurDiscord(discord);
		usr.setUtilisateurRole(role);
		usr.setUtilisateurDate(date);

		usrRepository.save(usr);
		return usr;

	}
	
	
	public Utilisateur updateUser(Integer id, UserUpdate usr) throws Exception{
		
		Optional<Utilisateur> option = usrRepository.findById(id);
		
		if(option.isPresent()) {
			Utilisateur u = option.get();
			u.setUtilisateurEmail(usr.getEmail());
			u.setUtilisateurNom(usr.getNom());
			u.setUtilisateurPrenom(usr.getPrenom());
			u.setUtilisateurTel(usr.getTel());
			u.setUtilisateurDiscord(usr.getDiscord());
			u.setUtilisateurRole(usr.getRole());
			usrRepository.save(u);
			return u ;
		}
		
		throw new Exception("Erreur, cet Utilisateur n'existe pas");
	}
	
	
	public void deleteUser(int id) throws Exception {
	
		Optional<Utilisateur> option = usrRepository.findById(id);
	
		if(option.isPresent()) {
			Utilisateur u = option.get();
			usrRepository.delete(u);
		}
		
		else {
			throw new Exception("Erreur, cet Utilisateur n'existe pas");
		}
	}
	

	public Utilisateur editProfile(Utilisateur usr, String newNom, String newPrenom,
			String newTel, String newDiscord) {

			usr.setUtilisateurNom(newNom);
			usr.setUtilisateurPrenom(newPrenom);
			usr.setUtilisateurTel(newTel);
			usr.setUtilisateurDiscord(newDiscord);
			usrRepository.save(usr);
			return usr;
	}
	
	
	
	private void exec() throws IOException {
		
		String urlScript = "c:\\Users\\mrcut\\Desktop\\Stage\\discord_insert.py \"";
		String fetching = "python " + urlScript;   
		String[] commandToExecute = new String[] { "cmd.exe", "/c", fetching };
		Runtime.getRuntime().exec(commandToExecute);
		;
	}

	
	public String executeScript() throws IOException {
		
		int count = msgRepository.countMessages();
		int newCount ;
		int diffCount;

		
			exec();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			newCount = msgRepository.countMessages();
			diffCount = newCount - count;
			if (diffCount == 0) {
				return "Pas de nouveau message";
			}
			if (diffCount == 1) {
				return "1 message ajouté";
			}

			return diffCount + " messages ajoutés";

	
	}
	
	
	public LoginReponse login(String email, String password) throws Exception {
		
		LoginReponse lr = new LoginReponse();

		email = email.trim();
		Optional<Utilisateur> option = usrRepository.findEmail(email);

		boolean erreur = false;

		if (option.isPresent()) {
			Utilisateur usr = option.get();

			boolean ok = encodeur.verifier(password, usr.getUtilisateurMdp());

			if (ok) {

				Token token = genererToken();
				token.setUtilisateur(usr);
				tokenRepository.save(token);

				lr.setToken(token.getTokenCode());
				lr.setNom(usr.getUtilisateurNom());
				lr.setPrenom(usr.getUtilisateurPrenom());
				lr.setDiscord(usr.getUtilisateurDiscord());
				lr.setRole(usr.getUtilisateurRole());
				lr.setTel(usr.getUtilisateurTel());
			} else {
				erreur = true;
			}

		} else {
			erreur = true;
		}
		if (erreur) {
			throw new Exception("Erreur sur l'email ou le mot de passe");
		}

		return lr;
	}

	
	private Token genererToken() {
		
		UUID uuid = UUID.randomUUID();
		String valeur = uuid.toString();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 90);

		Date expireDate = cal.getTime();
		return new Token(valeur, expireDate);
	}

}
