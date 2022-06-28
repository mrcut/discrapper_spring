package root.service;

import java.io.IOException;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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
    private String chiffreRegex = "[0-9]";
    private String nomPrenomRegex = "[a-zA-Z]([- ',.a-zA-Z]{0,48}[.a-zA-Z])?";
    private String emailRegex  = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private String discordRegex = "^.{3,32}#[0-9]{4}$";
    private String telRegex = "^(?:(?:\\+|00)33[\\s.-]{0,3}(?:\\(0\\)[\\s.-]{0,3})?|0)[1-9](?:(?:[\\s.-]?\\d{2}){4}|\\d{2}(?:[\\s.-]?\\d{3}){2})$";
	
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
	
	
	
    private String supprimerAccents(String texte){
        String copie = 
            Normalizer.normalize(texte, Normalizer.Form.NFD);
        copie = 
           copie.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return copie;
    }
	
	public Utilisateur createUser(String email, String mdp, String nom, String prenom,
			String tel, String discord, String role) throws Exception {

		HashMap<String, String> erreurs = new HashMap<>();
		
		Optional<Utilisateur> option = usrRepository.findEmail(email);
		Optional<Utilisateur> option2 = usrRepository.findDiscord(discord);
		
		
		if (email == null || email.trim().isEmpty()) {
			erreurs.put("erEmail", "Email obligatoire");
		}
		
			else {
				email = email.trim();	
				if(!email.matches(emailRegex)) {
					erreurs.put("erEmail", "Email invalide");
			} 
			else {
				if(option.isPresent()) {
					erreurs.put("erEmail","Email déjà utilisé");
				}
			}
		}
		
		
		if (mdp == null || mdp.isEmpty()) {
			erreurs.put("erMdp", "Mot de passe obligatoire");
		}
		
			else {
				Pattern p = Pattern.compile(chiffreRegex);
				Matcher m = p.matcher(mdp);
					if(mdp.length() < 8 || !m.find()) {
						erreurs.put("erMdp", "Au Moins 8 caractères et au moins 1 chiffre");
					}
			}
		
		
		if (nom == null || nom.trim().isEmpty()) {
			erreurs.put("erNom", "Nom obligatoire");	
		}
			else {
				nom = nom.trim();
				String nomSansAccent = supprimerAccents(nom);
				if(!nomSansAccent.matches(nomPrenomRegex)){
					erreurs.put("erNom", "Nom invalide");
				}
			}
		
		if (prenom == null || prenom.trim().isEmpty()) {
			erreurs.put("erPrenom", "Prénom obligatoire");	
		}
		else {
            prenom = prenom.trim();
            String prenomSansAccent = supprimerAccents(prenom);
            if(!prenomSansAccent.matches(nomPrenomRegex)){
                erreurs.put("erPrenom", "Prénom invalide");
            }
        }
		
		
		if (!tel.isEmpty()) {
			tel = tel.trim();
			if(!tel.matches(telRegex)) {
				erreurs.put("erTel", "Tél invalide");
			}
		}
		
		if (!discord.isEmpty()) {
			discord = discord.trim();
			if(!discord.matches(discordRegex)) {
				erreurs.put("erDiscord", "Pseudo Discord invalide");	
			}
			else {
				if(option2.isPresent()) {
					erreurs.put("erDiscord", "Pseudo Discord déjà utilisé");
				}
			}
			
		}
				
        if(!erreurs.isEmpty()){
            ObjectMapper jsonMapper = new ObjectMapper();
            String erreursJson = jsonMapper.writeValueAsString(erreurs);
            throw new Exception (erreursJson);
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
