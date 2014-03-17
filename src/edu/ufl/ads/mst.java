package edu.ufl.ads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//Main class for performing all the input and calling the objects
class mst {
	// list of all the vertexes
	Vertex[] vertexList;
	// a,b are used for storing vertices, and c for costs
	ArrayList<Integer> a = new ArrayList<Integer>(5000);
	ArrayList<Integer> b = new ArrayList<Integer>(5000);
	ArrayList<Integer> c = new ArrayList<Integer>(5000);
	//time in fibonacci mode
	long time = 0;
	//getting the result in fibonacci. Declared here to display in input mode
	Map<Integer, Double> result = new HashMap<Integer, Double>();


	// to select mst from graph using simple scheme
	int[][] simple = new int[6000][6000];
	// list of edge objects for calculating mst using simple prim
	Edge[] edgeList = new Edge[10000];
	int rand = 10000;
	int flag = 0;
	// calculating the cost of the mst
	int sumSimple;
	int sumHeapCost = 0;

	/*
	 * This function initializes the matrix used for computing the simple prim's
	 * algorithm, and keeps the track of the minimum spanning tree in an array.
	 * It calls the computePrim() function for main computation.
	 */
	public void prim() {
		// allot max value to the edges
		for (int i = 0; i < vertexList.length; i++) {
			for (int j = 0; j < vertexList.length; j++) {
				simple[i][j] = Integer.MAX_VALUE;
			}
		}
		// initialize array with the costs
		for (int i = 0; i < vertexList.length; i++) {
			for (Neighbor nbr = vertexList[i].adjList; nbr != null; nbr = nbr.next) {
				simple[i][vertexList[nbr.vertexNumber].id] = nbr.weight;
			}
		}
		// initialize edgeList for keeping track of mst
		for (int v = 0; v < vertexList.length; v++) {
			edgeList[v] = new Edge(vertexList[v].id, rand, 5000);
		}
		// giving cost 0 to vertex 0
		edgeList[0].v1 = rand;
		// calling the computePrim function
		computePrim(0);
		int sum1 = 0;
		// calculating the sum of mst costs from the sum arraylist c
		for (int i = 0; i < c.size(); i++) {
			sum1 = sum1 + c.get(i);
		}
		sumSimple = sum1;
	}

	// computing the prim MST using recursion
	public void computePrim(int v) {
		while (flag < vertexList.length - 1) {
			flag++;
			int min = 5000;
			int index = 0;

			int[] simple2 = new int[vertexList.length];
			// storing the neighbors in the matrix
			for (int i = 0; i < vertexList.length; i++)
				simple2[i] = simple[v][i];
			// adding the costs to the edgeList matrix
			for (int j = 0; j < vertexList.length; j++) {
				if ((edgeList[j].cost > simple2[j]) && (edgeList[j].v1 != rand)) {
					edgeList[j].v2 = v;
					edgeList[j].cost = simple2[j];

				}
			}
			// finding the minimum cost amongst the neighbors
			for (int v2 = 0; v2 < vertexList.length; v2++) {
				if (edgeList[v2].v1 != rand) {
					if (edgeList[v2].cost < min) {
						min = edgeList[v2].cost;
						index = v2;
					}
				}
			}
			// adding the edges to the arraylists
			a.add(index);
			// marking the visited nodes
			edgeList[index].v1 = rand;
			b.add(edgeList[index].v2);
			c.add(min);
			// recusively calling the function
			computePrim(index);
		}
	}

	// Printing the graph for debugging
	public void print() {
		int l = 0;
		System.out.println();
		for (int v = 0; v < vertexList.length; v++) {
			System.out.print(vertexList[v].id);
			for (Neighbor nbr = vertexList[v].adjList; nbr != null; nbr = nbr.next) {
				System.out.print(" -> " + vertexList[nbr.vertexNumber].id + "("
						+ nbr.weight + ")");
				l++;
			}
			System.out.println("\n");
		}
		System.out.println("Number of  edges = " + l / 2);
	}

	// finding the shortest path MST using the Fibonacci heap
	public void shortestPaths(int source) {

		long start = 0;
		long stop;
		for (int v = 0; v < vertexList.length; v++) {
			edgeList[v] = new Edge(vertexList[v].id, rand, 5000);
		}
		// creating object of fibonacci
		FHeap pq = new FHeap();
		// creating a map for checking
		Map<Integer, FHeap.Node> check = new HashMap<Integer, FHeap.Node>();
		// storing the mst costs

		for (int v = 0; v < vertexList.length; v++) {
			check.put(vertexList[v].id,
					pq.enqueue(vertexList[v].id, Double.POSITIVE_INFINITY));
		}
		start = System.currentTimeMillis();
		// allot cost 0 to initial node
		pq.decreaseKey(check.get(vertexList[source].id), 0.0);

		while (!pq.isEmpty()) {
			// take the current node and get its minimum cost
			FHeap.Node current = pq.dequeueMin();

			// store the values in the table
			result.put(current.getValue(), current.getCost());

			// update the costs
			for (Neighbor nbr = vertexList[current.getValue()].adjList; nbr != null; nbr = nbr.next) {

				// edge is not added if shortest cost is known
				if (result.containsKey(vertexList[nbr.vertexNumber].id))
					continue;

				// update cost to shortest
				FHeap.Node finalCost = check
						.get(vertexList[nbr.vertexNumber].id);
				if (nbr.weight < finalCost.getCost())
					pq.decreaseKey(finalCost, nbr.weight);
			}
		}
		stop = System.currentTimeMillis();
		// computing the time
		time = stop - start;

		// calculate the MST cost
		for (int i = 0; i < vertexList.length; i++) {
			sumHeapCost = (int) (sumHeapCost + result.get(i));
		}
		// for printing in case of input from file in fibonacci mode
	
		// System.out.println(sumHeapCost);
	}

	public static void main(String[] args) throws IOException {
		String input = args[0];
		// checking for random
		if ("-r".equals(input)) {
			long start = 0;
			long stop;
			long timeSim;
			int nodes = Integer.parseInt(args[1]);
			int density = Integer.parseInt(args[2]);
			// creating g object for generating the random graph
			GraphRandom g = new GraphRandom(nodes, density);
			start = System.currentTimeMillis();
			// running prim's algorithm for the graph
			g.prim();
			stop = System.currentTimeMillis();
			// execution time for your algorithm.
			timeSim = stop - start;
			// g.print();
			g.shortestPaths(0);
			System.out.println("Time for simple prim= " + timeSim);
			System.out.println("Time for Fibonacci Heap= " + g.time);

		} // for the file input using simple approach
		else if ("-s".equals(input)) {
			String file = args[1];
			GraphInput graph = new GraphInput(file);
			// graph.print();
			graph.prim();
			System.out.println(graph.sumSimple);
			for (int i = 0; i < graph.a.size(); i++) {
				System.out.println(graph.b.get(i) + " " + graph.a.get(i));
			}
		}// for the file input using the fibonacci heap
		else if ("-f".equals(input)) {
			String file = args[1];
			GraphInput graph = new GraphInput(file);
			// graph.print();
			graph.shortestPaths(0);
			System.out.println(graph.sumHeapCost);
			for (int v = 0; v < graph.vertexList.length; v++) {
				for (Neighbor nbr = graph.vertexList[v].adjList; nbr != null; nbr = nbr.next) {
					for (int i = 0; i < graph.vertexList.length; i++) {
						if (graph.result.get(i) == nbr.weight) {
							if (graph.edgeList[graph.vertexList[nbr.vertexNumber].id].v2 != graph.vertexList[v].id) {
								graph.edgeList[v].v1 = (graph.vertexList[v].id);
								graph.edgeList[v].v2 = graph.vertexList[nbr.vertexNumber].id;
							}
						}
					}
				}
			}
			for (int v = 0; v < graph.vertexList.length - 1; v++) {
				System.out.print(graph.edgeList[v].v1 + " "
						+ graph.edgeList[v].v2 + "\n");
			}

		}

	}

}