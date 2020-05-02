package pl.gda.zsl;

import javax.ejb.Remote;

@Remote
public interface GameRemote {
	int getPlayer();
	boolean getInProgress();
	String getGraphics(int playerID, boolean enemy);
	int join();
	byte shoot(int x, int y);
	boolean getLastMoveNew();
	int getLastMoveX();
	int getLastMoveY();
	boolean getLastMoveSuccess();
	void setLastMoveNew();
}
