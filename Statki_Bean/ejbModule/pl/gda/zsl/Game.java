package pl.gda.zsl;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * Session Bean implementation class Board
 */
@Singleton
@LocalBean
public class Game implements GameRemote, GameLocal {

	private Board[] boards = new Board[2];
	
	private int player = 0;
	private int playerCount = 0;
	private boolean inProgress = false;
	
	// to print enemy's moves
	private int lastMoveNewCount = 0; // number of people who acknowledged the new move
	private int lastMoveX = 0;
	private int lastMoveY = 0;
	private boolean lastMoveSuccess = false;
	
    public Game() {
    	this.boards[0] = new Board();
    	this.boards[1] = new Board();
    }
    
    public int getPlayer() {
    	return player;
    }
    
    public boolean getInProgress() {
    	return inProgress;
    }
    
    private int getEnemyPlayerID(int playerID) {
    	return 1 - playerID;
    }
    
    public String getGraphics(int playerID, boolean enemy) {
    	// "playerID" means the ID of the player who GETS the map.
    	if (enemy)
    		playerID = getEnemyPlayerID(playerID);
    	return boards[playerID].getGraphics(enemy);
    }
    
    public int join() {
    	if (inProgress)
    		return -1; // Can't join, already started
    	playerCount++;
    	if (playerCount == 2)
    		start();
    	return playerCount - 1;
    }
    
    private void start() {
    	boards[0].init();
    	boards[1].init();
    	
    	// just three pseudo-random ships
    	boards[0].placeShip(3, 1, 3, true);
    	boards[0].placeShip(6, 8, 3, false);
    	boards[0].placeShip(4, 6, 3, false);
    	boards[1].placeShip(6, 0, 3, false);
    	boards[1].placeShip(3, 3, 3, true);
    	boards[1].placeShip(6, 4, 3, true);
    	
    	inProgress = true;
    }
    
    // Returns negative when error, 0 when missed, 1 when hit
    public byte shoot(int x, int y) {
    	if (!inProgress)
    		return -3; // Game not started yet
    	if (x < 0 || x > 10 || y < 0 || y > 10)
    		return -1; // Out of bounds
    	
    	Board board = boards[getEnemyPlayerID(player)];
    	if (board.getHit(x, y))
    		return -2; // Already hit there
    	// Shoot
    	board.setHit(x, y, true);
    	// Change the player if missed
    	if (!board.getShip(x, y))
    		player = getEnemyPlayerID(player);
    	
    	// Communicate with the enemy first
    	lastMoveNewCount = 2;
    	lastMoveX = x;
    	lastMoveY = y;
    	lastMoveSuccess = board.getShip(x, y);
    	
    	return (byte) (lastMoveSuccess ? 1 : 0);
    }
    
    public boolean getLastMoveNew() {
    	return lastMoveNewCount > 0;
    }
    
    public int getLastMoveX() {
    	return lastMoveX;
    }
    
    public int getLastMoveY() {
    	return lastMoveY;
    }
    
    public boolean getLastMoveSuccess() {
    	return lastMoveSuccess;
    }
    
    public void setLastMoveNew() {
    	// You can only set it to false
    	lastMoveNewCount--;
    }

}
