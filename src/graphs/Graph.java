package graphs;

import java.util.Vector;
import java.lang.StringBuffer;

// TODO: make sure all method signatures match those in the assignment document

public class Graph {
	// returns the name you have given to this graph library [1 point]
	public String getLibraryName() {
		return "Graphs Library";
	}

	// returns the current version number [1 point]
	public String getLibraryVersion() {
		return "V 1.0.0";
	}

	// TODO: Implement methods starting here
	// the following method adds a vertex to the graph [2 points]
	public void insertVertex(StringBuffer strUniqueID, StringBuffer strData) throws GraphException {
	}

	// inserts an edge between 2 specified vertices [2 points]
	public void insertEdge(StringBuffer strVertex1UniqueID, StringBuffer strVertex2UniqueID,
			StringBuffer strEdgeUniqueID, StringBuffer strEdgeData, int nEdgeCost) throws GraphException {
	}

	// removes vertex and its incident edges [1 point]
	public void removeVertex(StringBuffer strVertexUniqueID) throws GraphException {
	}

	// removes an edge from the graph [1 point]
	public void removeEdge(StringBuffer strEdgeUniqueID) throws GraphException {
	}

	// returns a vector of edges incident to vertex whose
	// id is strVertexUniqueID [1 point]
	public Vector<Edge> incidentEdges(StringBuffer strVertexUniqueID) throws GraphException {
	}

	// returns all vertices in the graph [1 point]
	public Vector<Vertex> vertices() throws GraphException {
	}

	// returns all edges in the graph [1 point]
	public Vector<Edge> edges() throws GraphException {
	}

	// returns an array of the two end vertices of the
	// passed edge [1 point]
	public Vertex[] endVertices(StringBuffer strEdgeUniqueID) throws GraphException {
	}

	// returns the vertex opposite of another vertex [1 point]
	public Vertex opposite(StringBuffer strVertexUniqueID, StringBuffer strEdgeUniqueID) throws GraphException {
	}

	// performs depth first search starting from passed vertex
	// visitor is called on each vertex and edge visited. [12 points]
	public void dfs(StringBuffer strStartVertexUniqueID, Visitor visitor) throws GraphException {
	}

	// performs breadth first search starting from passed vertex
	// visitor is called on each vertex and edge visited. [17 points]
	public void bfs(StringBuffer strStartVertexUniqueID, Visitor visitor) throws GraphException {
	}

	// returns a path between start vertex and end vertex
	// if exists using DFS. [18 points]
	public Vector<PathSegment> pathDFS(StringBuffer strStartVertexUniqueID, StringBuffer strEndVertexUniqueID)
			throws GraphException {
	}

	// finds the closest pair of vertices using divide and conquer
	// algorithm. Use X and Y attributes in each vertex. [30 points]
	public Vertex[] closestPair() throws GraphException {
	}
}
