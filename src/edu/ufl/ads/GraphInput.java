package edu.ufl.ads;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//Class for reading the graph from the input file
class GraphInput extends mst {
	public GraphInput(String file) throws FileNotFoundException {
		// scanner
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new File(file));
		ArrayList<Integer> inp1 = new ArrayList<Integer>();
		ArrayList<Integer> inp2 = new ArrayList<Integer>();
		ArrayList<Integer> inp3 = new ArrayList<Integer>();
		int i = 0;
		int vertices = sc.nextInt();
		int edges = sc.nextInt();
		vertexList = new Vertex[vertices];
		// reading from the file and storing in the array lists
		while (sc.hasNext()) {

			inp1.add(sc.nextInt());
			inp2.add(sc.nextInt());
			inp3.add(sc.nextInt());
		}
		for (i = 0; i < vertices; i++) {
			vertexList[i] = new Vertex(i, null);
		}
		// inputting from arraylist to the adjacency list
		for (i = 0; i < edges; i++) {
			// read vertex numbers
			// add v2 to front of v1's adjacency list and
			vertexList[inp1.get(i)].adjList = new Neighbor(inp2.get(i),
					inp3.get(i), vertexList[inp1.get(i)].adjList);
			// add v1 to front of v2's adjacency list
			vertexList[inp2.get(i)].adjList = new Neighbor(inp1.get(i),
					inp3.get(i), vertexList[inp2.get(i)].adjList);
		}

	}
}