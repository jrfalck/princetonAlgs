// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// all methods should take constant time plus a constant number of calls to the union-find methods union(), find(), connected(), and count().
public class Percolation {
	private final WeightedQuickUnionUF Dgrid;
	private final WeightedQuickUnionUF Dgrid2;
	private Boolean[][] trackingtable;
	private int GridDimensions;
	private int OpenSitesCounter;
	private int IndexTopSite;
	private int IndexBottomSite;
	
	 // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
    	GridDimensions = n;
    	OpenSitesCounter = 0;
    	int nrows = n;
    	int ncols = n;
    	if (n <= 0) throw new IllegalArgumentException("Wrong dimesions error");
    	Dgrid = new WeightedQuickUnionUF(n * n + 2); // One site was added for virtual TOP and another for Bottom
    	Dgrid2 = new WeightedQuickUnionUF(n * n + 1); // to deal with back wash
    	// 0 would be site 1, n-1 would be last site, n TOP, n-+1 BOTTOM
    	IndexTopSite = n*n;
    	IndexBottomSite = n*n+1;
    	trackingtable = new Boolean[nrows][nrows];
    	
    	for (int row = 0; row < nrows; row++) {
    		for (int col = 0; col < ncols; col++) {
    			trackingtable[row][col] = false;
    		}
    	}
    	
    }
    
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
    	// Check row and cols are valid
    	if ((row <= 0 || col <= 0) || (row > GridDimensions || col > GridDimensions))  throw new IllegalArgumentException("Wrong Dim error");
    
    	// Check if ISOPEN already
    	// If it's not open already then open it
    	if (!isOpen(row, col)) {
    		// Convert row and col to flat index
    		int siteindex = convertrowcol(row , col);
    		OpenSitesCounter = OpenSitesCounter+1;
    		trackingtable[row-1][col-1] = true;
    	
    		// If site in top row union with TOP site
    		// If site in bottom row union with BOTTOM site
    		if (row == 1) {
    			Dgrid.union(siteindex, IndexTopSite);
    			Dgrid2.union(siteindex, IndexTopSite);

    		}
    		if (row == GridDimensions) {
    			Dgrid.union(siteindex, IndexBottomSite);
    		}
    	
    		// Check if site above open and valid, if so UNION
    		if (row != 1) {
    			if (isOpen(row-1, col)) {
    				Dgrid.union(convertrowcol(row , col),convertrowcol(row-1 , col));
    				Dgrid2.union(convertrowcol(row , col),convertrowcol(row-1 , col));
    			}
    		}
    		
    		// Check if site below open and valid, if so UNION
    		if (row != GridDimensions) {
    			if (isOpen(row+1 , col)) {
    				Dgrid.union(convertrowcol(row , col),convertrowcol(row+1 , col));
    				Dgrid2.union(convertrowcol(row , col),convertrowcol(row+1 , col));
    			}
    		}
    		
    		// Check if site left open and valid, if so UNION
    		if (col != 1) {
    			if (isOpen(row , col-1)) {
    				Dgrid.union(convertrowcol(row , col),convertrowcol(row , col-1));
    				Dgrid2.union(convertrowcol(row , col),convertrowcol(row , col-1));
    			}
    		}
    		
    		// Check if site right open an valid, if so UNION
    		if (col != GridDimensions) {
    			if (isOpen(row , col+1)) {
    				Dgrid.union(convertrowcol(row , col),convertrowcol(row , col+1));
    				Dgrid2.union(convertrowcol(row , col),convertrowcol(row , col+1));
    			}
    		}
    	}
    }
    
    // is the site (row, col) open?
    public boolean isOpen(int row , int col) {
      	if ((row <= 0 || col <= 0) || (row > GridDimensions || col > GridDimensions))  throw new IllegalArgumentException("Wrong Dim error");
        
    	return trackingtable[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
      	if ((row <= 0 || col <= 0) || (row > GridDimensions || col > GridDimensions))  throw new IllegalArgumentException("Wrong Dim error");
        
    	// Do two FINDS to the site to see if connect to site TOP
    return (Dgrid2.find(IndexTopSite) == Dgrid2.find(convertrowcol(row, col)));
    }
    
    // returns the number of open sites
    public int numberOfOpenSites() {
    // Implement a variable to keep track of open site and just return number
    return (OpenSitesCounter);
    }
    
    // does the system percolate?
    public boolean percolates() {
    // if Site in Bottom in same set a site in Top they percolates use two FINDS
    	int parenttop;
    	int parentbottom;
    	parenttop = Dgrid.find(IndexTopSite);
    	parentbottom = Dgrid.find(IndexBottomSite);
    	if (parenttop == parentbottom) {
    		return true;
    	} else {
    		return false;
    	}
    	
    	// return (Dgrid.find(IndexTopSite)==Dgrid.find(IndexBottomSite));
    }

    private int convertrowcol(int row, int col) {
    	// We need to convert the rowxcol (1 to n by 1 to n) to a flat index 0 to n-1
    	return ((row - 1) * GridDimensions + col - 1);
    }
    
    // test client (optional)
    public static void main(String[] args) {
	
    	System.out.println("Percolates: ");
    }

}