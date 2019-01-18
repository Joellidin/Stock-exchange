import java.util.*;

/* A priority queue. The class invariant is the properties of the binary heap, namely that it is a complete tree and
   that every root node has higher priority than its children nodes. A tree is complete if and only if every level 
   except the bottom one is full, and the bottom level is filled from left to right.*/
public class PriorityQueue<E> {
	private ArrayList<E> heap = new ArrayList<E>(); // A binary heap containing objects of generic type.
	private Comparator<E> comparator; // A comparator object of generic type.
	private Map<E, Integer> map; // A map that will store elements of the heap as keys and their indices as values.

	// Instantiates the class with a given comparator.
	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
		this.map = new HashMap<>();
	}

	// Returns the size of the priority queue. O(1).
	public int size() {
		return heap.size();
	}

	// Adds an item, x, of type E to the priority queue. O(log(n)).
	public void add(E x)
	{
		heap.add(x);
		map.put(x, heap.size()-1);
		siftUp(heap.size()-1);
	}

	// Updates an element of the heap with a new value. O(log(n)).
	public void update(E oldValue, E newValue)
	{
		// Search for index of oldValue
		int foundIndex = search(oldValue);
		
		// Update
		heap.set(foundIndex, newValue);
		map.remove(oldValue);
		map.put(newValue, foundIndex);
		
		int result = comparator.compare(oldValue, newValue);
		if (result > 0) {
			siftUp(foundIndex);
		} else if (result < 0) {
			siftDown(foundIndex);
		}
	}
	
	// Returns index of an element in the heap. O(1).
	private int search(E value) {	
		if(!map.containsKey(value)) {
			throw new NoSuchElementException();
		} else {
			return map.get(value);
		}
	}

	// Returns the smallest item in the priority queue.
	// Throws NoSuchElementException if empty. O(1).
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	// Removes the smallest item in the priority queue.
	// Throws NoSuchElementException if empty. O(log(n)).
	public void deleteMinimum() {
		if (size() == 0)
			throw new NoSuchElementException();
		map.remove(heap.get(0));
		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		if (heap.size() > 0) siftDown(0);
	}

	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct. O(log(n)).
	private void siftUp(int index) {
		E value = heap.get(index);
		int parentIndex = parent(index);
		
		// Change nodes if parent is smaller than element at 'index'.
		while(comparator.compare(value,heap.get(parentIndex)) <	 0) {	
			E tmp = heap.get(parentIndex);
			heap.set(parentIndex, heap.get(index));
			map.put(heap.get(index), parentIndex);
			heap.set(index, tmp);
			map.put(tmp, index);			
			index = parentIndex;
			parentIndex = parent(index);
		}
	}

	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct. O(log(n)).
	private void siftDown(int index) {
		E value = heap.get(index);

		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);

			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E childValue = heap.get(left);

			// ...but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) > 0) {
					child = right;
					childValue = rightValue;
				}
			}

			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(value, childValue) > 0) {
				heap.set(index, childValue);
				map.put(childValue, index);
				index = child;
			} else break;
		}

		heap.set(index, value);
		map.put(value, index);
	}

	
	// Helper functions for calculating the children and parent of an index. O(1).
	private final int leftChild(int index) {
		return 2*index+1;
	}

	private final int rightChild(int index) {
		return 2*index+2;
	}

	private final int parent(int index) {
		return (index-1)/2;
	}

}
