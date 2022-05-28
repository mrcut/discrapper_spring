 package root.controleurs;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
 import org.springframework.web.bind.annotation.CrossOrigin;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RestController;
 import org.springframework.web.server.ResponseStatusException;

 import root.http.LoginInfo;
 import root.http.LoginReponse;
 import root.service.UtilisateurService;

 @RestController
 @CrossOrigin(value="*") 
 public class LoginCTRL {
 	
 	
 	
 	private UtilisateurService usrService;
 	
 	
 	@Autowired
 	public LoginCTRL(UtilisateurService usrService) {
 		this.usrService = usrService;
 	}


 	
 	@PostMapping("/api/login")
 	public ResponseEntity<LoginReponse> login(@RequestBody LoginInfo infos){
 		
 		try {
 			LoginReponse lr = usrService.login(infos.getEmail(),infos.getPassword());
 			return ResponseEntity.ok(lr);
 		} catch  (Exception ex) {
 			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
 		}
 	}

 }
