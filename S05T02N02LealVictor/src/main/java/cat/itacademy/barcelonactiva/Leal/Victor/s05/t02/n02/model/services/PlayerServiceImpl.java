package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.DiceRoll;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.Player;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{
	
	@Autowired
	private PlayerRepository playerRepository;

	@Override
	public List<Player> getAllPlayers() {
		
		List <Player> players=new ArrayList<Player>();
		playerRepository.findAll().forEach(p->players.add(p));
		
		return players;
	}

	@Override
	public Player getPlayerById(String id) {
		
		Player player=playerRepository.findById(id).get();
		
		return player;
	}

	@Override
	public void save(Player player) {

		playerRepository.save(player);
		
	}

	@Override
	public void update(Player player) {
		
		String userName=player.getUserName();
		player=getPlayerById(player.getId());
		player.setUserName(userName);

		playerRepository.save(player);
		
	}
	
	@Override
	public void update(Player player, DiceRoll diceRoll) {
		
		List<DiceRoll> diceRolls=player.getDiceRolls();
		diceRolls.add(diceRoll);
		player.setDiceRolls(diceRolls);
		updateAverageService(player);
		playerRepository.save(player);
	}

	@Override
	public void delete(String id) {
		
		playerRepository.deleteById(id);
		
	}

	@Override
	public Player getWinner() {
		
		List<Player> playerList=playerRepository.findAllByOrderByAveragePlaysAsc();

		Player winner=playerList.stream().max((p1, p2) -> (int) (p1.getAveragePlays() - p2.getAveragePlays())).get();
		return winner;
	}

	@Override
	public Player getLoser() {
		
		List<Player> playerList=playerRepository.findAllByOrderByAveragePlaysDesc();
		
		Player loser=playerList.stream().min((p1, p2) -> (int) (p1.getAveragePlays() - p2.getAveragePlays())).get();
		return loser;
	}

	@Override
	public float getTotalAverage() {
		
		List<Player> playerList=playerRepository.findAllByOrderByAveragePlaysAsc();
		
		double totalAverage=playerList.stream().mapToDouble(x->x.getAveragePlays()).sum();
		totalAverage=Math.round(totalAverage*100/playerList.size());

		return (float)totalAverage/100;
	}

	@Override
	public boolean findPlayerByName(String name) {
		boolean result=false;
		Player player=playerRepository.findByUserName(name);
		
		if (player!=null) {

			result=true;
		}
		
		return result;
	}
	
	public void updateAverageService(Player player) {
		
		float average = 0;
		int count = 0;
		
		if (player.getDiceRolls().size() != 0) {
			for (DiceRoll d : player.getDiceRolls()) {

				if (d.getWinningRoll()) {

					count++;
				}
			}
			average = (float) count * 100 / player.getDiceRolls().size();
		}
		average=Math.round(average*100);
		player.setAveragePlays(average/100);
	}

	@Override
	public void restartAverage(Player player) {

		player.setAveragePlays(0);
		playerRepository.save(player);
	}

	@Override
	public void deleteAllPlaysByPlayer(Player player) {
		
		List<DiceRoll> diceRolls=new ArrayList <DiceRoll>();
		player.setDiceRolls(diceRolls);
		updateAverageService(player);
		playerRepository.save(player);

	}
}
