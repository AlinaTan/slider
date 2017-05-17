/*
 * Author: Su Ping Alina Tan (743564), Rio Kurnia Susanto (700360)
 * 
 * This class represents the horizontal piece, implementing the Piece class
 * It contains information and methods specific to a horizontal player
 */

package aiproj.slider;

public class Horizontal implements Piece {
	private int[][] possibleMoves = {RIGHT, DOWN, UP};
	private Cell cell;
	private String pieceType = Board.HORIZONTAL_PIECE;
	
	/**
	 * Constructor for Horizontal class
	 * @param cell the Horizontal piece is on
	 */
	public Horizontal(Cell cell) {
		this.cell = cell;
	}
	
	/**
	 * Copy constructor for Horizontal class
	 * @param horizontal piece to be copied
	 */
	public Horizontal(Horizontal horizontal) {
		this.cell = new Cell(horizontal.getCell());
	}
	
	public int[][] getPossibleMoves() {
		return possibleMoves;
	}
	
	public String getPieceType() {
		return pieceType;
	}
	
	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	public boolean winningMove(int boardSize, int row, int col) {
		if(col == boardSize) {
			return true;
		}
		return false;
	}
	
	public Move.Direction translateMove(Integer[] move){
		if (move[0] == 1 && move[1] == 0){
			return Move.Direction.UP;			
		}
		else if (move[0] == 0 && move[1] == 1){
			return Move.Direction.RIGHT;			
		}
		else if (move[0] == -1 && move[1] == 0){
			return Move.Direction.DOWN;			
		}
		else {
			return Move.Direction.LEFT;			
		}
	}
	
	public int[] translatePieceMove(Move move) {
		if (move.d == Move.Direction.UP){
			return Piece.UP;			
		}
		else if (move.d == Move.Direction.DOWN){
			return Piece.DOWN;			
		}
		else if (move.d == Move.Direction.RIGHT){
			return Piece.RIGHT;	
		}
		else {
			return Piece.LEFT;	
		}
	}
	
	public int distanceToGoal(int boardSize, int pieceRow, int pieceCol) {
		return boardSize - pieceCol - 1;
	}
	
	public int pathBlockedDistance(Board board, int pieceRow, int pieceCol) {
		for(int col = pieceCol; col < board.cells.length; col ++) {
			if(board.cells[pieceRow][col].isVertical()  || board.cells[pieceRow][col].isBlocked()) {
				return (col - pieceCol);
			}
		}
		return -1;
	}
}