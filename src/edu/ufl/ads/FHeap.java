package edu.ufl.ads;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


class FHeap {

	// heap size
	private int size = 0;

	public static final class Node {

		private Node next; // Next element
		private Node prev; // Previous element
		private Node parent; // Parent node
		private Node child; // Child node

		private int element; // Element being stored here
		private double cost; // Its priority or the cost in our case

		private int degree = 0; // Number of children
		private boolean isChecked = false;

		// constructor
		private Node(int id, double priority) {
			next = prev = this;
			element = id;
			cost = priority;
		}

		// get the value
		public int getValue() {
			return element;
		}

		// set the value
		public void setValue(int value) {
			element = value;
		}

		// get priority
		public double getCost() {
			return cost;
		}

	}

	// min element in heap
	private Node min = null;

	// min element of the heap
	public Node min() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");
		return min;
	}

	// check if heap is empty
	public boolean isEmpty() {
		return min == null;
	}

	// number of elements in the heap
	public int size() {
		return size;
	}

	// Inserts element with priority into the Fibonacci heap.
	public Node enqueue(int id, double priority) {
		if (Double.isNaN(priority))
			throw new IllegalArgumentException(priority + " is invalid.");
		Node result = new Node(id, priority);
		// Merge result list with the tree list.
		min = mergeLists(min, result);
		size = size + 1;
		// return the element
		return result;
	}

	// Dequeueing the Fibonacci heap.
	public Node dequeueMin() {
		if (isEmpty())
			throw new NoSuchElementException("Heap is empty.");

		size = size - 1;

		// store min element
		Node minElement = min;

		if (min.next == min) { // check if other elements exist
			min = null;
		} else {
			min.prev.next = min.next;
			min.next.prev = min.prev;
			min = min.next;
		}

		// Clear the parent fields of all of the min element's children to make
		// them roots
		if (minElement.child != null) {
			// store first visited child
			Node current = minElement.child;
			do {
				current.parent = null;

				current = current.next;
			} while (current != minElement.child);
		}

		// merge into topmost list, store one in variable
		min = mergeLists(min, minElement.child);

		if (min == null)
			return minElement;

		// arraylist to keep the degrees of the trees
		List<Node> treeTable = new ArrayList<Node>();

		// make sure if the node is visited once
		List<Node> toVisit = new ArrayList<Node>();

		// make sure to add all
		for (Node current = min; toVisit.isEmpty() || toVisit.get(0) != current; current = current.next)
			toVisit.add(current);

		// traversing
		for (Node current : toVisit) {
			while (true) {

				while (current.degree >= treeTable.size())
					treeTable.add(null);

				if (treeTable.get(current.degree) == null) {
					treeTable.set(current.degree, current);
					break;
				}

				// merging
				Node other = treeTable.get(current.degree);
				treeTable.set(current.degree, null); // Clear the slot

				// check for smaller roots
				Node min = (other.cost < current.cost) ? other : current;
				Node max = (other.cost < current.cost) ? current : other;

				// merge max into min's child list.

				max.next.prev = max.prev;
				max.prev.next = max.next;

				max.next = max.prev = max;
				min.child = mergeLists(min.child, max);

				max.parent = min;

				max.isChecked = false;

				// Increase min's degree
				min.degree = min.degree + 1;

				current = min;
			}

			// update min
			if (current.cost <= min.cost)
				min = current;
		}
		return minElement;
	}

	// merge the two heaps
	public static FHeap merge(FHeap one, FHeap two) {
		FHeap result = new FHeap();

		// get the min of the two list
		result.min = mergeLists(one.min, two.min);

		// The size of the new heap
		result.size = one.size + two.size;
		// reset old heap variables
		one.size = two.size = 0;
		one.min = null;
		two.min = null;
		return result;
	}

	// Merge the two lists together into one circularly-linked list

	private static Node mergeLists(Node one, Node two) {

		if (one == null && two == null) {
			return null;
		} else if (one != null && two == null) {
			return one;
		} else if (one == null && two != null) {
			return two;
		} else {

			Node oneNext = one.next;
			one.next = two.next;
			one.next.prev = one;
			two.next = oneNext;
			two.next.prev = two;

			// check for smaller one
			return one.cost < two.cost ? one : two;
		}
	}

	// Decreases the key of the specified element to the new cost and
	// check if new cost>old.
	public void decreaseKey(Node entry, double newPriority) {
		if (Double.isNaN(newPriority))
			throw new IllegalArgumentException(newPriority + " is invalid.");
		if (newPriority > entry.cost)
			throw new IllegalArgumentException("New priority exceeds old.");

		entry.cost = newPriority;
		// compare node's priority with parent's
		if (entry.parent != null && entry.cost <= entry.parent.cost)
			removeNode(entry);
		// check if value is new min
		if (entry.cost <= min.cost)
			min = entry;

	}

	// Cuts a node from its parent recursively

	private void removeNode(Node entry) {
		entry.isChecked = false;

		if (entry.parent == null)
			return;

		// check for node's siblings
		if (entry.next != entry) {
			entry.next.prev = entry.prev;
			entry.prev.next = entry.next;
		}

		if (entry.parent.child == entry) {
			if (entry.next != entry) {
				entry.parent.child = entry.next;
			}

			else {
				entry.parent.child = null;
			}
		}

		// Decrease the degree of the parent
		entry.parent.degree = entry.parent.degree - 1;

		// add into root list
		entry.prev = entry.next = entry;
		min = mergeLists(min, entry);

		// mark or cut if marked
		if (entry.parent.isChecked)
			removeNode(entry.parent);
		else
			entry.parent.isChecked = true;

		entry.parent = null;
	}
}