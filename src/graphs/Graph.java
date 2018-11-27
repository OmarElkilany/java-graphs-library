package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.lang.StringBuffer;

// TODO: make sure all method signatures match those in the assignment document

public class Graph {
	
	private static final String version = "1.0.0";
	protected ArrayList<Vertex> _arrVertices;
	
	private ArrayList<Edge> _arrEdges;
	
	// returns the name you have given to this graph library [1 point]
	public String getLibraryName() {
		return "GUC Graphs Library";
	}

	// returns the current version number [1 point]
	public String getLibraryVersion() {
		return "Version: " +  version;
	}

	// TODO: Implement methods starting here
	
	// the following method adds a vertex to the graph [2 points]
	public void insertVertex(String strUniqueID, String strData, int x, int y) throws GraphException {

		// First ensure that the ID is unique
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().equals(strUniqueID)){
				throw new GraphException("Vertex ID already used!");
			}
		}
		
		Vertex newVertex = new Vertex(strUniqueID, strData, x, y);
		_arrVertices.add(newVertex);
	}

	// inserts an edge between 2 specified vertices [2 points]
	public void insertEdge(String strVertex1UniqueID, String strVertex2UniqueID,
			String strEdgeUniqueID, String strEdgeData, int nEdgeCost) throws GraphException {
		
		for(Edge edge : _arrEdges){
			if(edge.getUniqueID().toString().equals(strEdgeUniqueID.toString())){
				throw new GraphException("Edge ID already used!");
			}
		}
		
		// Find Vertices
		Vertex v1 = null,v2 = null;
		for(Vertex v: _arrVertices){
			if(v.getUniqueID().toString().equals(strVertex1UniqueID.toString())){
				v1 = v;
			}
			if(v.getUniqueID().toString().equals(strVertex2UniqueID.toString())){
				v2 = v;
			}
			if(v1 != null && v2 != null){
				break;
			}
		}
		
		if(v1 == null | v2 == null){
			throw new GraphException("One or more of the vertices to connect not found!");
		} else {
			// Check if edge already exists
			for(AdjacentVertexNode node: v1.getAdjacencyList()){
				if(node.getAdjacentVertex().equals(v2)){
					throw new GraphException("The Vertices are already connected!");
				}
			}
		}
		
		// Create edge and add it to IDs array
		Edge newEdge = new Edge(strEdgeUniqueID, strEdgeData, nEdgeCost, v1, v2);
		_arrEdges.add(newEdge); 
		
		// Update the adjacency lists
		v1.getAdjacencyList().add(new AdjacentVertexNode(v2, newEdge));
		v2.getAdjacencyList().add(new AdjacentVertexNode(v1, newEdge));
	}

	// removes vertex and its incident edges [1 point]
	public void removeVertex(String strVertexUniqueID) throws GraphException {
		Vertex vertextoDelete = null;
		
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strVertexUniqueID)){
				vertextoDelete = v;
			}
		}
		
		if(vertextoDelete == null){
			throw new GraphException("Vertex to delete not found");
		}
		
		LinkedList<AdjacentVertexNode> connectedVertices = vertextoDelete.getAdjacencyList();
		
		for(AdjacentVertexNode node:connectedVertices){
			LinkedList<AdjacentVertexNode> adjacentVertexList = _arrVertices.get(_arrVertices.indexOf(node.getAdjacentVertex())).getAdjacencyList();
			for(int i = 0; i < adjacentVertexList.size(); i++){
				if(adjacentVertexList.get(i).getAdjacentVertex().equals(vertextoDelete)){
					adjacentVertexList.remove(i);
					break;
				}
			}
		}
		
		_arrVertices.remove(vertextoDelete);
	}

	// removes an edge from the graph [1 point]
	public void removeEdge(String strEdgeUniqueID) throws GraphException {
		Edge edgeToDelete = null;
		
		for(Edge edge: _arrEdges){
			if(edge.getUniqueID().toString().equals(strEdgeUniqueID)){
				edgeToDelete = edge;
			}
		}
		
		if(edgeToDelete == null){
			throw new GraphException("Edge to delete not found!");
		}
		
		LinkedList<AdjacentVertexNode> list;
		list = _arrVertices.get(_arrVertices.indexOf(edgeToDelete._verFirstVertex)).getAdjacencyList();
		
		// Delete edge from first vertex
		for(AdjacentVertexNode node: list){
			if(node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID)){
				list.remove(node);
				break;
			}
		}
		
		list = _arrVertices.get(_arrVertices.indexOf(edgeToDelete._verSecondVertex)).getAdjacencyList();
		
		// Delete edge from second vertex
		for(AdjacentVertexNode node: list){
			if(node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID)){
				list.remove(node);
				break;
			}
		}
	}

	// returns a vector of edges incident to vertex whose
	// id is strVertexUniqueID [1 point]
	public Vector<Edge> incidentEdges(String strVertexUniqueID) throws GraphException {
		Vertex vertexInQuestion = null;
		
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strVertexUniqueID)){
				vertexInQuestion = v;
			}
		}
		
		if(vertexInQuestion == null){
			throw new GraphException("Vertex not found!");
		}
		
		Vector<Edge> incidentEdges = new Vector<>();
		
		for(AdjacentVertexNode node: vertexInQuestion.getAdjacencyList()){
			incidentEdges.add(node.getConnectingEdge());
		}
		
		return incidentEdges;
	}

	// returns all vertices in the graph [1 point]
	public Vector<Vertex> vertices() throws GraphException {
		return new Vector<Vertex>(_arrVertices);
	}

	// returns all edges in the graph [1 point]
	public Vector<Edge> edges() throws GraphException {
		return new Vector<Edge>(_arrEdges);
	}

	// returns an array of the two end vertices of the
	// passed edge [1 point]
	public Vertex[] endVertices(String strEdgeUniqueID) throws GraphException {
		Edge edgeInQuestion = null;
		
		for(Edge edge: _arrEdges){
			if(edge.getUniqueID().toString().equals(strEdgeUniqueID)){
				edgeInQuestion = edge;
			}
		}
		
		if(edgeInQuestion == null){
			throw new GraphException("Edge not found!");
		}
		
		Vertex[] endVertices = new Vertex[2];
		endVertices[0] = edgeInQuestion._verFirstVertex;
		endVertices[1] = edgeInQuestion._verSecondVertex;
		
		return endVertices;
	}

	// returns the vertex opposite of another vertex [1 point]
	public Vertex opposite(String strVertexUniqueID, String strEdgeUniqueID) throws GraphException {
		Edge edgeInQuestion = null;
		
		for(Edge edge: _arrEdges){
			if(edge.getUniqueID().toString().equals(strEdgeUniqueID)){
				edgeInQuestion = edge;
			}
		}
		
		if(edgeInQuestion == null){
			throw new GraphException("Edge not found!");
		}
		
		if(!strVertexUniqueID.equals(edgeInQuestion._verFirstVertex.getUniqueID().toString()) && !strVertexUniqueID.equals(edgeInQuestion._verSecondVertex.getUniqueID().toString())){
			throw new GraphException("Vertex not connected to Edge/doesn't exist");
		}
		
		if(strVertexUniqueID.equals(edgeInQuestion._verFirstVertex.getUniqueID().toString())){
			return edgeInQuestion._verSecondVertex;
		} else {
			return edgeInQuestion._verFirstVertex;
		}
	}

	// performs depth first search starting from passed vertex
	// visitor is called on each vertex and edge visited. [12 points]
	public void dfs(StringBuffer strStartVertexUniqueID, Visitor visitor) throws GraphException {
		Vertex startVertex = null;
		
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strStartVertexUniqueID)){
				startVertex = v;
			}
		}
		
		if(startVertex == null){
			throw new GraphException("Vertex not found!");
		}
		
		dfsHelper(startVertex, visitor, new ArrayList<Vertex>());
	}
	
	public void dfsHelper(Vertex vertex, Visitor visitor, ArrayList<Vertex> visitedVertices) {

		for(AdjacentVertexNode node : vertex.getAdjacencyList()) {					// Traversing Through All Adjacent Vertices

			if(!visitedVertices.contains(node.getAdjacentVertex())) {				// Checking Whether Vertex Is Visited Or Not

				visitedVertices.add(vertex);										// Adding Vertex To Visited Vertices
				visitor.visit(node.getAdjacentVertex());							// Calling Visitor For The Adjacent Vertex
				visitor.visit(node.getConnectingEdge());							// Calling Visitor For The Connecting Edge

				dfsHelper(node.getAdjacentVertex(), visitor, visitedVertices);		// Calling dfsHelper For The Current Vertex

			}
		}

	}

	// performs breadth first search starting from passed vertex
	// visitor is called on each vertex and edge visited. [17 points]
	public void bfs(StringBuffer strStartVertexUniqueID, Visitor visitor) throws GraphException {
		Vertex startVertex = null;
		
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strStartVertexUniqueID)){
				startVertex = v;
			}
		}
		
		if(startVertex == null){
			throw new GraphException("Vertex not found!");
		}
		
		ArrayList<Vertex> visitedVertices = new ArrayList<Vertex>();				// Array List For Visited Vertices
		ArrayList<Vertex> verticesToBeVisited = new ArrayList<Vertex>();			// Array List For Vertices To Be Visited
		ArrayList<Edge> edgesToBeVisited = new ArrayList<Edge>();					// Array List For Edges To Be Visited Through Visited Vertices
		
		verticesToBeVisited.add(startVertex);										// Adding Start Vertex To Visited Vertices (Redundant & Add Only To Satisfy The Condition)
		edgesToBeVisited.add(new Edge(null, null, 0, null, null));					// Adding Redundant Edge To Avoid The Error In The First Iteration
		
		while(verticesToBeVisited.size() != 0) {									// Checking If There Is More Vertices Need To Be Visited

			Vertex vertex = verticesToBeVisited.remove(0);							// Getting First Element In The Vertices To Be Visited
			Edge edge = edgesToBeVisited.remove(0);									// Getting First Edge That Connects The Parent Vertex With The Child Vertex
			
			visitedVertices.add(vertex);											// Adding Vertex To The Visited Vertices

			if(vertex != startVertex) {
				visitor.visit(vertex);
				visitor.visit(edge);
			}
			
			for(AdjacentVertexNode node : vertex.getAdjacencyList()) {				// Traversing Through All Adjacent Vertices
				
				if(!visitedVertices.contains(node.getAdjacentVertex())) {			// Checking If Vertex Has Been Visited Or Not

					verticesToBeVisited.add(node.getAdjacentVertex());				// Adding Vertex To Be Visited To The End Of The Array List
					edgesToBeVisited.add(node.getConnectingEdge());					// Adding Edge That Connects Visited Vertex With Vertex To Be Visited

				}
				
			}

		}

	}

	// returns a path between start vertex and end vertex
	// if exists using DFS. [18 points]
	public Vector<PathSegment> pathDFS(StringBuffer strStartVertexUniqueID, StringBuffer strEndVertexUniqueID)
			throws GraphException {
				return null;
	}

	// finds the closest pair of vertices using divide and conquer
	// algorithm. Use X and Y attributes in each vertex. [30 points]
	public Vertex[] closestPair() throws GraphException {
		return null;
	}
}
