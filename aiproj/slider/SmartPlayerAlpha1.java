package aiproj.slider;

import java.util.ArrayList;
import java.util.Scanner;

public class SmartPlayerAlpha1 implements SliderPlayer {
	
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
		
		ArrayList<Piece> pieces = board.verticals;
		
		if(playerPiece == 'H') {
			pieces = board.horizontals;
		}
		
		return null;
	}
	
	public ScoreMove alphabeta(Move move, int depth, int alpha, int beta, char player){
		
		
		/*DOUBLE CHECK (player == 'H') W RIO*/
		int bestScore = (player == 'H') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		Move bestMove = move;
		
		if (depth == 0 || board.horizontals.isEmpty() || board.verticals.isEmpty()){
			bestScore = evaluateBoard(board, move, player);
		}
		else{
			
			if (player == 'H'){
				/* Need to write a function to find the validmoves 
				 * of all the pieces of a certain player on the board */
				for (){
					/*The 'V' here is basically supposed to be the opposite of what we are */
					bestScore = Math.max(bestScore, alphabeta(currentMove, depth-1, alpha, beta, 'V').score);
					/* REMEMBER TO PUT IN THE BESTMOVE */
					alpha = Math.max(alpha, bestScore);
					/* DOUBLE CHECK THIS '>' AND '<' THIS */
					if (beta <= alpha){
						break;
					}
				}
			}
			else{
				/* Need to write a function to find the validmoves 
				 * of all the pieces of a certain player on the board */
				for (){
					bestScore = Math.min(bestScore, alphabeta(move, depth-1, alpha, beta, 'H').score);
					/* REMEMBER TO PUT IN THE BESTMOVE */
					beta = Math.min(beta, bestScore);
					/* DOUBLE CHECK THIS '>' AND '<' THIS */
					if (beta >= alpha){
						break;
					}
				}
			}

		}
		return new ScoreMove(bestMove, bestScore);
	}
	
	
	
	public int evaluateBoard(Board board, Move move, char player) {
		Piece pieceToBeMoved = board.cells[move.j][move.i].getPiece();
		int[] translatedMove = pieceToBeMoved.translatePieceMove(move);
		int score = 0, rowMovedTo = move.j + translatedMove[0],
				colMovedTo = move.i + translatedMove[1];
		ArrayList<Piece> pieces = board.verticals;
		Move.Direction goal = Move.Direction.RIGHT;
		
		
		// gets goal of piece and find the pieces of the player
		if(playerPiece == 'H') {
			pieces = board.horizontals;
			goal = Move.Direction.RIGHT;
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
		if(pieceToBeMoved.distanceToGoal(board.cells.length, translatedMove) == 1) {
			score += 1;
		}
		
		return score;
	}
	
	/* Helper Class to store the Move and the Score for Minimax */
	class ScoreMove{
		Move move;
		int score;
		ScoreMove(Move move, int score){
			this.move = move;
			this.score = score;
		}
	}

}
