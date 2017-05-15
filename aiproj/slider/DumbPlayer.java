package aiproj.slider;

import java.util.ArrayList;
import java.util.ListIterator;
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
			//System.out.println("UPDATE() for player " + playerPiece);
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
				//System.out.println("Piece deleted\n");
			}
			else {
				/* Set original cell to empty and new cell to have piece */
				board.cells[move.j][move.i].setPiece(null);
				board.cells[newRow][newCol].setPiece(piece);
				/* Update piece's cell location */
				piece.setCell(board.cells[move.j + rowToMove][move.i + colToMove]);
				//System.out.println("Piece cell updated into " + piece.getCell().getCol() + ", " + piece.getCell().getRow() + "\n");
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
		
		Move bestMove = minimax(board, playerPiece);
		update(bestMove);
		
		return bestMove;
	}

	public Move minimax(Board board, char player) {
		ArrayList<Piece> pieces;
		if(playerPiece == 'H') {
			pieces = board.horizontals;
		}
		else {
			pieces = board.verticals;
		}
		
		ArrayList<Move> validMoves = board.totalMoves(pieces, board.cells);
		int bestScore = 0;
		Move bestMove = null;
		
		// iterate arraylist of validMoves in descending order
		for (ListIterator iterator = validMoves.listIterator(validMoves.size()); iterator.hasPrevious();) {
			Move move = (Move)iterator.previous();
			int currentScore = evaluateBoard(board, move, player);
			// only updates bestMove if 
			if(currentScore > bestScore || (bestScore == 0 && currentScore == 0)) {
				bestScore = currentScore;
				bestMove = move;
			}
		}
		// iterate arraylist of validMoves in descending order
		/*for(Move move : validMoves) {
			int currentScore = evaluateBoard(board, move, player);
			// only updates bestMove if 
			if(currentScore > bestScore || (bestScore == 0 && currentScore == 0)) {
				bestScore = currentScore;
				bestMove = move;
			}
		}*/
		
		if(bestMove == null) {
			System.out.println("NULL MOVE");
		}
		else {
			System.out.println("Best move for piece " + playerPiece + ": " +
					"("+ bestMove.j + ", " + bestMove.i + ") --> " + bestMove.d +
					", score: " + bestScore);
		}
		
		return bestMove;
	}
	
	public int evaluateBoard(Board board, Move move, char player) {
		ArrayList<Piece> pieces;
		Move.Direction goal;
		Piece pieceToBeMoved = board.cells[move.j][move.i].getPiece();
		int[] translatedMove = pieceToBeMoved.translatePieceMove(move);
		int score = 0, rowMovedTo = move.j + translatedMove[0],
				colMovedTo = move.i + translatedMove[1];
		
		// gets goal of piece and find the pieces of the player
		if(playerPiece == 'H') {
			pieces = board.horizontals;
			goal = Move.Direction.RIGHT;
		}
		else {
			pieces = board.verticals;
			goal = Move.Direction.UP;
		}
		
		// winning move +2
		if(pieceToBeMoved.winningMove(board.cells.length, rowMovedTo, colMovedTo)) {
			score += 2;
		}
		
		// moving towards goal +1
		if(move.d == goal) {
			score += 1;
		}
		
		// moving to a cell that's one move away from goal +1
		if(pieceToBeMoved.distanceToGoal(board.cells.length, translatedMove) == 0) {
			score += 1;
		}
		
		return score;
	}
	
}
