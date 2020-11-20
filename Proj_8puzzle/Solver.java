import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Arrays;
// import java.util.Stack;
import java.util.LinkedList;


public class Solver {
		
	private boolean solved = false;
	private int stepsCounter = 0;
	private LinkedList<Board> stack = new LinkedList<Board>(); 
	private MinPQ<SearchNode> pq;
	private MinPQ<SearchNode> pqTwin;
	
	 private class SearchNode implements Comparable<SearchNode> {
	        private Board dBoard;
	        private int moves;
	        private SearchNode prev;
	        private int heuristic;

	        // Constructor
	        public SearchNode(Board dBoard, int moves, SearchNode prev) {
	            this.dBoard = dBoard;
	            this.moves = moves;
	            this.prev = prev;
	            this.heuristic = dBoard.manhattan();
	        }

	        // Compare SearchNodes by their priorities
	        @Override
	        public int compareTo(SearchNode o) {
	        	 return this.priority() - o.priority();
	        }
	        
	        public int priority() {
	        	return this.heuristic + this.moves;
	        }
	 }
	 
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	 
    	if(initial == null) throw new  IllegalArgumentException();
    	 
    	// Create empty PQ
    	pq = new MinPQ<SearchNode>();
    	pqTwin = new MinPQ<SearchNode>();
    	SearchNode curr = new SearchNode(initial,0, null);
    	SearchNode twin = new SearchNode(initial.twin(), 0 , null);  
    	pq.insert(curr);  
    	pqTwin.insert(twin);
    
    	while (!curr.dBoard.isGoal() && !twin.dBoard.isGoal()) {
    	
	    	// Delete minimum
	    	curr = pq.delMin();
	    	twin = pqTwin.delMin();
	    	
	    	//loop neighbors and add them to PQ

	        for (Board nb : curr.dBoard.neighbors()) {  
	            if (curr.prev == null || !nb.equals(curr.prev.dBoard))  
	            { pq.insert(new SearchNode(nb, curr.moves+1, curr));}  
	        }  
	     
	        for (Board nb : twin.dBoard.neighbors()) {  
	            if (twin.prev == null || !nb.equals(twin.prev.dBoard))  
	                pqTwin.insert(new SearchNode(nb, twin.moves+1, twin));  
	        }

	    }  
	    
    	if (curr.dBoard.isGoal()) {  
	        createRoute(curr);
	        stepsCounter = curr.moves;
    		pq =null;
    		pqTwin = null;
	        solved = true;  
	    }
	    	
    
    	
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
    	return solved;
    	
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	if (!solved) {
    		return -1;
    	}
    	return stepsCounter;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	if (!solved) {
    		return null;
    	}
    	 return  stack;
    }

// create Arraylist or Stack from last Search node
    private void createRoute(SearchNode lastNode) {

    	SearchNode curr;
    	curr = lastNode;
    	

    	while (curr != null)
    	{
    		stack.addFirst(curr.dBoard);
    		curr = curr.prev;
    	}
    	
    }
    
    // test client (see below) 
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    	
}