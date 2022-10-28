package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.DiceRoll;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.Player;

public interface DiceRollService {
	
	public List <DiceRoll> getAllDiceRolls();
	public List<DiceRoll> findByPlayer(String id);
	public void saveOrUpdate(DiceRoll diceRoll);
	public void deleteAllPlaysByPlayer(String id);
	public DiceRoll playGame(Player player);

}
