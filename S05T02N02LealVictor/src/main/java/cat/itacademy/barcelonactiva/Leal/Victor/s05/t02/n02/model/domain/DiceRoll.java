package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection="tirades")
public class DiceRoll {
	
	@Id
	private String id;
	private int score;
	private boolean winningRoll;
	private String message;
	private int dice1;
	private int dice2;
	private Player player;
	
	public DiceRoll(Player player) {
		super();
		
		dice1=calculateDiceScore();
		dice2=calculateDiceScore();
		score=(dice1+dice2);
		winningRoll=score==7;
		message=generateMessage();
		this.player=player;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean getWinningRoll() {
		return winningRoll;
	}
	public void setWinningRoll(boolean winningRoll) {
		this.winningRoll = winningRoll;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getDice1() {
		return dice1;
	}
	public void setDice1(int dice1) {
		this.dice1 = dice1;
	}
	public int getDice2() {
		return dice2;
	}
	public void setDice2(int dice2) {
		this.dice2 = dice2;
	}
	
	public int calculateDiceScore(){
		
		return  (int) (6*Math.random()+1);
	}
	
	public String generateMessage() {
		
		return winningRoll?"YOU WIN!!":"YOU LOST!!";
	}
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "DiceRoll [id=" + id + ", score=" + score + ", winningRoll=" + winningRoll + ", message=" + message
				+ ", dice1=" + dice1 + ", dice2=" + dice2 + "]";
	}
}
