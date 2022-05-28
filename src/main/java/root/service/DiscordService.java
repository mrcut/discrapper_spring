package root.service;

import java.util.*;

import root.entites.Discord;

public interface DiscordService {

	public Discord createDiscord(String name, String link, String channel) throws Exception;
	
	public Discord updateDiscord(Integer id, Discord dis) throws Exception;
	
	public void deleteDiscord(Integer id) throws Exception;
	
	public List<Discord> getAllDiscord();
	
	public Optional<Discord> getDiscordById(int id);
}
