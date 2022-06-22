package root.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entites.Categorie;
import root.repository.CategorieRepository;

@Service
public class CategorieServiceImpl implements CategorieService {

		@Autowired
		private CategorieRepository catRepo;
		
		
		public List<Categorie> getAllCategories(){		
			
			List<Categorie> categories = catRepo.findAll();
			return categories;
		}	
}
