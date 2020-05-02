package pl.gda.zsl;

public class Board {

	private final char[] LETTERS = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
	
	private boolean[][] hits = new boolean[10][10];
	private boolean[][] ships = new boolean[10][10];
	
    public Board() {
    	init();
    }
    
    public void setHit(int x, int y, boolean state) {
    	hits[x][y] = state;
    }

    public boolean getHit(int x, int y) {
    	return hits[x][y];
    }
    
    public void setShip(int x, int y, boolean state) {
    	ships[x][y] = state;
    }

    public boolean getShip(int x, int y) {
    	return ships[x][y];
    }
    
    public void init() {
    	for (int i = 0; i < 10; i++) {
    		for (int j = 0; j < 10; j++) {
    			setHit(i, j, false);
    			setShip(i, j, false);
    		}
    	}
    }
    
    public void placeShip(int x, int y, int length, boolean vertical) {
    	for (int i = 0; i < length; i++) {
    		if (vertical)
    			setShip(x, y + i, true);
    		else
    			setShip(x + i, y, true);
    	}
    }
    
    public char getSymbol(int x, int y, boolean enemy) {
    	boolean isHit = getHit(x, y);
    	boolean isShip = getShip(x, y);
    	if (enemy) {
    		if (!isHit)
    			return '.';
    		// if it's hit:
    		if (isShip)
    			return 'X';
    		return '*';
    	} else {
    		if (isHit)
    			if (isShip)
    				return '#';
    			else
    				return '*';
    		// if it's not hit:
    		if (isShip)
    			return 'X';
    		return '.';
    	}
    }
    
    public String getGraphics(boolean enemy) {
    	String s = enemy ? "=== OPPONENT SHIPS ===" : "===== YOUR SHIPS =====";
    	s += "\n  1 2 3 4 5 6 7 8 9 10";
    	for (int i = 0; i < 10; i++) {
    		String line = "" + LETTERS[i];
    		for (int j = 0; j < 10; j++) {
    			line += " " + getSymbol(i, j, enemy);
    		}
    		s += "\n" + line;
    	}
    	return s;
    }

}
