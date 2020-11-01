import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
   public static void main(String[] args) {

	   RandomizedQueue<String> queue = new RandomizedQueue<String>();
       
	   while (!StdIn.isEmpty()) {
           queue.enqueue(StdIn.readString());
       }
       
       int arguments = Integer.parseInt(args[0]);
    
       for (int i = 0; i < arguments; i++) {
           StdOut.println(queue.dequeue());
       }
   }
}