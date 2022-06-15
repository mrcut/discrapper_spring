package root.controleurs;

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

import root.entites.Discord;
import root.http.DiscordInput;
import root.service.AccessSecurityService;
import root.service.DiscordService;

@RestController
@CrossOrigin(value = "*")
public class DiscordCTRL {

	
	
	@Autowired
	private AccessSecurityService access;

	
	@Autowired
	private DiscordService disService;

	
	
	@GetMapping("/api/discords")
	public ResponseEntity<List<Discord>> getAllDiscord(HttpServletRequest request) {
		boolean okAdmin = access.verifierRole(request, "admin");
		boolean okEmploye = access.verifierRole(request, "employe");
		boolean okUser = access.verifierRole(request, "user");
		
		if(okAdmin || okEmploye || okUser) {
			List<Discord> discord = disService.getAllDiscord();
			return ResponseEntity.ok(discord);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}

	}
	
	
	@GetMapping("/api/discord/{id}")
	public ResponseEntity<Discord> getDiscordById(@PathVariable("id") int id){
		Optional<Discord> option = disService.getDiscordById(id);
		if(option.isPresent()) {
			Discord dis = option.get();
			return ResponseEntity.ok(dis);
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discord introuvable");
		}
	}
	
	
	@PostMapping("/api/discord/create")
	public ResponseEntity<Discord> createDiscord(HttpServletRequest request, @RequestBody DiscordInput input)
	throws Exception {

		boolean okAdmin = access.verifierRole(request, "admin");
		if (okAdmin) {
			Discord dis = disService.createDiscord(input.getDiscordNom(), input.getDiscordLien(), input.getDiscordChannel());

			return ResponseEntity.ok(dis);
		} else {

			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}

	}

	
	@PutMapping("/api/discord/update/{id}")
	public ResponseEntity<Discord> updateDiscord(@PathVariable Integer id , @RequestBody Discord discord) throws Exception{
		Discord dis = disService.updateDiscord(id, discord);
		return ResponseEntity.ok(dis);
	}
	

	@DeleteMapping("/api/discord/delete/{id}")
	public void deleteDiscord(@PathVariable Integer id) throws Exception{
		disService.deleteDiscord(id);
		
	}
	
}
