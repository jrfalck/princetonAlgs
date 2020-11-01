import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] dArray;
	private int size;

	// construct an empty randomized queue
    public RandomizedQueue() {
    	dArray = (Item[]) new Object[1];
    	size = 0;
    }

    
	// Have to create a random iterator class
	private class CustomArrayIterator implements Iterator<Item> {
		private int newListsize = size;
        private int[] randomOrder;
		
		public CustomArrayIterator() {
			randomOrder = new int[newListsize];
            for (int j = 0; j < newListsize; ++j) {
                randomOrder[j] = j;
            }
            StdRandom.shuffle(randomOrder);
		}
		
		public boolean hasNext() {
			return newListsize>0;
		}
		
		public Item next() {
			if (!hasNext()) throw new java.util.NoSuchElementException("Error - Empty");
            return dArray[randomOrder[--newListsize]]; 
		}
	 }
	
    
    // is the randomized queue empty?
    public boolean isEmpty() {
    	return size == 0;
    }
    	

    // return the number of items on the randomized queue
    public int size() {
    	return size;
    }

    
    // Creates new array twice the size and copies contents from old array
    private void resize(int newSize) {
    	Item[] temp = (Item[]) new Object[newSize];
    	for (int i = 0; i < size; i++)
    		temp[i] = dArray[i];
    	dArray = temp;
    }

    // add the item
    public void enqueue(Item item) {
    	if (item == null) throw new IllegalArgumentException("Error- null");
    	dArray[size++] = item;
    	if (size == dArray.length) {
    		resize(2 * dArray.length); }
    }

    
    // remove and return a random item
    // Randomly select a location and replace with last element in array
    public Item dequeue() {
    	if (isEmpty()) {
    		throw new NoSuchElementException("Error - Empty");
    	}
    	int idItemtoremove = StdRandom.uniform(0, size);
    	size--;
    	Item itemTodelete = dArray[idItemtoremove];
    	dArray[idItemtoremove] = dArray[size];
    	dArray[size] = null;
    	if (size == dArray.length / 4) {
    		resize(dArray.length/ 2 );
    	}
    	return itemTodelete;
    }

    
    // return a random item (but do not remove it)
    public Item sample() {
    	if (isEmpty()) {
    		throw new NoSuchElementException("Error - Empty");
    	}
    	return dArray[StdRandom.uniform(0, size)];
    }

    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    	return new CustomArrayIterator();
    }

    
    // unit testing (required)
    public static void main(String[] args) {
    	
    	RandomizedQueue<String> queue = new RandomizedQueue<String>();
    	
    	queue.enqueue("A");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.dequeue();
        
        // must be after list has values
        Iterator<String> myIterator = queue.iterator();
        // Now myIterartor has a iterable list of values
        
        while (myIterator.hasNext()) {
        	System.out.print(myIterator.next() + " ");
        }
        System.out.println();
    }

}