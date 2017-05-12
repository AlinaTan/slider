package aiproj.slider;

import java.util.ArrayList;
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
			//Piece piece = board.getPiece(board.cells[move.j][move.i]);
			System.out.println("UPDATE() for player " + playerPiece);
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
			
			int newRow = move.j + rowToMove, newCol = move.i + colToMove;
			/** first checks if piece would move outside of board and is a winning move*/
			if(piece.winningMove(dimension, newRow, newCol) == true) {
				board.cells[move.j][move.i].setPiece(null);
				board.removePiece(piece);
				System.out.println("Piece deleted\n");
			}
			else {
				/* Set original cell to empty and new cell to have piece */
				board.cells[move.j][move.i].setPiece(null);
				board.cells[newRow][newCol].setPiece(piece);
				/* Update piece's cell location */
				piece.setCell(board.cells[move.j + rowToMove][move.i + colToMove]);
				System.out.println("Piece cell updated into " + piece.getCell().getCol() + ", " + piece.getCell().getRow() + "\n");
			}
		}
	}

	@Override
	public Move move() {
		java.util.Random rng = new java.util.Random();
		int i = 0;
		ArrayList<Piece> pieces;
		
		if(playerPiece == 'H') {
			pieces = board.horizontals;
		}
		else {
			pieces = board.verticals;
		}
		
		int r = rng.nextInt(board.horizontals.size());
			
		if(pieces.size() > 0) {
			Piece selectedPiece = null;
			for(Piece piece : pieces) {
				if(board.validMoves(piece, board.cells).size() > 0) {
					selectedPiece = piece;
					break;
				}
			}
			
			Integer[] selectedMove = board.validMoves(selectedPiece, board.cells).get(0);
			System.out.println("Number of valid moves: " + board.validMoves(selectedPiece, board.cells).size());
			System.out.println(selectedPiece.getCell().getCol() + " ," + selectedPiece.getCell().getRow() + " --> " + selectedPiece.translateMove(selectedMove) + "\n");
			Move move = new Move(selectedPiece.getCell().getCol(), selectedPiece.getCell().getRow(), selectedPiece.translateMove(selectedMove));
			update(move);
			return move;
		}
			
		return null;
	}

}
