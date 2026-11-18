package TicTacToe_LLD;

public class Board {
	
	private final int size = 3;
	private final Symbol[][] grid;
	
	// initialize the board
	public Board(){
		
		grid = new Symbol[size][size];
		
		for(int row=0; row<size; row++) {
			for(int col=0; col<size; col++) {
				grid[row][col] = Symbol.EMPTY;
			}
		}
	}
	
	public int getsize() {
		return size;
	}
	
	public Symbol getsymbol(int row, int col) {
		return grid[row][col];
	}
	
	//Methods
	public boolean isCellEmpty(int row, int col) {
		return grid[row][col] == Symbol.EMPTY;
	}

	public void markCell(Symbol symbol, int row, int col) {
		grid[row][col] = symbol;
	}
	
	public void display() {
		for(int row=0; row<size; row++) {
			
			for(int col=0; col<size; col++) {
				System.out.print(grid[row][col]);
				
				if(col < size) {
					System.out.print(" | ");
				}
			}
			
			System.out.println();
			
			if(row < size -1) {
				System.out.println("---+---+---");
			}
		}
	}
	
}
