package root.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entites.Message;
import root.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService{
	
	
	private MessageRepository msgRepository;
	
	
	
	@Autowired
	public MessageServiceImpl(MessageRepository msgRepository) {
		this.msgRepository = msgRepository;
	}




	public List<Message> getAllMessages(){
		
		// regex et controles
		
		List<Message> messages = msgRepository.findAll();
		return messages;
	}

	
	
	
	public Optional<Message> getMessageById(int id){
		return msgRepository.findById(id);
		}

	
	
	public List<Message> getAllMessagesByCategorie(int id){
		return msgRepository.findByCategorieId(id);
	}

	


	public void deleteMessage(Integer id) throws Exception {
	Optional<Message> option = msgRepository.findById(id);
	
	if(option.isPresent()) {
		Message m = option.get();
		msgRepository.delete(m);
	}
	else {
	throw new Exception("Erreur, ce Message n'existe pas");
}

}
	}