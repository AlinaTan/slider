package aiproj.slider;

import java.util.Scanner;

public class DumbPlayer implements SliderPlayer {

	char playerPiece;
	Board board; 
	int dimension;

	@Override
	public void init(int dimension, String board, char player) {
		/** boardArray is the 2D array that will store the contents of the board */
	    String[][] boardArray = new String[dimension][dimension];
	    /** inputStr is used to temporarily store each line read from the file */
	    String inputStr;
	    int row = 0;
		
		Scanner sc = new Scanner(board).useDelimiter("\n");
		while(sc.hasNext()) {
			inputStr = sc.next();
			boardArray[row] = inputStr.split(" ");
            row ++;
		}
		sc.close();
		
		this.board = new Board(boardArray);
		this.playerPiece = player;
		this.dimension = dimension;
	}

	@Override
	public void update(Move move) {
		
		if(move != null){
			/* Piece being moved */
			Piece piece = board.cells[move.j][move.i].getPiece();
			
			/* Update movement */
			switch(move.d){
			case UP:
				board.cells[move.j-1][move.i].setPiece(piece);
				break;
			case DOWN:
				board.cells[move.j+1][move.i].setPiece(piece);
				break;
			case LEFT:
				board.cells[move.j][move.i-1].setPiece(piece);
				break;
			case RIGHT:
				board.cells[move.j][move.i+1].setPiece(piece);
				break;			
			}
			
			/* Set original block to empty */
			board.cells[move.i][move.j].setPiece(null);
		}
	}

	@Override
	public Move move() {
		return null;
	}

}
