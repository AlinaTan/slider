package aiproj.slider;

public class Horizontal implements Piece {
	private int[][] possibleMoves = {UP, RIGHT, DOWN};
	private Cell cell;
	private String pieceType = Board.HORIZONTAL_PIECE;
	
	public Horizontal(Cell cell) {
		this.cell = cell;
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
}