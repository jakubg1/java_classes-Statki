import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pl.gda.zsl.GameRemote;

public class Statki {
	
	private static final String[] ERROR_MESSAGES = new String[] {
		"Given position is out of the board!",
		"You've already shot there!",
		"The game has not started yet!"
	};

	public static void main(String[] args) throws Exception
	{
		Scanner s = new Scanner(System.in);
		
		/*
		String tile = s.nextLine();
		int x = translateX(tile);
		int y = translateY(tile);
		if (x == -1 || y == -1)
			System.out.println("Invalid tile!");
		else
			System.out.println("(" + x + ", " + y + ")");
		*/
		
		GameRemote client = lookupRemoteHelloWorldService();

		System.out.println("XXX Warship Game XXX");
		int playerID = client.join();
		if (playerID == -1) {
			System.out.println("Game is already full!");
			s.close(); // don't forget to close the scanner
			return;
		}
		if (!client.getInProgress()) {
			System.out.println("Waiting for another player...");
			while (true)
				if (client.getInProgress())
					break;
		}
		System.out.println("Game started!");
		//System.out.println(client.getGraphics(playerID, false));
		while (client.getInProgress()) {
			System.out.println();
			if (playerID == client.getPlayer()) {
				System.out.println("Your turn!");
				System.out.println(client.getGraphics(playerID, true));
				int x, y;
				while (true) {
					System.out.print("You shoot: ");
					String tile = s.nextLine();
					x = translateX(tile);
					y = translateY(tile);
					if (x > -1 && y > -1)
						break;
					System.out.println("Illegal input!");
				}
				byte result = client.shoot(x, y);
				if (result == 0)
					System.out.println("MISS!");
				else if (result == 1)
					System.out.println("HIT!");
				else
					System.out.println(ERROR_MESSAGES[1 - result]);
			}
			else {
				System.out.println("Opponent's turn!");
				System.out.println(client.getGraphics(playerID, false));
				System.out.println("Waiting for input...");
				while (true)
					if (client.getLastMoveNew())
						break;
				System.out.println("Opponent's move: " + translateStr(client.getLastMoveX(), client.getLastMoveY()));
				if (client.getLastMoveSuccess())
					System.out.println("HIT!");
				else
					System.out.println("MISS!");
			}
			client.setLastMoveNew();
		}
		
		System.out.println("Game ended! Thanks for playing!");
		
		s.close();
	}

	private static final char[] LETTERS = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
	
	private static int translateX(String tile) {
		if (tile.length() > 0) {
			char c = tile.toUpperCase().charAt(0);
			for (int i = 0; i < 10; i++)
				if (c == LETTERS[i])
					return i;
		}
		return -1; // illegal one
	}
	
	private static final char[] DIGITS = new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	private static int translateY(String tile) {
		if (tile.length() < 2 || tile.length() > 3) // ID length protection
			return -1; // illegal one
		
		if (tile.length() == 2) { // 1 to 9 (translated: from 0 to 8)
			char c = tile.charAt(1);
			for (int i = 0; i < 10; i++)
				if (c == DIGITS[i])
					return i;
		}
		// special case for 10:
		if (tile.length() == 3 && tile.charAt(1) == '1' && tile.charAt(2) == '0')
			return 9;
		return -1; // illegal one
	}
	
	private static String translateStr(int x, int y) {
		return "" + LETTERS[x] + (y + 1);
	}
	
	private static GameRemote lookupRemoteHelloWorldService() throws NamingException
	{
		Properties jndiProperties = new Properties();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		Context context = new InitialContext(jndiProperties);
		final String appName = "Statki_BeanEAR";
		final String moduleName = "Statki_Bean";
		final String beanName = "Game";
		final String viewClassName = GameRemote.class.getName();
		return (GameRemote) context.lookup("ejb:" + appName + "/" + moduleName + "/" + beanName + "!" + viewClassName);
	}

}
