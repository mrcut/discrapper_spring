package root.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import root.entites.Token;

public interface AccessSecurityService {
	public Optional<Token> identifierToken(HttpServletRequest request);
	public boolean verifierRole(HttpServletRequest request, String nomRole);

}
