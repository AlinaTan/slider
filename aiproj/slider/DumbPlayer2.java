package aiproj.slider;

import java.util.Scanner;

public class DumbPlayer2 implements SliderPlayer {

	char playerPiece;
	Board board; 
	int dimension;

	@Override
	public void init(int dimension, String board, char player) {
		/** boardArray is the 2D array that will store the contents of the board */
	    String[][] boardArray = new String[dimension][dimension];
	    /** inputStr is used to temporarily store each line read from the file */
	    String inputStr;
	    int row = dimension - 1;
		
		Scanner sc = new Scanner(board).useDelimiter("\n");
		while(sc.hasNext()) {
			inputStr = sc.next();
			boardArray[row] = inputStr.split(" ");
            row --;
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
			int rowToMove = 0, colToMove = 0;
			
			/* Update movement */
			switch(move.d){
			case UP:
				rowToMove = 1;
				break;
			case DOWN:
				rowToMove = -1;
				break;
			case LEFT:
				colToMove = -1;
				break;
			case RIGHT:
				colToMove = 1;
				break;			
			}
			
			/* Set original cell to empty and new cell to have piece */
			board.cells[move.j][move.i].setPiece(null);
			board.cells[move.j + rowToMove][move.i + colToMove].setPiece(piece);
			/* Update piece's cell location */
			piece.setCell(board.cells[move.j + rowToMove][move.i + colToMove]);
		}
	}

	@Override
	public Move move() {
		java.util.Random rng = new java.util.Random();
		int i = 0;
		
		if(playerPiece == 'H'){
			int r = rng.nextInt(board.horizontals.size());
			
			//Piece selectedPiece = board.horizontals.get(r);
			
			/*while (board.validMoves(selectedPiece, board.cells).size() <= 0){
				selectedPiece = board.horizontals.get(i);
				i++;
			}*/
			
			if(board.horizontals.size() > 0) {
				Piece selectedPiece = null;
				for(Piece h : board.horizontals) {
					selectedPiece = h;
				}
				
				if(board.validMoves(selectedPiece, board.cells).size() != 0) {
					Integer[] selectedMove = board.validMoves(selectedPiece, board.cells).get(0);
					System.out.println("Number of valid moves: " + board.validMoves(selectedPiece, board.cells).size());
					System.out.println(selectedPiece.getCell().getCol() + " ," + selectedPiece.getCell().getRow() + " --> "+ selectedPiece.translateMove(selectedMove));
					Move move = new Move(selectedPiece.getCell().getCol(), selectedPiece.getCell().getRow(), selectedPiece.translateMove(selectedMove));
					return move;
				}
			}
			
			return null;
		}
		
		else {
			int r = rng.nextInt(board.verticals.size());
			Piece selectedPiece = board.verticals.get(r);
			Integer[] selectedMove = board.validMoves(selectedPiece, board.cells).get(0);
			System.out.println("Number of valid moves: " + board.validMoves(selectedPiece, board.cells).size());
			System.out.println(selectedPiece.getCell().getCol() + " ," + selectedPiece.getCell().getRow() + " --> (" + selectedMove[0] + "," + selectedMove[1] + "),  "+ selectedPiece.translateMove(selectedMove));
			Move move = new Move(selectedPiece.getCell().getCol(), selectedPiece.getCell().getRow(), selectedPiece.translateMove(selectedMove));
			return move;
		}
		
	}

}
