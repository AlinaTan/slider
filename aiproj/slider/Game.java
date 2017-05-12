package aiproj.slider;

import java.io.IOException;
import java.util.Scanner;

public class Game {

	/**
	 * this is the driver class of the project, reading and storing the board input file
	 * then it will print out the total number of valid moves of the horizontal and vertical pieces
	 * @param args
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String args[]) throws NumberFormatException, IOException {
		String[][] boardArray = fileReader();
		Board board = new Board(boardArray);
		System.out.println(board.totalMoves(board.horizontals, board.cells));
		System.out.println(board.totalMoves(board.verticals, board.cells));
	}
	
	/**
	 * fileReader reads file from System.in and returns a 2D array of String,
	 * each element being the content of each cell of the board
	 * @return String[][] form of the file read
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static String[][] fileReader() throws NumberFormatException, IOException {
		/** creates Scanner to read the file  */
	    Scanner sc = new Scanner(System.in);
		
	    /** reads first line to get the board size */
	    int boardSize = Integer.parseInt(sc.nextLine());
	    /** row is an iterator used in storing the cells in the 2D String array */
	    int row = 0;
	    
	    /** boardArray is the 2D array that will store the contents of the board */
	    String[][] boardArray = new String[boardSize][boardSize];
	    /** inputStr is used to temporarily store each line read from the file */
	    String inputStr;
	    
	    try {
	    	/** reads input file line by line*/
	        while (sc.hasNextLine()) {
	        	inputStr = sc.nextLine();
	        	System.out.println(inputStr);
	            boardArray[row] = inputStr.split(" ");
	            row ++;
	            
	            /** when finished reading file, return the board array */
	            if(row == boardSize) {
	            	sc.close();
	            	return boardArray;
	            }
	        }
	    } 
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
}