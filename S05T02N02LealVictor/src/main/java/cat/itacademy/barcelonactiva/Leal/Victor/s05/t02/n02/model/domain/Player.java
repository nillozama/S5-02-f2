package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n02.model.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document (collection="jugadors")
public class Player {
	
	@Id
	private String id;
	private String userName;
	@DBRef
	@JsonIgnore
	private List<DiceRoll> diceRolls;
	private float averagePlays;
	
	public Player() {

	}
	
	public Player(String userName) {
		super();
		this.userName = userName;
		averagePlays=0;//
		diceRolls=new ArrayList<DiceRoll>();
	}
	
	public Player(String id, String userName) {//
		super();
		this.id=id;
		this.userName = userName;
		averagePlays=0;//
		//diceRolls=new ArrayList<DiceRoll>();//
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<DiceRoll> getDiceRolls() {
		return diceRolls;
	}
	public void setDiceRolls(List<DiceRoll> diceRolls) {
		this.diceRolls = diceRolls;
	}
	public float getAveragePlays() {
		return averagePlays;
	}
	public void setAveragePlays(float averagePlays) {
		this.averagePlays = averagePlays;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", userName=" + userName +  ", diceRolls="
				+ diceRolls + ", averagePlays=" + averagePlays + "]";
	}
}
