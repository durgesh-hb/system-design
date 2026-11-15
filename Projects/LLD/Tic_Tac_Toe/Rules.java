package TicTacToe_LLD;

public interface Rule {
	
	//contract class's
	boolean isValidMove(int row, int col, Board board);
	
	boolean checkWin(int row, int col, Board board,Symbol symbol);
	
	boolean isDraw(Board board);

}
