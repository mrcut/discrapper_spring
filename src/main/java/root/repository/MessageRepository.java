package root.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import root.entites.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

	
	public Optional<Message> findByMessageContent(String messageContent);
	
	
	@Query("select count(messageContent) from Message m")
    public int countMessages();
    	
    	
	@Query("select m from Message m where m.categorieId.categorieId = :paramId")
	public List<Message> findByCategorieId(@Param("paramId")int id);

}
    	




	

 