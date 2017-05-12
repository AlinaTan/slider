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
		

	}

	@Override
	public Move move() {
		return null;
	}

}
