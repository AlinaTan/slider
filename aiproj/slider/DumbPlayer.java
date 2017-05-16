package aiproj.slider;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class DumbPlayer implements SliderPlayer {

	char playerPiece, enemyPiece;
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
		this.enemyPiece = (player == 'H') ? 'V' : 'H';
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
		
		MoveScore bestMoveScore = minimax(new Board(board), 4, Integer.MIN_VALUE, Integer.MAX_VALUE, playerPiece);
		Move bestMove = bestMoveScore.move;
		
		if(bestMove != null) {
			System.out.println("Best move for piece " + playerPiece + ": " +
					"("+ bestMove.j + ", " + bestMove.i + ") --> " + bestMove.d +
					", score: " + bestMoveScore.score);
		}
		update(bestMove);
		
		return bestMove;
	}

	/**
	 * 
	 * @param boardState
	 * @param depth
	 * @param player
	 * @return
	 */
	public MoveScore minimax(Board boardState, int depth, int alpha, int beta, char player) {
		ArrayList<Piece> pieces;
		if(player == 'H') {
			pieces = boardState.horizontals;
		}
		else {
			pieces = boardState.verticals;
		}
		
		int bestScore = (player == playerPiece) ? Integer.MIN_VALUE : Integer.MAX_VALUE, currentScore;
		ArrayList<Move> validMoves = boardState.totalMoves(pieces, boardState.cells);
		Move bestMove = null;
		
		// checks if no more possible moves or depth has reach 0
		if (validMoves.isEmpty() || depth == 0) {
	         bestScore = evaluateBoard();
	    } 
		else {
			// iterate through all nodes/moves
			for (Move move : validMoves) {
				if (player == playerPiece) {
		               currentScore = minimax(boardState, depth - 1, alpha, beta, enemyPiece).score;
		               if (currentScore > bestScore) {
		                  bestScore = currentScore;
		                  bestMove = move;
		               }
		               alpha = Math.max(alpha, bestScore);
		               if (beta <= alpha){
							break;
						}
		            } 
				else {
		               currentScore = minimax(boardState, depth - 1, alpha, beta, playerPiece).score;
		               if (currentScore < bestScore) {
		                  bestScore = currentScore;
		                  bestMove = move;
		               }
		               beta = Math.min(beta, bestScore);
		               if (beta <= alpha){
							break;
						}
		            }
			}
		}
		
		/*if(bestMove == null) {
			System.out.println("NULL MOVE");
		}
		else {
			System.out.println("Best move for piece " + playerPiece + ": " +
					"("+ bestMove.j + ", " + bestMove.i + ") --> " + bestMove.d +
					", score: " + bestScore);
		}*/
		
		return new MoveScore(bestMove, bestScore);
	}
	
	/**
	 * 
	 * @param board
	 * @param move
	 * @param player
	 * @return
	 */
	public int evaluateBoard() {
		ArrayList<Piece> playerPieces, enemyPieces;
		Move.Direction goal;
		Piece enemyPieceType;
		int score = 0, horIsPlayer = 1, verIsPlayer = 1;
		
		// gets goal of piece and find the pieces of the player and enemy
		if(playerPiece == 'H') {
			playerPieces = board.horizontals;
			enemyPieces = board.verticals;
			goal = Move.Direction.RIGHT;
			verIsPlayer = -1;
		}
		else {
			playerPieces = board.verticals;
			enemyPieces = board.horizontals;
			goal = Move.Direction.UP;
			horIsPlayer = -1;
		}
		
		// counts the difference in pieces left on the board
		score += (enemyPieces.size() - playerPieces.size()) * 20;
		
		// enemy blocked heuristics
		for(Piece piece : enemyPieces) {
			int blockedDistance = piece.pathBlockedDistance(board, piece.getCell().getRow(), piece.getCell().getCol());
			
			// enemy piece's path to goal blocked +5
			if(blockedDistance > 0) {
				score += 5;
			}
			// enemy piece's path to goal adjacently blocked +7
			if(blockedDistance == 1) {
				score += 7;
			}
			// enemy piece that is one cell away from winning blocked +15
			if(blockedDistance == 1 && 
					piece.distanceToGoal(board.cells.length, piece.getCell().getRow(), piece.getCell().getCol()) == 1) {
				score += 15;
			}
			
		}
		
		// utility function
		if(playerPieces.size() == 0) {
			score = Integer.MAX_VALUE;
		}
		else if(enemyPieces.size() == 0) {
			score = Integer.MIN_VALUE;
		}
		
		return score;
	}
	
	public class MoveScore {
		Move move;
		int score;
		MoveScore(Move move, int score) {
			this.move = move;
			this.score = score;
		}
	}
	
}
