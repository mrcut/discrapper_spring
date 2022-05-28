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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import root.entites.Message;
import root.service.AccessSecurityService;
import root.service.MessageService;

@RestController
@CrossOrigin(value = "*")
public class MessageCTRL {

	
	
	private AccessSecurityService access;
	private MessageService msgService;

	
	@Autowired
	public MessageCTRL(MessageService msgService, AccessSecurityService access) {
		this.msgService = msgService;
		this.access = access;
	}

	
	
	@GetMapping("/api/messages")
	public ResponseEntity<List<Message>> getAllMessages(HttpServletRequest request) {
		boolean okAdmin = access.verifierRole(request, "admin");
		boolean okEmploye = access.verifierRole(request, "employe");

		if (okAdmin || okEmploye) {
			List<Message> messages = msgService.getAllMessages();
			return ResponseEntity.ok(messages);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}
	}

	
	@GetMapping("/api/message/{id}")
	public ResponseEntity<Message> getMessageById(@PathVariable("id") int id ){
		//boolean okAdmin = access.verifierRole(request, "admin");
		//boolean okEmploye = access.verifierRole(request, "employe");

		//if(okAdmin || okEmploye) {	
		Optional<Message> option = msgService.getMessageById(id);
		if(option.isPresent()) {
			Message msg = option.get();
			return ResponseEntity.ok(msg);

		}
		else {
			
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Message introuvable");
		}
		
		
	}

	
	@DeleteMapping("/api/message/{id}")
	public void deleteMessage(@PathVariable Integer id) throws Exception{
		msgService.deleteMessage(id);
		
	}
	 
	
	@GetMapping("/api/category/{id}")
	public ResponseEntity<List<Message>> getAllMessagesByCategorie(HttpServletRequest request, @PathVariable("id") int id){
		boolean okAdmin = access.verifierRole(request, "admin");
		boolean okEmploye = access.verifierRole(request, "employe");

		if (okAdmin || okEmploye) {
			List<Message> messages = msgService.getAllMessagesByCategorie(id);
			return ResponseEntity.ok(messages);
		} else {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès refusé");
		}
	} 
}
