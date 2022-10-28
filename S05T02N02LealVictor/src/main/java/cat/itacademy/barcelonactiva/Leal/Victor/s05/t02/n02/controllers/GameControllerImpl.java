package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.DiceRoll;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain.Player;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.services.DiceRollServiceImpl;
import cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.services.PlayerServiceImpl;


//Notación para indicar que es un controlador de tipo Rest
@RestController
//Notación para indicar el contexto de nuestros endpoint
@RequestMapping("/players")
public class GameControllerImpl implements GameController{

	// Inyección de dependencias
	@Autowired
	private PlayerServiceImpl playerService;
	@Autowired
	private DiceRollServiceImpl diceRollService;

	@Override
	@PostMapping()
	public ResponseEntity<String> addPlayer(@RequestParam (defaultValue="ANÒNIM") String userName) { // crea un jugador/a

		ResponseEntity<String> responseEntity;
		
		if (userName.equalsIgnoreCase("ANÒNIM")||!playerService.findPlayerByName(userName)) {

			Player player = new Player (userName);
			try {
				playerService.save(player);
				responseEntity = new ResponseEntity<>("S'ha creat el jugador "+userName, HttpStatus.CREATED);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		else {

			responseEntity = new ResponseEntity<>("Aquest nom d'usuari ja existeix.", HttpStatus.IM_USED);
		}

		return responseEntity;
	}

	@Override
	@PutMapping()
	public ResponseEntity<String> updatePlayer(@RequestBody Player player) { // modifica el nom del jugador/a

		ResponseEntity<String> responseEntity;
		String name=player.getUserName();

		if (playerService.getPlayerById(player.getId()) != null) {

			playerService.update(player);

			responseEntity = new ResponseEntity<>("S'ha modificat el nom a " +name , HttpStatus.OK);
		} else {

			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@PostMapping("/{id}")
	@ResponseBody
	public ResponseEntity<String> playGame(@PathVariable("id") String  idPlayer) { // un jugador/a específic realitza una
																				// tirada dels daus

		ResponseEntity<String> responseEntity;

		if (playerService.getPlayerById(idPlayer) != null) {
			


			Player player=playerService.getPlayerById(idPlayer);
			
			DiceRoll diceRoll = diceRollService.playGame(player);
			

			try {

				diceRollService.saveOrUpdate(diceRoll);
				playerService.update(player,diceRoll);

				
				responseEntity = new ResponseEntity<>(diceRoll.generateMessage(), HttpStatus.CREATED);
			} catch (Exception e) {
				responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		else {

			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	@Override
	@DeleteMapping("/{id}/games")
	@ResponseBody
	public ResponseEntity<HttpStatus> deleteDiceRolls(@PathVariable("id") String id) { // elimina les tirades del jugador/a

		ResponseEntity<HttpStatus> responseEntity;
		
		Player player=playerService.getPlayerById(id);

		try {
			diceRollService.deleteAllPlaysByPlayer(id);
			playerService.deleteAllPlaysByPlayer(player);

			responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}

	@Override
	@GetMapping("/")
	public ResponseEntity<List<Player>> getAllPlayers() { // retorna el llistat de tots els jugadors/es del sistema
																// amb el seu percentatge mitjà d’èxits

		ResponseEntity<List<Player>> responseEntity;

		try {
			List<Player> players = new ArrayList<Player>();

			players = playerService.getAllPlayers();

			if (players.isEmpty()) {
				responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			responseEntity = new ResponseEntity<List<Player>>(players, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/{id}/games")
	public ResponseEntity<List<DiceRoll>> getAllDiceRolls(@PathVariable("id") String idPlayer) { // retorna elllistat dejugades per unjugador/a

		ResponseEntity<List<DiceRoll>> responseEntity;

		try {

			List<DiceRoll> playerPlays = new ArrayList<DiceRoll>();

			playerPlays = diceRollService.findByPlayer(idPlayer);

			responseEntity = new ResponseEntity<List<DiceRoll>>(playerPlays, HttpStatus.OK);

		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking")
	public ResponseEntity<Float> getAverageRanking() { // retorna el ranking mig de tots els jugadors/es del
																	// sistema. És a dir, el percentatge mitjà d’èxits.

		ResponseEntity<Float> responseEntity = null;
		float totalAverage=0;
		
		totalAverage=playerService.getTotalAverage();
		
		try {

			responseEntity = new ResponseEntity<Float>(totalAverage, HttpStatus.OK);

		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
	}
	
	@Override
	@GetMapping("/rankingList")
	public ResponseEntity<List<Player>> getAverageRankingList() { // retorna el ranking mig de tots els jugadors/es del
																	// sistema. És a dir, el percentatge mitjà d’èxits.

		ResponseEntity<List<Player>> responseEntity = null;
		
		
		return responseEntity;
	}

	@Override
	@GetMapping("/ranking/loser")
	public ResponseEntity<Player> getWorstPlayer() { // retorna el jugador/a amb pitjor percentatge d’èxit

		ResponseEntity<Player> responseEntity;
		
		try {

			Player playerDTO = playerService.getLoser();

			responseEntity = new ResponseEntity<Player>(playerDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@Override
	@GetMapping("/ranking/winner")
	public ResponseEntity<Player> getBestPlayer() { // retorna el jugador/a amb mitjor percentatge d’èxit

		ResponseEntity<Player> responseEntity;
		
		try {

			Player playerDTO = playerService.getWinner();

			responseEntity = new ResponseEntity<Player>(playerDTO, HttpStatus.OK);
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}
}