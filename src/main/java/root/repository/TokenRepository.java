package root.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import root.entites.Token;

public interface TokenRepository extends JpaRepository<Token, Integer>{
	
	
	@Query("select t from Token t where t.tokenCode = :paramValeur")
	public Optional<Token> selectByValeur(@Param("paramValeur") String valeur);
<<<<<<< HEAD
	
	
	@Query("select t from Token t where t.tokenExpiration < :paramDate")
	public List<Token> findExpiredTokens(@Param("paramDate") Date date);
	
	
}
=======
}

>>>>>>> 31a59aacc0dfdd1f7dac9a8bde66a3649397ab1f
