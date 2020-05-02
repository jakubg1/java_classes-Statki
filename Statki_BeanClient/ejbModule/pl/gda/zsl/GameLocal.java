package pl.gda.zsl;

import javax.ejb.Local;

@Local
public interface GameLocal {
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
