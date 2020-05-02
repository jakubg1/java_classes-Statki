package pl.gda.zsl;

import javax.ejb.Stateful;

/**
 * Session Bean implementation class Player
 */
@Stateful
public class Player implements PlayerRemote, PlayerLocal {
	private int counter = 0;
	private String username;
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Player() {
		
	}
}
