###Compiling and executing the program:

- javac mst.java
- java mst (arguments)

1. For random graph: java mst –r [vertices] [density]
  
 E.g.: java mst –r 1000 50    //for simple prim’s algorithm

￼2. For input from file: java mst –s [filename] java mst –f [filename]
  
  E.g.: java mst –s sample.txt //for Fibonacci heap prim’s algorithm


###Structure of the program:
The program consists of 8 java classes:
- #####Vertex:
The vertex class represents one vertex of the graph and has attributes such as id and the list of the neighbor objects of the vertex. This is used to implement the adjacency list in which every vertex has the details of its neighbors.

- #####Neighbor:
The neighbor class represents the neighbors of the vertex. It has a vertex number and a weight and a next pointer pointing to the next neighbor of the vertices. Every vertex has a neighbor object adjList and every neighbor points to the next neighbor of the vertex.

- #####mst: 
The mst class is the main class of the project with the main function and it calls all the functions and main objects of the GraphRandom and GraphInput class. Both of these classes extends this class.

    Functions:

    Prim(): This function initializes the matrix used for computing the simple prim’s algoritm, and keeps the track of the minimum spanning tree in an array. It calls the computePrim() function for main computation.

    ComputePrim(): It computes the prim’s algorithm recursively by selecting the min cost using the simple approach.

    shortestPaths(): It creates an object of the FHeap class, i.e. a Fibonacci heap and computes the cost of the minimum spanning tree.

    Print(): It prints the graph generated either randomly or by the input file. It is used for debugging.

- #####GraphRandom:
This class generates a random graph according to the given number of vertices and the density and checks the graph for connectivity using dfs.
