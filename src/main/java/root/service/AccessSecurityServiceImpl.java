package root.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entites.Token;
import root.entites.Utilisateur;
import root.repository.TokenRepository;

@Service
public class AccessSecurityServiceImpl implements AccessSecurityService {
	
	private TokenRepository tokenRepository;
	
	@Autowired
	public AccessSecurityServiceImpl(TokenRepository tokenRepository) {
		this.tokenRepository = tokenRepository;
	}

	@Override
	public Optional<Token> identifierToken(HttpServletRequest request){
		String valeurToken = request.getHeader("Authorization");

		 
		if(valeurToken == null) {
			return Optional.empty();
		}
		valeurToken = valeurToken.replace("Bearer ", "");
		Optional<Token> option = tokenRepository.selectByValeur(valeurToken);

	
		return option;
	}

	@Override
	public boolean verifierRole(HttpServletRequest request, String nomRole) {
		
		Optional<Token> option = identifierToken(request);
		if(option.isPresent()) {
			Token t = option.get();
			
			// 1) Si le token a expiré return false
			
			// 2) Supprimer les tokens qui ont expirés
		
			// 3) Si le token est bon
			Utilisateur usr = t.getUtilisateur();
			String role = usr.getUtilisateurRole();
					
			return role.equals(nomRole);
			
		}
		return false;
	}
}
