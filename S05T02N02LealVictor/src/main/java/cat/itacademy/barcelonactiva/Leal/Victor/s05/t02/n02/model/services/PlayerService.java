package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.services;

import java.util.List;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.DiceRoll;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.Player;

public interface PlayerService {
	
	public List <Player> getAllPlayers();
	public Player getPlayerById(String playerId);
	public void save(Player player);
	public void update(Player player);
	public void update(Player player, DiceRoll diceRoll);
	public void delete(String playerId);
	public float getTotalAverage();
	public Player getWinner();
	public Player getLoser();
	public void restartAverage(Player player);
	public void deleteAllPlaysByPlayer(Player player);
	public boolean findPlayerByName(String name);
}
