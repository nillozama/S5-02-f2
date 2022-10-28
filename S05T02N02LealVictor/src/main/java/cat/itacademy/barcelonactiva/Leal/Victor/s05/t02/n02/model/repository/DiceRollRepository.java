package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.DiceRoll;

public interface DiceRollRepository extends MongoRepository<DiceRoll, String>{

	Iterable<DiceRoll> findPlaysByPlayer(String id);

	void deleteAllById(String id);
	
	public List<DiceRoll> findByPlayerId(String id);

}

