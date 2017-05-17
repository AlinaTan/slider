/*
 * Author: Su Ping Alina Tan (743564), Rio Kurnia Susanto (700360)
 * 
 * This is an implementation of a cell, containing information of where it is in the board and what is above it
 */

package aiproj.slider;

public class Cell {
	private int row;
	private int col;
	private Piece piece;
	/** isBlocked identifies if current cell is a Blocked cell (will be false if there is a piece on this cell) */
	private boolean isBlocked = false;
	
	/**
	 * constructor for Cell class
	 * @param row
	 * @param col
	 */
	public Cell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	/**
	 * Copy constructor for cell class
	 * @param cell to be copied
	 */
	public Cell(Cell cell) {
		this.row = cell.getRow();
		this.col = cell.getCol();
		this.piece = cell.getPiece();
		this.isBlocked = cell.isBlocked;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public boolean isBlocked() {
		return isBlocked;
	}
	
	public void setIsBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isHorizontal() {
		if(piece != null && piece.getPieceType() == Board.HORIZONTAL_PIECE) {
			return true;
		}
		return false;
	}

	public boolean isVertical() {
		if(piece != null && piece.getPieceType() == Board.VERTICAL_PIECE) {
			return true;
		}
		return false;
	}
	
	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

}