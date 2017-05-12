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
	
	public boolean winningMove(int boardSize, int row, int col) {
		if(row < 0) {
			return true;
		}
		return false;
	}
	
	public Move.Direction translateMove(Integer[] move){
		
		if (move[0] == -1 && move[1] == 0){
			return Move.Direction.UP;			
		}
		else if (move[0] == 0 && move[1] == 1){
			return Move.Direction.RIGHT;			
		}
		else if (move[0] == 1 && move[1] == 0){
			return Move.Direction.DOWN;			
		}
		else {
			return Move.Direction.LEFT;			
		}
	}
}