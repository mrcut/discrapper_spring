package root.controleurs;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import root.entites.Categorie;
import root.service.AccessSecurityService;
import root.service.CategorieService;

@RestController
@CrossOrigin(value = "*")
public class CategorieCTRL {

	
	private CategorieService catService;
	private AccessSecurityService access;
	
	@Autowired
	public CategorieCTRL(CategorieService catService, AccessSecurityService access) {
		this.catService = catService;
		this.access = access;
	
	}
	
	
	@GetMapping("/api/categories")
	public ResponseEntity<List<Categorie>> getAllCategories(HttpServletRequest request){
		boolean okAdmin = access.verifierRole(request, "admin");
		boolean okEmploye = access.verifierRole(request, "employe");
		if (okAdmin || okEmploye) {
			List<Categorie> categories = catService.getAllCategories();
			return ResponseEntity.ok(categories);
	} else {
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "accès Refusé");
	}
	}
}
