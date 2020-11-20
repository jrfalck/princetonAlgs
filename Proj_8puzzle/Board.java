// import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

	private final int [][] dTile;
	private int dimension;
	private int emptySpacerow;
	private int emptySpacecol;
	private boolean cachedexists;
	private Board cachedboard;
		
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

    	dTile = new int[tiles.length][tiles.length];
    	dimension = tiles.length;
    	
    	cachedexists = false;
     	
    	// ** loop copy implementation
    	// I could use either tiles ? or make a copy int[][] dTile = copyBoard()
    	 for (int row = 0; row < dimension; ++row) {
    		for (int col = 0; col < dimension; ++col) {
    			dTile[row][col] = tiles[row][col];
    			if (tiles[row][col] == 0) {
    				emptySpacerow = row;
    				emptySpacecol = col;
    			}
    		}
    	}
    	
    }
    
    // string representation of this board
    public String toString() {
//    	String newLine = System.getProperty("line.separator");
    	String newLine = "\n";
    	String dBoard = Integer.toString(dimension)+newLine;
    	
    	for (int row = 0; row < dimension; row++) {
    		for (int col = 0; col < dimension; col++) {
    			dBoard = dBoard+Integer.toString(dTile[row][col])+ " ";
    		}
    		dBoard = dBoard + newLine;
    	}
    	return dBoard;
    }

    // board dimension n
    public int dimension() {
    	return dimension;
    }

    // number of tiles out of place
    public int hamming() {
    	int correctTile = 0;
    	int hamming = 0;
    	int tile2Check =1;
    	
    	for (int row = 0; row < dimension; row++) {
    		for (int col = 0; col < dimension; col++) {
    			if (tile2Check == dimension * dimension) {
    				correctTile = 0;
    			} else {
    				correctTile = row * dimension + col + 1; 
    			}
    			if ((correctTile != dTile[row][col]) && dTile[row][col] != 0) {
    				hamming++;
    			}
    			tile2Check++;
    		}
    	}
    	return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
    	int manhattan = 0;
    	int rowitshouldbe = 0;
    	int colitshouldbe = 0;
    	int tile2Check =1;
    	
    	for (int row = 0; row < dimension; row++) {
    		for (int col = 0; col < dimension; col++) {
    			tile2Check = dTile[row][col];
    			if (tile2Check != 0) {
    				rowitshouldbe = tile2Check / dimension;
    				if (tile2Check % dimension == 0) {
    					rowitshouldbe--;
    				}
    				colitshouldbe = tile2Check -1 - (rowitshouldbe * dimension);
    				manhattan = manhattan + manhattanone(row,col,rowitshouldbe, colitshouldbe);
    			}
    		}
    	}
    	return manhattan;
    }

    private int manhattanone(int x0, int y0, int x1, int y1) {
    	int distance = Math.abs(x1-x0) + Math.abs(y1-y0);
    	return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
    	return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
    	if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass() != this.getClass())
            return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        for (int row = 0; row < that.dimension(); row++) {
        	for (int col = 0; col < that.dimension(); col++) {
                if (this.dTile[row][col] != that.dTile[row][col])
                    return false;
        	}
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
 
    	ArrayList<Board> listofNeighbors = new ArrayList<Board>();	
    	
    	// Check if there is neighbor below
    	if (isTilevalid(emptySpacerow+1,emptySpacecol) ) {
    	   	int[][] neighbor = copyBoard();
    	   	swaptiles(neighbor, emptySpacerow, emptySpacecol, emptySpacerow+1,emptySpacecol);
    	   	listofNeighbors.add(new Board(neighbor));
    	}
    	
    	// Check if there is neighbor on top
    	if (isTilevalid(emptySpacerow-1,emptySpacecol) ) {
    	   	int[][] neighbor = copyBoard();
    	   	swaptiles(neighbor, emptySpacerow, emptySpacecol, emptySpacerow-1,emptySpacecol);
    	   	listofNeighbors.add(new Board(neighbor));
    	}
    	
    	// Check if there is neighbor on the right
    	if (isTilevalid(emptySpacerow,emptySpacecol+1) ) {
    	   	int[][] neighbor = copyBoard();
    	   	swaptiles(neighbor, emptySpacerow, emptySpacecol, emptySpacerow,emptySpacecol+1);
    	   	listofNeighbors.add(new Board(neighbor));
    	}

    	// Check if there is neighbor on the left
    	if (isTilevalid(emptySpacerow,emptySpacecol-1) ) {
    	   	int[][] neighbor = copyBoard();
    	   	swaptiles(neighbor, emptySpacerow, emptySpacecol, emptySpacerow,emptySpacecol-1);
    	   	listofNeighbors.add(new Board(neighbor));
    	}

    	return listofNeighbors;
     }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
    	
     	int[][] twin = copyBoard();
//    	int[][] twin = dTile.clone();
        
        if (twin[0][0] == 0) {
             swaptiles(twin, 1, 0, 1, 1);
             return new Board(twin);
        }
        if (twin[0][1] == 0) {
        	swaptiles(twin, 0, 0, 1, 0);
            return new Board(twin);
        } 
        if (twin[1][0] == 0) {
        	swaptiles(twin, 0, 0, 0, 1);
            return new Board(twin);
        } 

        swaptiles(twin, 0, 0, 1, 0);
        return new Board(twin);
    }
   
    private void swaptiles(int[][] board,int Arow, int Acol, int Brow, int Bcol) {
    	int tileA = board[Arow][Acol];
    	int tileB = board[Brow][Bcol];
    	board[Arow][Acol] = tileB;
    	board[Brow][Bcol] = tileA;
    }

    private int[][] swaptiles2(int Arow, int Acol, int Brow, int Bcol) {
    	int[][] copy = copyBoard();
    	int tileA = copy[Arow][Acol];
    	int tileB = copy[Brow][Bcol];
    	copy[Arow][Acol] = tileB;
    	copy[Brow][Bcol] = tileA;
    	return copy;
    }
  
    private boolean isTilevalid(int row, int col) {
        if (row < 0 || row >= dimension || col < 0 || col >= dimension) {
            return false;
        }
        return true;
    }
    
    private int[][] copyBoard() {
        int[][] newBoard = new int[dimension][dimension];
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                newBoard[row][col] = dTile[row][col];
            }
        }
        return newBoard;
    }
    
    // unit testing (not graded)
    public static void main(String[] args) {
    	int[][] t = new int[2][2];
        int k = 1;
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t.length; j++) {
                t[i][j] = k++;
            }
        }

        t[0][0] = 2;
        t[0][1] = 0;
        t[1][0] = 1;
        t[1][1] = 3;
        Board b = new Board(t);
        System.out.println(b);

        for (Board u : b.neighbors()) {
            System.out.println(u);
        }
        
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b);
        System.out.println(b.twin());
        System.out.println(b.twin());
        System.out.println(b.twin());
        System.out.println(b.twin());

    }
 

}