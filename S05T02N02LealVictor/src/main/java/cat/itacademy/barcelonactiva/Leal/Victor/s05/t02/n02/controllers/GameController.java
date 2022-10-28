package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.DiceRoll;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.Player;

public interface GameController {
	
	public ResponseEntity<String> addPlayer(String name);
	public ResponseEntity<String> updatePlayer( Player player);
	public ResponseEntity<String> playGame(String idPlayer);
	public ResponseEntity<HttpStatus> deleteDiceRolls(String id);
	public ResponseEntity<List<Player>> getAllPlayers();
	public ResponseEntity<List<DiceRoll>> getAllDiceRolls(String idPlayer);
	public ResponseEntity<Float> getAverageRanking();
	public ResponseEntity<Player> getWorstPlayer();
	public ResponseEntity<Player> getBestPlayer();
	public ResponseEntity<List<Player>> getAverageRankingList();

}
