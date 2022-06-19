package root.controleurs;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import root.entites.Token;
import root.entites.Utilisateur;
import root.http.UserInput;
import root.http.UserUpdate;
import root.service.AccessSecurityService;
import root.service.UtilisateurService;

@RestController
@CrossOrigin(value = "*")
public class UtilisateurCTRL {

	
	@Autowired
	private AccessSecurityService access;

	@Autowired
	private UtilisateurService usrService;

	
	@GetMapping("/api/users")
	public ResponseEntity<List<Utilisateur>> getAllUsers(HttpServletRequest request){
		
		boolean okAdmin = access.verifierRole(request, "admin");
		
		if(okAdmin) {
			List<Utilisateur> usrs = usrService.getAllUsers();
			return ResponseEntity.ok(usrs);
		} 
		
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}
		
	}
	
	
	@GetMapping("/api/user/{id}")
	public ResponseEntity<Utilisateur> getUserById(HttpServletRequest request, @PathVariable("id") int id){
		
		boolean okAdmin = access.verifierRole(request, "admin");
		
		if(okAdmin) {
			Optional<Utilisateur> option = usrService.getUserById(id);
			
			if(option.isPresent()) {
				Utilisateur u  = option.get();
				return ResponseEntity.ok(u);
			}
			
			else {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discord introuvable");
			}
			
		} 
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}
	}
	
	
	
	@PostMapping("/api/user/create")
	public ResponseEntity<Utilisateur> createUser(HttpServletRequest request, @RequestBody UserInput input)
	throws Exception {
		
		boolean okAdmin = access.verifierRole(request, "admin");
		
		if (okAdmin) {
			Utilisateur usr = usrService.createUser(input.getEmail(), input.getMdp(),
			input.getNom(), input.getPrenom(), input.getTel(), input.getDiscord(), input.getRole());
			return ResponseEntity.ok(usr);
		} 
		
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}
	}

	
	@PutMapping("/api/user/update/{id}")
	public ResponseEntity<Utilisateur> updateUser ( HttpServletRequest request, @PathVariable Integer id, @RequestBody UserUpdate user)
	throws Exception{
		
		boolean okAdmin = access.verifierRole(request, "admin");

		if(okAdmin) {			
			Utilisateur usr = usrService.updateUser(id,user);
			return ResponseEntity.ok(usr);
		} 
		
		else  {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}	
	}
	
	
	@DeleteMapping("/api/user/delete/{id}")
	public void deleteUser(@PathVariable Integer id, HttpServletRequest request) throws Exception{
		
		boolean okAdmin = access.verifierRole(request, "admin");
		
		if (okAdmin) {
			usrService.deleteUser(id);
		}
		
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}
	}
	
	
	@PutMapping("/api/user/profile")
	public ResponseEntity<Utilisateur> editProfile (HttpServletRequest request, @RequestBody UserInput input)
	{
		
		boolean okAdmin = access.verifierRole(request, "admin");
		boolean okEmploye = access.verifierRole(request, "employe");
		boolean okUser = access.verifierRole(request, "user");

		if(okAdmin || okEmploye || okUser) {
			Optional<Token> option = access.identifierToken(request);
			Token token = option.get();
			Utilisateur usr = token.getUtilisateur();			
			usr = usrService.editProfile(usr, input.getNom(), input.getPrenom(), input.getTel(), input.getDiscord());
			return ResponseEntity.ok(usr);
		} 
		
		else  {	
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}	
	}
	

	
	@GetMapping("/api/user/script")
	public ResponseEntity<String> executeScript(HttpServletRequest request) throws IOException {
		
		boolean okAdmin = access.verifierRole(request, "admin");
		
		if (okAdmin) {
			String s = usrService.executeScript();
			return ResponseEntity.ok(s);

		} 
		
		else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}
	}
}