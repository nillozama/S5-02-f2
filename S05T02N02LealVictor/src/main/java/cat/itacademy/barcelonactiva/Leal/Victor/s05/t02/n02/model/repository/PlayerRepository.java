package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.Player;

public interface PlayerRepository extends MongoRepository<Player, String>{
	
	public Player findByUserName(String userName);
	public List<Player> findAllByOrderByAveragePlaysDesc();
	public List<Player> findAllByOrderByAveragePlaysAsc();
}
