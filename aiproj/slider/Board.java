package aiproj.slider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import aiproj.slider.Move.Direction;

public class Board {
	/** CONSTANTS for what is in each cell of the input file */
	public static String HORIZONTAL = "H", VERTICAL = "V", BLOCKED = "B", EMPTY = "+";
	public static String HORIZONTAL_PIECE = "Horizontal", VERTICAL_PIECE = "Vertical";
	
	/** cells is the 2D array that will represent the board's content */
	public Cell[][] cells;
	
	/** horizontals and verticals are ArrayList of Piece class that will
	 *  be used to keep track of the pieces */
	public ArrayList<Piece> horizontals = new ArrayList<Piece> ();
	public ArrayList<Piece> verticals = new ArrayList<Piece> ();
	
	/**
	 * constructor for Board class, it initializes each cell of the board
	 * and stores them in a 2D array called cells
	 * @param boardArray is the String 2D array of the board read from the file
	 */
	public Board(String[][] boardArray) {
		int boardSize = boardArray.length;
		cells = new Cell[boardSize][boardSize];
		
		for(int row=0; row<boardSize; row++) {
			for(int col=0; col<boardSize; col++) {
				cells[row][col] = new Cell(row, col);
				initCellState(cells[row][col], boardArray[row][col]);
			}
		}
	}
	
	/** Copy constructor for board */
	public Board(Board board) {
		if(board == null) {
			System.out.println("Failed to copy board");
			System.exit(0);
		}
		this.cells = board.cells;
		this.horizontals = board.horizontals;
		this.verticals = board.verticals;
	}
	
	/** 
	 * initCellState initialises the cell depending on what is on top of 
	 * the cell, sets isBlocked to true if the cell is Blocked and
	 * initializes the Piece object of cell if there is a piece on the cell
	 * @param cell is the current cell being initialised
	 * @param state is the String of what's on the cell, taken from file input */
	public void initCellState(Cell cell, String state) {
		if(state.equals(BLOCKED)) {
			cell.setIsBlocked(true);
		}
		else if(state.equals(HORIZONTAL)) {
			Horizontal horizontal = new Horizontal(cell);
			horizontals.add(horizontal);
			cell.setPiece(horizontal);
		}
		else if(state.equals(VERTICAL)) {
			Vertical vertical = new Vertical(cell);
			verticals.add(vertical);
			cell.setPiece(vertical);
		}
	}
	
	/**
	 * updateCellState updates the cell with the piece
	 * @param cell is the current cell being updated
	 * @param piece is the piece going to be on the cell*/
	public void updateCellState(Cell cell, Piece piece, Move move) {
		if(piece == null) {
			cell.setPiece(null);
		}
		else if(piece.getPieceType().equals(HORIZONTAL)) {
			cell.setPiece(piece);
		}
		else if(piece.getPieceType().equals(VERTICAL)) {
			cell.setPiece(piece);
		}
	}
	
	/** 
	 * validMoves returns an ArrayList of valid moves the piece can make,
	 * with a move being in the form of an int array {rowNumberToAdd, colNumberToAdd}
	 * e.g. moving RIGHT is the array {0, 1}
	 * @param piece is the current Piece we want to know what the valid moves are
	 * @param cells is a 2D array of the board
	 * @return ArrayList of valid moves
	 */
	public ArrayList<Integer[]> validMoves(Piece piece, Cell[][] cells) {
		Cell currentCell = piece.getCell();
		int currentRow = currentCell.getRow(), currentCol = currentCell.getCol();
		ArrayList<Integer[]> validMoves = new ArrayList<Integer[]> ();

		
		/** move will be in an array form of {rowNumberToAdd, colNumberToAdd} */
		for(int moveIndex=0; moveIndex<piece.getPossibleMoves().length; moveIndex++) {
			int[] move = piece.getPossibleMoves()[moveIndex];
			int newRow = currentRow+move[0], newCol = currentCol+move[1];
			Integer[] newMove = {(Integer)move[0], (Integer)move[1]};
			
			/** first checks if piece would move outside of board and is a winning move*/
			if(piece.winningMove(cells.length, newRow, newCol) == true) {
				validMoves.add(newMove);
			}
			else {
				/** this try catch block is used to ignore adjacent arrays that would be 
				 *  out of the boardArray */
				try {
					Cell adjacentCell = cells[currentRow+move[0]][currentCol+move[1]];
					
					/** adds the Integer typecasted move (named newMove) into validMoves ArrayList
					 *  only if the adjacent cell with that move isn't blocked or contains any piece */
					if(!adjacentCell.isBlocked() && !adjacentCell.isHorizontal() && !adjacentCell.isVertical()) {
						validMoves.add(newMove);
					}
				}
				catch(Exception e) {}
			}
		}
		
		return validMoves;
	}
	
	/** 
	 * totalMoves takes an ArrayList of pieces (either all the horizontal pieces or the vertical pieces)
	 * and returns a list of the total valid moves of the piece type
	 * @param pieces an ArrayList of either Horizontal pieces or Vertical pieces
	 * @param cells is a 2D array of the board
	 * @return ArrayList of possible moves for player of piece type (H or V)
	 */
	public ArrayList<Move> totalMoves(ArrayList<Piece> pieces, Cell[][] cells) {
		ArrayList<Move> totalMoves = new ArrayList<Move> ();
		
		for(Piece piece : pieces) {
			ArrayList<Integer[]> validMoves = validMoves(piece, cells);
			for(Integer[] move : validMoves) {
				Cell pieceCell = piece.getCell();
				Move pieceMove = new Move(pieceCell.getCol(), pieceCell.getRow(), piece.translateMove(move));
				totalMoves.add(pieceMove);
			}
		}
		
		return totalMoves;
	}
	
	public void removePiece(Piece pieceToBeRemoved) {
		ArrayList<Piece> pieces = null;
		if(pieceToBeRemoved.getPieceType() == Board.HORIZONTAL_PIECE) {
			pieces = horizontals;
		}
		else if(pieceToBeRemoved.getPieceType() == Board.VERTICAL_PIECE) {
			pieces = verticals;
		}
		
		for (Iterator<Piece> iterator = pieces.iterator(); iterator.hasNext(); ) {
		    Piece piece = iterator.next();
		    if(pieces.indexOf(piece) == pieces.indexOf(pieceToBeRemoved)) {
				iterator.remove();
			}
		}
	}
	
}