package TicTacToe;

public class standardRules implements Rule{
	
	@Override
	public boolean isValidMove(int row, int col, Board board) {
		
		if(row >= 0 && col >= 0 &&
		   row < board.getsize() && col < board.getsize()) {
			
			return (board.isCellEmpty(row, col));
		}
		
		return false;
	}

	@Override
	public boolean checkWin(int row, int col, Board board, Symbol symbol) {
		
		int size = board.getsize();
		// Horizontal
		for(int i=0; i<size; i++) {
			boolean win = true;
			for(int j=0; j<size; j++) {
				if(board.getsymbol(i, j) != symbol) {
					win = false;
					break;
				}
			}
			if(win) return true;
		}
		
		
		// Vertical
		for(int i=0; i<size; i++) {
			boolean win = true;
			for(int j=0; j<size; j++) {
				if(board.getsymbol(j, i) != symbol) {
					win = false;
					break;
				}
			}
			if(win) return true;
		}
		
		
		// Main diagonal
		boolean win = true;
		for (int i = 0; i < size; i++) {
		    if (board.getsymbol(i, i) != symbol) {
		        win = false;
		        break;
		    }
		}
		if (win) return true;
		
		
		// Other diagonal
		win = true;
		for (int i = 0; i < size; i++) {
		    if (board.getsymbol(i, size - 1 - i) != symbol) {
		        win = false;
		        break;
		    }
		}
		if (win) return true;
		
		
		return false;
	}

	@Override
	public boolean isDraw(Board board) {
		
		for(int row=0; row<board.getsize(); row++) {
			for(int col=0; col<board.getsize(); col++) {
				if(board.isCellEmpty(row, col)) {
					return false;
				}
			}
		}
		return true;
	}

}
