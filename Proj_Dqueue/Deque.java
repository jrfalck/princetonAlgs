import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

	private class Node
		{	
			Item item;
			Node next;
			Node previous;
		}

	private Node front;
	private Node back;
	private int nodeCounter;
	
	// construct an empty deque
    public Deque() {
    	front = null;
    	back = front;
    	nodeCounter = 0;
    }

	
	// return an iterator over items in order from front to back
	public Iterator<Item> iterator() {
		return new ListIterator();
		}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = front;
		
		public boolean hasNext() {
			return current != null;
			}
		
		public Item next()
		{
			if (current == null) {
				throw new NoSuchElementException("Error - Empty");
			}
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

    
	
    // is the deque empty?
    public boolean isEmpty() {
    	return (front == null);
    }
    
    // return the number of items on the deque
    public int size() {
    	return nodeCounter;
    }

    // add the item to the front
    public void addFirst(Item item) {
    	if (item == null) throw new IllegalArgumentException("Error - null");

    	Node newNode = new Node();
    	newNode.item = item;
    	nodeCounter++;
    	
    	if (isEmpty()) {
    		back = newNode;
    		front = newNode;
    		newNode.previous = null;
    		newNode.next = null;
    	} else {
    		newNode.previous = null;
    		newNode.next = front;
    		front.previous = newNode;
    		front = newNode;
    	}
    }
    
    
    // add the item to the back
    public void addLast(Item item) {
    	if (item == null) throw new IllegalArgumentException("Error- null");

    	Node newNode = new Node();
    	newNode.item = item;
    	nodeCounter++;
    	
    	if (isEmpty()) {
    		back = newNode;
    		front = newNode;
    		newNode.previous = null;
    		newNode.next = null;
    	} else {
    		newNode.next = null;
    		newNode.previous = back;
    		back.next = newNode;
    		back = newNode;
    	}
    }

    
    // remove and return the item from the front
    public Item removeFirst() {
        Item itemtoReturn = null;
    	if (isEmpty()) {
        	throw new NoSuchElementException("Error - Empty");}
        else if (nodeCounter == 1) {
            itemtoReturn = front.item;
            front = null;
            back = null;
         } else {
             Node oldFront = front;
             itemtoReturn = oldFront.item;
             front = oldFront.next;
             front.previous = null;
             oldFront.next = null;
             oldFront.item = null;
         }
         nodeCounter--;
         return itemtoReturn;
    }

    
    // remove and return the item from the back
    public Item removeLast() {
        Item itemtoReturn = null;
    	if (isEmpty()) {
        	throw new NoSuchElementException("Error - Empty"); }
        else if (nodeCounter == 1) {
            itemtoReturn = front.item;
            front = null;
            back = null;
         } else {
             Node oldBack = back;
             itemtoReturn = oldBack.item;
             back = oldBack.previous;
             back.next = null;
             oldBack.next = null;
             oldBack.item = null;
         }
         nodeCounter--;
         return itemtoReturn;
    }

   
    
    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> queue = new Deque<String>();
        queue.addFirst("A");
        queue.addFirst("C");
        queue.addFirst("D");
        queue.addLast("E");
        queue.addLast("F");
        queue.addLast("H");
        queue.removeFirst();
        queue.removeLast();
        
        // has to be after items have added so iterator has elements in it
        Iterator<String> iter = queue.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println(queue.size());
    }
} 