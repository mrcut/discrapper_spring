package root.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import root.entites.Discord;
import root.repository.DiscordRepository;

@Service
public class DiscordServiceImpl implements DiscordService {

	private DiscordRepository discordRepository;

	@Autowired
	public DiscordServiceImpl(DiscordRepository discordRepository) {
		this.discordRepository = discordRepository;
	}

	public List<Discord> getAllDiscord(){
	
		List<Discord> discord = discordRepository.findAll();
		return discord;
	}
	
	
	public Optional<Discord> getDiscordById(int id){
		return discordRepository.findById(id);
	}
	
	

	public Discord createDiscord(String name, String link, String channel) throws Exception {
		
		
		if (name == null || name.trim().isEmpty()) {
			throw new Exception("Erreur, Nom du Discord obligatoire");
		}
		
		if (link == null || link.trim().isEmpty()) {
			throw new Exception("Erreur, Lien du Discord obligatoire");
		}
		
		if (channel == null || channel.trim().isEmpty()) {
			throw new Exception("Erreur, Id du Channel obligatoire");
		}
		
		List<Discord> liste = discordRepository.findDiscord(name, link, channel);
		if (liste.size() > 0) {

			for(Discord d : liste) {
				if(d.getDiscordNom().equals(name))
				{
					throw new Exception("Erreur, ce Discord existe déjà");
				}
				if(d.getDiscordLien().equals(link))
				{
					throw new Exception("Erreur, ce Lien Discord existe déjà");
				}
				if(d.getDiscordChannel().equals(channel))
				{
					throw new Exception("Erreur, ce Channel existe déjà");
				}
			
		}
			}
			
			
			Discord dis = new Discord();
			dis.setDiscordNom(name);
			dis.setDiscordLien(link);
			dis.setDiscordChannel(channel);

			discordRepository.save(dis);

			return dis;

		}
	
	public Discord updateDiscord(Integer id, Discord dis) throws Exception {
		Optional<Discord> option = discordRepository.findById(id);
		System.out.println(option);
		
		
		if(option.isPresent()) {
			List<Discord> liste = discordRepository.findDiscord(dis.getDiscordNom(), dis.getDiscordLien(), dis.getDiscordChannel());
		
			
			Discord d = option.get();
			d.setDiscordNom(dis.getDiscordNom());
			d.setDiscordLien(dis.getDiscordLien());
			d.setDiscordChannel(dis.getDiscordChannel());
			discordRepository.save(d);
			return d;
			
		}
		
		throw new Exception("Erreur, ce Discord n'existe pas");
	}
	
	



public void deleteDiscord(Integer id) throws Exception {
	Optional<Discord> option = discordRepository.findById(id);
	
	if(option.isPresent()) {
		Discord d = option.get();
		discordRepository.delete(d);
	}
	else {
	throw new Exception("Erreur, ce Discord n'existe pas");
}

}
}
	



