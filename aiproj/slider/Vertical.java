/*
 * Author: Su Ping Alina Tan (743564), Rio Kurnia Susanto (700360)
 * 
 * This class represents the vertical piece, implementing the Piece class
 * It contains information and methods specific to a vertical player
 */

package aiproj.slider;

public class Vertical implements Piece {
	private int[][] possibleMoves = {UP, LEFT, RIGHT};
	private Cell cell;
	private String pieceType = Board.VERTICAL_PIECE;
	
	/**
	 * Constructor for Vertical class
	 * @param cell
	 */
	public Vertical(Cell cell) {
		this.cell = cell;
	}
	
	/**
	 * Copy constructor of vertical class
	 * @param vertical piece to be copied
	 */
	public Vertical(Vertical vertical) {
		this.cell = new Cell(vertical.getCell());
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
		if(row == boardSize) {
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
		return boardSize - pieceRow - 1;
	}
	
	public int pathBlockedDistance(Board board, int pieceRow, int pieceCol) {
		for(int row = pieceRow; row < board.cells.length; row ++) {
			if(board.cells[row][pieceCol].isHorizontal() || board.cells[row][pieceCol].isBlocked()) {
				return (row - pieceRow);
			}
		}
		return -1;
	}
	
}