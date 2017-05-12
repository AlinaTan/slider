package aiproj.slider;

public class Vertical implements Piece {
	private int[][] possibleMoves = {UP, RIGHT, LEFT};
	private Cell cell;
	private String pieceType = Board.VERTICAL_PIECE;
	
	public Vertical(Cell cell) {
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