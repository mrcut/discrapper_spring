package root.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
			
			Calendar cal = Calendar.getInstance();
			Date actual = cal.getTime();
			
			cal.add(Calendar.MINUTE, -90);
			Date expireDate = cal.getTime();
		
				if (t.getTokenExpiration().before(actual)) {
					return false;
				}
		
			List<Token> liste = tokenRepository.findExpiredTokens(expireDate);
			tokenRepository.deleteAll(liste);


			Utilisateur usr = t.getUtilisateur();
			String role = usr.getUtilisateurRole();				
			return role.equals(nomRole);		
		}
		
		return false;
	}
}
