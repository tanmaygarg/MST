package edu.ufl.ads;

/*
 * Neighbor class keeps track of the neighbors of the vertex The Neighbor class
 * represents the neighbors of the vertex. It has a vertex number and a weight
 * and a next pointer pointing to the next neighbor of the vertices. Every
 * vertex has a neighbor object adjList and every neighbor points to the next
 * neighbor of the vertex.
 */
class Neighbor {
	public int vertexNumber;
	public int weight;
	public Neighbor next;// pointing to the next neighbor

	public Neighbor(int vertex, int cost, Neighbor neighbor) {
		this.vertexNumber = vertex;
		this.weight = cost;
		next = neighbor;
	}
}
