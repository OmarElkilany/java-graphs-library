package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.lang.StringBuffer;

// TODO: make sure all method signatures match those in the assignment document

public class Graph {
	
	private static final int version = 1;
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
	public void insertVertex(StringBuffer strUniqueID, StringBuffer strData) throws GraphException {

		// First ensure that the ID is unique
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().equals(strUniqueID)){
				throw new GraphException("Vertex ID already used!");
			}
		}
		
		Vertex newVertex = new Vertex(strUniqueID, strData);
		_arrVertices.add(newVertex);
	}

	// inserts an edge between 2 specified vertices [2 points]
	public void insertEdge(StringBuffer strVertex1UniqueID, StringBuffer strVertex2UniqueID,
			StringBuffer strEdgeUniqueID, StringBuffer strEdgeData, int nEdgeCost) throws GraphException {
		
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
	public void removeVertex(StringBuffer strVertexUniqueID) throws GraphException {
		Vertex vertextoDelete = null;
		
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strVertexUniqueID.toString())){
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
	public void removeEdge(StringBuffer strEdgeUniqueID) throws GraphException {
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
			if(node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID.toString())){
				list.remove(node);
				break;
			}
		}
		
		list = _arrVertices.get(_arrVertices.indexOf(edgeToDelete._verSecondVertex)).getAdjacencyList();
		
		// Delete edge from second vertex
		for(AdjacentVertexNode node: list){
			if(node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID.toString())){
				list.remove(node);
				break;
			}
		}
	}

	// returns a vector of edges incident to vertex whose
	// id is strVertexUniqueID [1 point]
	public Vector<Edge> incidentEdges(StringBuffer strVertexUniqueID) throws GraphException {
		Vertex vertexInQuestion = null;
		
		for(Vertex v : _arrVertices){
			if(v.getUniqueID().toString().equals(strVertexUniqueID.toString())){
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
	public Vertex[] endVertices(StringBuffer strEdgeUniqueID) throws GraphException {
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
	public Vertex opposite(StringBuffer strVertexUniqueID, StringBuffer strEdgeUniqueID) throws GraphException {
		Edge edgeInQuestion = null;
		
		for(Edge edge: _arrEdges){
			if(edge.getUniqueID().toString().equals(strEdgeUniqueID)){
				edgeInQuestion = edge;
			}
		}
		
		if(edgeInQuestion == null){
			throw new GraphException("Edge not found!");
		}
		
		if(!strVertexUniqueID.equals(edgeInQuestion._verFirstVertex.getUniqueID().toString()) && !strVertexUniqueID.equals(edgeInQuestion._verFirstVertex.getUniqueID().toString())){
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
		
	}

	// performs breadth first search starting from passed vertex
	// visitor is called on each vertex and edge visited. [17 points]
	public void bfs(StringBuffer strStartVertexUniqueID, Visitor visitor) throws GraphException {
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
