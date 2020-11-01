// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private double PerTracker[];
	private double AveragePer;
	private double PerStdDev;
	private int TotalTrials;
	
	// perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

    if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
    PerTracker = new double[trials];
    TotalTrials = trials;

    for (int i = 1; i <= trials; i++) {          
        Percolation CurrentPercolation = new Percolation(n);
        while (CurrentPercolation.percolates() == false) { 
            int row = StdRandom.uniform(n) + 1;
            int col = StdRandom.uniform(n) + 1;
            CurrentPercolation.open(row, col);                
        }
        double opensites = CurrentPercolation.numberOfOpenSites();
        PerTracker[i-1] = opensites / (n*n);
    }
    
    AveragePer = StdStats.mean(PerTracker);
    PerStdDev = StdStats.stddev(PerTracker);
}
    
    
    // sample mean of percolation threshold
    public double mean() {
    	return AveragePer;
    }
    
    // sample standard deviation of percolation threshold
    public double stddev() {
    	return PerStdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return AveragePer - 1.96 * PerStdDev / Math.sqrt(TotalTrials);
    }

    public double confidenceHi() {
        return AveragePer + 1.96 * PerStdDev / Math.sqrt(TotalTrials);
    }
   
    // test client (see below)
   public static void main(String[] args) {
	   int GridSize = 0;
	   int NumTrials= 0;
       if (args.length >= 2) {
           GridSize = Integer.parseInt(args[0]);
           NumTrials = Integer.parseInt(args[1]);
       }
       // int GridSize = 100;
       // int NumTrials = 100;
       PercolationStats thisrun = new PercolationStats(GridSize, NumTrials);
       System.out.println("mean = "+ thisrun.mean());
       System.out.println("stddev = "+ thisrun.stddev());
       System.out.println("95% confidence interval = [" + thisrun.confidenceLo() + " , " + thisrun.confidenceHi() +"]");
//       System.out.println(thisrun.confidenceHi());
   
   }
	
}
