package root.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import root.entites.Discord;

public interface DiscordRepository extends JpaRepository<Discord, Integer> {
	
	
	@Query("select d from Discord d WHERE d.discordNom = :paramNom OR d.discordLien= :paramLien OR d.discordChannel= :paramChannel")
	public List<Discord> findDiscord(@Param("paramNom") String name,
			@Param("paramLien") String link,
			@Param("paramChannel") String channel);
	
}
 