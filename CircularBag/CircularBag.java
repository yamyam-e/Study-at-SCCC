/**
 * A Doubly Linked Circular Bag Implementation
 * <p>
 * This class implements the Bag interface with a doubly linked circular chain.
 * The last element in the chain points to the first element of the chain and the first element of the chain points to the last element of the chain.
 * All Nodes have two pointers, one to the next element of the chain and one to the previous element of the chain.
 * <p>
 *  Member variables:
 * <p>
 * <tt>head: Node</tt> - reference to first element of the chain
 * <p>
 * <tt>count: int</tt> - number of element in the </tt>Bag</tt>
 * <p>
 * @author Seongkwon Lee
 * @version 2014.03.09
 * @param <T>
 */
public class CircularBag<T> implements BagInterface<T> {
	
	private Node head;
	private int count;
	
	public CircularBag() {
		this.head = null;
		this.count = 0;
	}
	
	/** Gets the current number of entries in this bag.
	 @return the integer number of entries currently in the bag */
	public int getCurrentSize() {
		return this.count;
	}

	/**Sees whether this bag is full.
	 @return true if the bag is full, or false if not*/
	public boolean isFull() {
		boolean result = false;//since the circularbag can have unlimited element.
		return result;
	}

	/** Sees whether this bag is empty.
	 @return true if the bag is empty, or false if not */
	public boolean isEmpty() {
		return this.count == 0;
	}

	/** Adds a new entry to this bag.
    @param newEntry  the object to be added as a new entry
    @return true if the addition is successful, or false if not */
	public boolean add(T newEntry) {
		boolean result = false;
		if (newEntry != null) {
			Node newNode = new Node(newEntry);
			if (this.head == null) {
				newNode.next = newNode;
				newNode.prev = newNode;
				this.head = newNode;
			}
			else {
				Node temp = this.head;

				while(temp.next != this.head) {
					temp = temp.next;
				}
				
				newNode.next = temp.next;
				temp.next = newNode;
				newNode.prev = temp;
				newNode.next.prev = newNode;
			}
			this.count++;
			result = true;
		}
		return result;
	}

	/** Removes one unspecified entry from this bag, if possible.
  	@return either the removed entry, if the removal
          was successful, or null */
	public T remove() {
		T result = null;
		if (this.head != null) {
		    this.count--;
		}
		return result;
	}
	

	/** Locates a given entry within this bag.
	 *	Returns a reference to the node containing the entry, if located, // or null otherwise.
	 * @param anEntry
	 * @return a node if anEntry is in the bag */
	private Node getReferenceTo(T anEntry)
	{
		boolean found = false;
		Node currentHeadNode = this.head;
		for(int i = 0; i<this.count && !found && currentHeadNode != null;++i) {
			if (anEntry.equals(currentHeadNode.data)) found = true;
			else currentHeadNode = currentHeadNode.next;
		}
		return currentHeadNode;
	}

	/** Removes one occurrence of a given entry from this bag.
    @param anEntry  the entry to be removed
    @return true if the removal was successful, or false if not */
    public boolean remove(T anEntry) {
		boolean result = false;
		Node nodeN = getReferenceTo(anEntry);
		
		if (nodeN != null) {
			nodeN.prev.next = nodeN.next;
			nodeN.next.prev = nodeN.prev;
			
			if (nodeN == this.head) this.head = head.next;
			
			remove();
			
			if (isEmpty()) this.head = null;
			
			result = true;
		}
		return result;
	}

    /** Removes all entries from this bag. */
    public void clear() {
    	while (!isEmpty()) remove();
    	this.head = null;
    }

    /** Counts the number of times a given entry appears in this bag.
	 @param anEntry  the entry to be counted
	 @return the number of times anEntry appears in the bag */
    public int getFrequencyOf(T anEntry) {
    	int frequency = 0;
    	Node currentHeadNode = this.head;
    	
    	for(int i = 0; i<this.count && currentHeadNode != null; ++i) {
			if (anEntry.equals(currentHeadNode.data)) frequency++;
			currentHeadNode = currentHeadNode.next;
		}
    	
    	return frequency;
	}

    /** Tests whether this bag contains a given entry.
	 @param anEntry  the entry to locate
	 @return true if this bag contains anEntry, or false otherwise */
    public boolean contains(T anEntry) {
		boolean found = false;
		Node currentHeadNode = this.head;
		
		for(int i = 0; i<this.count && !found && currentHeadNode != null; ++i) {
			if (anEntry.equals(currentHeadNode.data)) found = true;
			else currentHeadNode = currentHeadNode.next;
		}
		
		return found;
	}

    /** Retrieves all entries that are in this bag.
     * @param <T>
	 @return a newly allocated array of all the entries in the bag */
    public T[] toArray() {
    	@SuppressWarnings("unchecked")
    	T[] result = (T[])new Object[this.count];
    	
        Node currentHeadNode = this.head;
        
        for(int i = 0; i<this.count && currentHeadNode != null; ++i) {
           result[i] = currentHeadNode.data;
           currentHeadNode = currentHeadNode.next;
        }

		return result;
	}
    
    private class Node {
    	private T data;
    	private Node prev;
    	private Node next;
    	
    	private Node(T dataPortion) {
    		this(dataPortion, null, null);
    	}
    	
    	private Node(T dataPortion, Node prev, Node next) {
    		this.data = dataPortion;
    		this.prev = prev;
    		this.next = next;
    	}
    }
}
