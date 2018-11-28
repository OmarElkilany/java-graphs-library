package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
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
		Vertex vertexToDelete = null;
		
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strVertexUniqueID)){
				vertexToDelete = v;
				break;
			}
		}
		
		if(vertexToDelete == null){
			throw new GraphException("Vertex to delete not found");
		}
		
		// retrieve the nodes connected to the vertex-to-delete
		LinkedList<AdjacentVertexNode> connectedNodes = vertexToDelete.getAdjacencyList();
		
		// loop over the connected nodes
		for(AdjacentVertexNode node:connectedNodes){
			
			// get the adjacency list of the connected node's vertex
			LinkedList<AdjacentVertexNode> connectedNodeList = node.getAdjacentVertex().getAdjacencyList();
			
			// loop over the connected node's adjacency list
			for(AdjacentVertexNode connectedNodeListEntry: connectedNodeList) {
				
				if(connectedNodeListEntry.getAdjacentVertex().equals(vertexToDelete)) {
					// remove the node of the vertex-to-delete from the adjacency list of the connected node
					connectedNodeList.remove(connectedNodeListEntry);
				}
			}
		}
		
		// remove the edges from the edges Array list
		for (AdjacentVertexNode node : connectedNodes) {
			_arrEdges.remove(node.getConnectingEdge());
		}
		
		// remove the vertex-to-delete
		_arrVertices.remove(vertexToDelete);
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
		
		// retrieve the adjacency list of the first vertex connected to the edge-to-delete
		LinkedList<AdjacentVertexNode> list;
		list = _arrVertices.get(_arrVertices.indexOf(edgeToDelete._verFirstVertex)).getAdjacencyList();
		
		// Delete the node from first vertex's adjacency list
		for(AdjacentVertexNode node: list){
			if(node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID)){
				list.remove(node);
				break;
			}
		}
		
		// retrieve the adjacency list of the second vertex connected to the edge-to-delete
		list = _arrVertices.get(_arrVertices.indexOf(edgeToDelete._verSecondVertex)).getAdjacencyList();
		
		// Delete the node from second vertex's adjacency list
		for(AdjacentVertexNode node: list){
			if(node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID)){
				list.remove(node);
				break;
			}
		}
		
		// remove the edge-to-delete
		_arrEdges.remove(edgeToDelete);
	}

	// returns a vector of edges incident to vertex whose
	// id is strVertexUniqueID [1 point]
	public Vector<Edge> incidentEdges(String strVertexUniqueID) throws GraphException {
		Vertex vertexInQuestion = null;
		
		// find the vertex
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strVertexUniqueID)){
				vertexInQuestion = v;
				break;
			}
		}
		
		if(vertexInQuestion == null){
			throw new GraphException("Vertex not found!");
		}
		
		// calculate the incident edges
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
		
		// find the edge
		for(Edge edge: _arrEdges){
			if(edge.getUniqueID().toString().equals(strEdgeUniqueID)){
				edgeInQuestion = edge;
				break;
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
		
		// find the edge
		for(Edge edge: _arrEdges){
			if(edge.getUniqueID().toString().equals(strEdgeUniqueID)){
				edgeInQuestion = edge;
				break;
			}
		}
		
		if(edgeInQuestion == null){
			throw new GraphException("Edge not found!");
		}
		
		// check if the edge-vertex combination provided is valid
		if(!strVertexUniqueID.equals(edgeInQuestion._verFirstVertex.getUniqueID().toString()) && !strVertexUniqueID.equals(edgeInQuestion._verSecondVertex.getUniqueID().toString())){
			throw new GraphException("Vertex not connected to Edge/doesn't exist");
		}
		
		// return the appropriate vertex
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

		// clear meta-data and find the start vertex
		for (Vertex v : _arrVertices) {

			// clear meta-data
			v.setColor("WHITE");
			v.setPredecessorID("NIL");

			// find the start vertex
			if (v.getUniqueID().toString().equals(strStartVertexUniqueID)) {
				startVertex = v;
			}

		}

		if (startVertex == null) {
			throw new GraphException("Vertex not found!");
		}

		// call the helper
		dfsVisit(startVertex, visitor);
	}

	private void dfsVisit(Vertex vertex, Visitor visitor) {

		// visit the current vertex
		vertex.setColor("GRAY");
		visitor.visit(vertex);

		// visit the adjacent vertices using DFS
		for (AdjacentVertexNode node : vertex.getAdjacencyList()) {

			// find an unvisited node
			if (node.getAdjacentVertex().getColor() == "WHITE") {

				// visit the connecting edge
				visitor.visit(node.getConnectingEdge());

				// set the predecessor
				node.getAdjacentVertex().setPredecessorID(vertex.getUniqueID().toString());

				// go deeper
				dfsVisit(node.getAdjacentVertex(), visitor);
			}
		}

		// done with all the adjacent vertices
		vertex.setColor("BLACK");
	}

	// performs breadth first search starting from passed vertex
	// visitor is called on each vertex and edge visited. [17 points]
	public void bfs(StringBuffer strStartVertexUniqueID, Visitor visitor) throws GraphException {
		Vertex startVertex = null;
		
		// initialize BFS
		for(Vertex v : _arrVertices){
			// determine and visit the start vertex
			if(v.getUniqueID().toString().equals(strStartVertexUniqueID)){
				startVertex = v;
				startVertex.setColor("GRAY");
				visitor.visit(startVertex);
			} else {
				v.setColor("WHITE");
				v.setPredecessorID("NIL");
			}	
		}
		
		if(startVertex == null){
			throw new GraphException("Vertex not found!");
		}
		
		// perform the BFS
		Queue<Vertex> verticesQueue = new LinkedList<Vertex>();
		
		verticesQueue.add(startVertex);
		
		while(!verticesQueue.isEmpty()) {
			Vertex currentVertex = verticesQueue.remove();
			
			for(AdjacentVertexNode node: currentVertex.getAdjacencyList()) {
				
				if(node.getAdjacentVertex().getColor() == "WHITE") {
					
					// visit the edge and then the vertex
					node.getAdjacentVertex().setColor("GRAY");
					visitor.visit(node.getConnectingEdge());
					visitor.visit(node.getAdjacentVertex());
					
					// set the predecessor ID
					node.getAdjacentVertex().setPredecessorID(currentVertex.getUniqueID().toString());
					
					// enqueue the visited vertex
					verticesQueue.add(node.getAdjacentVertex());
				}
			}
			
			// done with vertex and all of its adjacent vertices
			currentVertex.setColor("BLACK");	
		}
		
	}

	// returns a path between start vertex and end vertex
	// if exists using DFS. [18 points]
	public Vector<PathSegment> pathDFS(StringBuffer strStartVertexUniqueID, StringBuffer strEndVertexUniqueID)
			throws GraphException {
		
		// define arguments for pathDFSHelper
		Vertex startVertex = null, endVertex = null;
		Stack<Object> pathStack = new Stack<>();

		// clear meta-data and find the start and end vertices
		for (Vertex v : _arrVertices) {

			// clear meta-data
			v.setColor("WHITE");
			v.setPredecessorID("NIL");

			// find the start vertex
			if (v.getUniqueID().toString().equals(strStartVertexUniqueID)) {
				startVertex = v;
			}

			// find the end vertex
			if (v.getUniqueID().toString().equals(strEndVertexUniqueID)) {
				endVertex = v;
			}

		}

		if (startVertex == null || endVertex == null) {
			throw new GraphException("Start or end vertex not found!");
		}

		// perform DFS
		pathDFSHelper(pathStack, startVertex, endVertex);

		// create the output vector
		Vector<PathSegment> result = new Vector<PathSegment>();

		if (pathStack.isEmpty()) {
			return result;
		} else {
			// insert the last vertex
			result.insertElementAt(new PathSegment((Vertex) pathStack.pop(), null), 0);
			
			// insert all the other path segments
			while(!pathStack.isEmpty()){
				
				// pop an edge and a vertex
				Edge pathSegmentEdge = (Edge) pathStack.pop();
				Vertex pathSegmentVertex = (Vertex) pathStack.pop();
				
				// add them to the path vector
				result.insertElementAt(new PathSegment(pathSegmentVertex, pathSegmentEdge), 0);
				
			}
			
			// return the vector
			return result;	
		}
	}
	
	private void pathDFSHelper(Stack<Object> pathStack, Vertex currentVertex, Vertex destinationVertex) {

		// visit the current vertex
		currentVertex.setColor("GRAY");
		pathStack.push(currentVertex);
		
		// stop if destination is reached
		if(currentVertex.getUniqueID().toString().equals(destinationVertex.getUniqueID().toString())) {
			return;
		}

		// visit the adjacent vertices using DFS
		for (AdjacentVertexNode node : currentVertex.getAdjacencyList()) {

			// find an unvisited node
			if (node.getAdjacentVertex().getColor() == "WHITE") {

				// set the predecessor
				node.getAdjacentVertex().setPredecessorID(currentVertex.getUniqueID().toString());

				// add the edge to the path
				pathStack.push(node.getConnectingEdge());
				
				// go deeper
				pathDFSHelper(pathStack, node.getAdjacentVertex(), destinationVertex);
				
				// remove the edge since it led nowhere
				pathStack.pop();
			}
		}

		// done with all the adjacent vertices
		currentVertex.setColor("BLACK");
		
		// remove the vertex since it led nowhere
		pathStack.pop();
	}

	// finds the closest pair of vertices using divide and conquer
	// algorithm. Use X and Y attributes in each vertex. [30 points]
	public Vertex[] closestPair() throws GraphException {
		return null;
	}
}
