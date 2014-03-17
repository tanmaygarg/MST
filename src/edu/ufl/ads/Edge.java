package edu.ufl.ads;

//edge class for the simple prim algorithm
class Edge {
	int v1;
	int v2;
	int cost;

	public Edge(int v1, int v2, int cost) {
		this.v1 = v1;
		this.v2 = v2;
		this.cost = cost;
	}
}