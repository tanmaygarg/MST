package edu.ufl.ads;

import java.util.Random;

//class for generating the Graph randomly
class GraphRandom extends mst {

	// function for generating a random number
	public int random(int k) {
		Random random = new Random();
		int num = random.nextInt(k);
		return num;
	}

	// function for depth first search
	public void dfs(int v, int[] dfscheck) {
		for (Neighbor nbr = vertexList[v].adjList; nbr != null; nbr = nbr.next) {
			if (dfscheck[vertexList[nbr.vertexNumber].id] != 1) {
				dfscheck[vertexList[nbr.vertexNumber].id] = 1;
				dfs(vertexList[nbr.vertexNumber].id, dfscheck);
			}
		}
	}

	// function running DFS and checking if the graph is connected
	public void checkConnected(int n, int den) {
		alotNeighbors(n, den);
		int[] dfscheck = new int[vertexList.length];
		for (int v = 0; v < vertexList.length; v++) {
			dfscheck[v] = 0;
		}
		// checking for dfs
		dfs(0, dfscheck);
		for (int v = 0; v < vertexList.length; v++) {
			// recursively calling the check connected function ifthe graph is
			// not connected
			if (dfscheck[v] == 0) {
				checkConnected(n, den);
			}
		}
	}

	// randomly adding edges to the graph
	public void alotNeighbors(int n, int den) {
		for (int v = 0; v < vertexList.length; v++) {
			vertexList[v] = new Vertex(v, null);
		}
		for (int i = 0; i < den; i++) {

			int v1 = random(n);
			int v2 = random(n);
			// check if vertices are not same
			if (v1 != v2) {

				Neighbor nbr;
				// checking if the selected vertices are not already neighbors
				for (nbr = vertexList[v2].adjList; nbr != null; nbr = nbr.next) {
					if (vertexList[nbr.vertexNumber].id == v1) {
						break;
					}
				}
				if (nbr == null) {

					// add v2 to front of v1's adjacency list and
					// add v1 to front of v2's adjacency list
					int cost = random(1000);
					vertexList[v1].adjList = new Neighbor(v2, cost,
							vertexList[v1].adjList);
					vertexList[v2].adjList = new Neighbor(v1, cost,
							vertexList[v2].adjList);
				} else
					// increasing density count so as to compensate for the not
					// allocating cases
					den++;
			} else
				den++;
		}

	}

	// calling all the methods and creating a connected undirected graph
	public GraphRandom(int n, int density) {

		vertexList = new Vertex[n];
		// read vertices
		int temp = n * (n - 1);
		temp = (int) Math.floor(temp / 2);
		// calculating density
		int den = (int) Math.ceil(temp * density / 100);
		// calling the checkConnected to check if the graph is connected
		checkConnected(n, den);

	}

}