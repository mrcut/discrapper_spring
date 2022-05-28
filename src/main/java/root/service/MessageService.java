package root.service;

import java.util.*;

import root.entites.Message;

public interface MessageService {

	public List<Message> getAllMessages();
	
	public Optional<Message> getMessageById(int id);
	
	public void deleteMessage(Integer id) throws Exception;
	
	public List<Message> getAllMessagesByCategorie(int id);
}
