package TicTacToe_LLD;

public class Player {
	
	private final int playerID;
	private String playerName;
	private final Symbol symbol; 

	Player(int playerId, String playerName, Symbol symbol){
		this.playerID = playerId;
		this.playerName = playerName;
		this.symbol = symbol;
	}
	
	// getter
	public int getId() {
		return playerID;
	}
	
	public String getName() {
		return playerName;
	}
	
	public Symbol getsymbol() {
		return symbol;
	}
	
	// setter
	public void setName(String playerName) {
		this.playerName = playerName;
	}

}
