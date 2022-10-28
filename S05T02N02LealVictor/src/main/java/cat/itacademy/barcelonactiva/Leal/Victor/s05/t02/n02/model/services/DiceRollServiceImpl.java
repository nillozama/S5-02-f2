package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.DiceRoll;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.Player;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.repository.DiceRollRepository;

@Service
public class DiceRollServiceImpl implements DiceRollService{
	
	@Autowired
	private DiceRollRepository drRepository;

	@Override
	public List<DiceRoll> getAllDiceRolls() {
		
		List <DiceRoll> allPlays=new ArrayList<DiceRoll>();
		drRepository.findAll().forEach(dr->allPlays.add(dr));
		
		return allPlays;
	}

	@Override
	public List<DiceRoll> findByPlayer(String id) {

		return drRepository.findByPlayerId(id);
	}

	@Override
	public void saveOrUpdate(DiceRoll diceRoll) {
		
		drRepository.save(diceRoll);	
	}

	@Override
	public void deleteAllPlaysByPlayer(String id) {
		
		List<DiceRoll> diceRolls=drRepository.findByPlayerId(id);

		diceRolls.forEach(i->drRepository.deleteById(i.getId()));
		
	}

	@Override
	public DiceRoll playGame(Player player) {

		return new DiceRoll(player);
	}
}
