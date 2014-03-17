package edu.ufl.ads;

/* 
 * The vertex class represents one vertex of the graph and has attributes such as id
 * and pointer to the neighbor object of the vertex. This is used to implement the 
 * adjacency list in which every vertex has the details of its neighbors.
 */
class Vertex {
	int id;
	Neighbor adjList;// neighbor object for the neighbors of the vertices

	Vertex(int name, Neighbor neighbors) {
		this.id = name;
		this.adjList = neighbors;
	}
}

