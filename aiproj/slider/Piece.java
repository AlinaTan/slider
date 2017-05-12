package aiproj.slider;

public interface Piece {
	public static int[] UP = {-1, 0}, RIGHT = {0, 1}, DOWN = {1, 0}, LEFT = {0, -1};
	public int[][] possibleMoves = null;
	
	public abstract int[][] getPossibleMoves();
	public abstract String getPieceType();
	public abstract Cell getCell();
	public abstract boolean winningMove(int boardSize, int row, int col);
}