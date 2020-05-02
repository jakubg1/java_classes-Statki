package pl.gda.zsl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Session implements Serializable {
	
	private static final long serialVersionUID = 8280525979834362303L;
	
	@EJB(name="Player")
	PlayerLocal player;
	
	public void setPlayer(PlayerLocal player) {
		this.player = player;
	}
	
	public PlayerLocal getPlayer() {
		return this.player;
	}
}
