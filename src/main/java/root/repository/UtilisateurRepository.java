package root.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import root.entites.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
	
	
	@Query("select u from Utilisateur u WHERE u.utilisateurEmail= :paramEmail OR u.utilisateurDiscord= :paramDiscord")
	public List<Utilisateur> findUser(@Param("paramEmail") String email, 
			@Param("paramDiscord") String discord);



	@Query("select u From Utilisateur u WHERE u.utilisateurEmail= :paramEmail")
	public Optional<Utilisateur> findEmail(@Param("paramEmail")String email);
	
}
