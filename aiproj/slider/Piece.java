package aiproj.slider;

import aiproj.slider.Move.Direction;

public interface Piece {
	public static int[] UP = {1, 0}, RIGHT = {0, 1}, DOWN = {-1, 0}, LEFT = {0, -1};
	public int[][] possibleMoves = null;
	
	public abstract int[][] getPossibleMoves();
	public abstract String getPieceType();
	public abstract Cell getCell();
	public abstract void setCell(Cell cell);
	public abstract boolean winningMove(int boardSize, int row, int col);
	
	// int[] move -> Move.Direction
	public abstract Direction translateMove(Integer[] move);
	// Move class -> int[] move
	public abstract int[] translatePieceMove(Move move);
	public abstract int distanceToGoal(int boardSize, int move[]);
}