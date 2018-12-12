package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

// TODO: make sure all method signatures match those in the assignment document

public class Graph {

	private static final String version = "2.0.0";
	protected ArrayList<Vertex> _arrVertices;

	private ArrayList<Edge> _arrEdges;

	public Graph() {
		_arrVertices = new ArrayList<>();
		_arrEdges = new ArrayList<>();
	}

	// returns the name you have given to this graph library [1 point]
	public String getLibraryName() {
		return "GUC Graphs Library";
	}

	// returns the current version number [1 point]
	public String getLibraryVersion() {
		return "Version: " + version;
	}

	// the following method adds a vertex to the graph [2 points]
	public void insertVertex(String strUniqueID, String strData, int x, int y) throws GraphException {

		// First ensure that the ID is unique
		for (Vertex v : _arrVertices) {
			if (strUniqueID.equals(v.getUniqueID().toString())) {
				throw new GraphException("Vertex ID already used!");
			}
		}

		Vertex newVertex = new Vertex(strUniqueID, strData, x, y, _arrVertices.size());
		_arrVertices.add(newVertex);
	}

	// inserts an edge between 2 specified vertices [2 points]
	public void insertEdge(String strVertex1UniqueID, String strVertex2UniqueID, String strEdgeUniqueID,
			String strEdgeData, int nEdgeCost) throws GraphException {

		for (Edge edge : _arrEdges) {
			if (edge.getUniqueID().toString().equals(strEdgeUniqueID.toString())) {
				throw new GraphException("Edge ID already used!");
			}
		}

		// Find Vertices
		Vertex v1 = null, v2 = null;
		for (Vertex v : _arrVertices) {
			if (v.getUniqueID().toString().equals(strVertex1UniqueID.toString())) {
				v1 = v;
			}
			if (v.getUniqueID().toString().equals(strVertex2UniqueID.toString())) {
				v2 = v;
			}
			if (v1 != null && v2 != null) {
				break;
			}
		}

		if (v1 == null | v2 == null) {
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

		for (Vertex v : _arrVertices) {
			if (v.getUniqueID().toString().equals(strVertexUniqueID)) {
				vertexToDelete = v;
				break;
			}
		}

		if (vertexToDelete == null) {
			throw new GraphException("Vertex to delete not found");
		}
		
		int verIdx = vertexToDelete.getIdx();
		
		for(Vertex v : _arrVertices){
			if(v.getIdx() > verIdx){
				v.idx--;
			}
		}

		// retrieve the nodes connected to the vertex-to-delete
		LinkedList<AdjacentVertexNode> connectedNodes = vertexToDelete.getAdjacencyList();

		// loop over the connected nodes
		for (AdjacentVertexNode node : connectedNodes) {

			// get the adjacency list of the connected node's vertex
			LinkedList<AdjacentVertexNode> connectedNodeList = node.getAdjacentVertex().getAdjacencyList();
			// loop over the connected node's adjacency list
			for (AdjacentVertexNode connectedNodeListEntry : connectedNodeList) {
				if (connectedNodeListEntry.getAdjacentVertex().equals(vertexToDelete)) {
					// remove the node of the vertex-to-delete from the
					// adjacency list of the
					// connected node
					connectedNodeList.remove(connectedNodeListEntry);
					break;
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

		for (Edge edge : _arrEdges) {
			if (edge.getUniqueID().toString().equals(strEdgeUniqueID)) {
				edgeToDelete = edge;
			}
		}

		if (edgeToDelete == null) {
			throw new GraphException("Edge to delete not found!");
		}

		// retrieve the adjacency list of the first vertex connected to the
		// edge-to-delete
		LinkedList<AdjacentVertexNode> list;
		list = _arrVertices.get(_arrVertices.indexOf(edgeToDelete._verFirstVertex)).getAdjacencyList();

		// Delete the node from first vertex's adjacency list
		for (AdjacentVertexNode node : list) {
			if (node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID)) {
				list.remove(node);
				break;
			}
		}

		// retrieve the adjacency list of the second vertex connected to the
		// edge-to-delete
		list = _arrVertices.get(_arrVertices.indexOf(edgeToDelete._verSecondVertex)).getAdjacencyList();

		// Delete the node from second vertex's adjacency list
		for (AdjacentVertexNode node : list) {
			if (node.getConnectingEdge().getUniqueID().toString().equals(strEdgeUniqueID)) {
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
		for (Vertex v : _arrVertices) {
			if (v.getUniqueID().toString().equals(strVertexUniqueID)) {
				vertexInQuestion = v;
				break;
			}
		}

		if (vertexInQuestion == null) {
			throw new GraphException("Vertex not found!");
		}

		// calculate the incident edges
		Vector<Edge> incidentEdges = new Vector<>();

		for (AdjacentVertexNode node : vertexInQuestion.getAdjacencyList()) {
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
		for (Edge edge : _arrEdges) {
			if (edge.getUniqueID().toString().equals(strEdgeUniqueID)) {
				edgeInQuestion = edge;
				break;
			}
		}

		if (edgeInQuestion == null) {
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
		for (Edge edge : _arrEdges) {
			if (edge.getUniqueID().toString().equals(strEdgeUniqueID)) {
				edgeInQuestion = edge;
				break;
			}
		}

		if (edgeInQuestion == null) {
			throw new GraphException("Edge not found!");
		}

		// check if the edge-vertex combination provided is valid
		if (!strVertexUniqueID.equals(edgeInQuestion._verFirstVertex.getUniqueID().toString())
				&& !strVertexUniqueID.equals(edgeInQuestion._verSecondVertex.getUniqueID().toString())) {
			throw new GraphException("Vertex not connected to Edge/doesn't exist");
		}

		// return the appropriate vertex
		if (strVertexUniqueID.equals(edgeInQuestion._verFirstVertex.getUniqueID().toString())) {
			return edgeInQuestion._verSecondVertex;
		} else {
			return edgeInQuestion._verFirstVertex;
		}
	}

	// performs depth first search starting from passed vertex
	// visitor is called on each vertex and edge visited. [12 points]
	public void dfs(String strStartVertexUniqueID, Visitor visitor) throws GraphException {
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
	public void bfs(String strStartVertexUniqueID, Visitor visitor) throws GraphException {
		Vertex startVertex = null;

		// initialize BFS
		for (Vertex v : _arrVertices) {
			// determine and visit the start vertex
			if (v.getUniqueID().toString().equals(strStartVertexUniqueID)) {
				startVertex = v;
				startVertex.setColor("GRAY");
				visitor.visit(startVertex);
			} else {
				v.setColor("WHITE");
				v.setPredecessorID("NIL");
			}
		}

		if (startVertex == null) {
			throw new GraphException("Vertex not found!");
		}

		// perform the BFS
		Queue<Vertex> verticesQueue = new LinkedList<Vertex>();

		verticesQueue.add(startVertex);

		while (!verticesQueue.isEmpty()) {
			Vertex currentVertex = verticesQueue.remove();

			for (AdjacentVertexNode node : currentVertex.getAdjacencyList()) {

				if (node.getAdjacentVertex().getColor() == "WHITE") {

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

	// variable to store the final path
	static Stack<Object> finalPathStack;

	// returns a path between start vertex and end vertex
	// if exists using DFS. [18 points]
	public Vector<PathSegment> pathDFS(String strStartVertexUniqueID, String strEndVertexUniqueID)
			throws GraphException {

		// define arguments for pathDFSHelper
		Vertex startVertex = null, endVertex = null;
		Stack<Object> pathStack = new Stack<>();

		// reset the finalPathStack
		finalPathStack = null;

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

		if (finalPathStack == null) {
			return result;
		} else {
			// insert the last vertex
			result.add(new PathSegment((Vertex) finalPathStack.pop(), null));

			// insert all the other path segments
			while (!finalPathStack.isEmpty()) {

				// pop an edge and a vertex
				Edge pathSegmentEdge = (Edge) finalPathStack.pop();
				Vertex pathSegmentVertex = (Vertex) finalPathStack.pop();

				// add them to the path vector
				result.add(new PathSegment(pathSegmentVertex, pathSegmentEdge));

			}

			Collections.reverse(result);

			// return the vector
			return result;
		}
	}

	@SuppressWarnings("unchecked")
	private void pathDFSHelper(Stack<Object> pathStack, Vertex currentVertex, Vertex destinationVertex) {

		// visit the current vertex
		currentVertex.setColor("GRAY");
		pathStack.push(currentVertex);

		// stop if destination is reached
		if (currentVertex.getUniqueID().toString().equals(destinationVertex.getUniqueID().toString())) {
			// save the result
			finalPathStack = (Stack<Object>) pathStack.clone();

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
	@SuppressWarnings("unchecked")
	public Vertex[] closestPair() throws GraphException {
		if (_arrVertices.size() < 2) {
			throw new GraphException("Not enough points");
		}

		// Sort points according to X
		ArrayList<Vertex> pointsXSorted = (ArrayList<Vertex>) _arrVertices.clone();
		Collections.sort(pointsXSorted, Vertex.sortbyX);

		// Sort points according to Y
		ArrayList<Vertex> pointsYSorted = (ArrayList<Vertex>) _arrVertices.clone();
		Collections.sort(pointsYSorted, Vertex.sortbyY);

		Vertex[] pair = closestPairHelper(pointsXSorted, pointsYSorted);

		return pair;
	}

	private Vertex[] closestPairHelper(ArrayList<Vertex> pointsXSorted, ArrayList<Vertex> pointsYSorted) {
		Vertex[] result = new Vertex[2];

		// if the number of elements in the arraylist is less than 3, just find
		// the pair
		// using brute-force
		if (pointsXSorted.size() <= 3) {
			double minDistance = Double.MAX_VALUE;

			for (Vertex ver1 : pointsXSorted) {
				for (Vertex ver2 : pointsXSorted) {
					if (ver1 != ver2 && ver1.getDistance(ver2) < minDistance) {
						minDistance = ver1.getDistance(ver2);
						result[0] = ver1;
						result[1] = ver2;
					}
				}
			}

			return result;
		}

		int midpoint = pointsXSorted.size() / 2;

		// New X-coordinates-sorted subarrays
		ArrayList<Vertex> leftSubarrayX = new ArrayList<>(pointsXSorted.subList(0, midpoint));
		ArrayList<Vertex> rightSubarrayX = new ArrayList<>(pointsXSorted.subList(midpoint, pointsXSorted.size()));

		// New Y-coordinates-sorted subarrays
		ArrayList<Vertex> leftSubarrayY = new ArrayList<>(leftSubarrayX.size());
		ArrayList<Vertex> rightSubarrayY = new ArrayList<>(rightSubarrayX.size());
		// Fill them according to X-coordinates
		for (int i = 0; i < pointsYSorted.size(); i++) {
			if (pointsYSorted.get(i).getX() < pointsXSorted.get(midpoint).getX()) {
				leftSubarrayY.add(pointsYSorted.get(i));
			} else {
				rightSubarrayY.add(pointsYSorted.get(i));
			}
		}

		// Divide
		Vertex[] leftResult = closestPairHelper(leftSubarrayX, leftSubarrayY);
		Vertex[] rightResult = closestPairHelper(rightSubarrayX, rightSubarrayY);

		double minimumResult = Math.min(leftResult[0].getDistance(leftResult[1]),
				rightResult[0].getDistance(rightResult[1]));

		if (minimumResult == leftResult[0].getDistance(leftResult[1])) {
			result = leftResult;
		} else {
			result = rightResult;
		}

		// Create array of points within 2*minimumDistance of vertical line
		ArrayList<Vertex> pointsInStrip = new ArrayList<>();

		for (int i = 0; i < pointsYSorted.size(); i++) {
			if (pointsYSorted.get(i).getDistance(pointsXSorted.get(midpoint)) < minimumResult) {
				pointsInStrip.add(pointsYSorted.get(i));
			}
		}

		// Compare the distances between the points
		for (int i = 0; i < pointsInStrip.size(); i++) {
			for (int j = 0; j < pointsInStrip.size(); j++) {
				if (i == j) {
					continue;
				}
				if (pointsInStrip.get(i).getDistance(pointsInStrip.get(j)) < minimumResult) {
					minimumResult = pointsInStrip.get(i).getDistance(pointsInStrip.get(j));
					result[0] = pointsInStrip.get(i);
					result[1] = pointsInStrip.get(j);
				}
			}
		}

		return result;
	}

	// finds a minimum spanning tree using kruskal greedy algorithm
	// and returns the path to achieve that. Use Edge._nEdgeCost
	// attribute in finding the min span tree [30 pts]
	public Vector<PathSegment> minSpanningTree() throws GraphException {
		Vector<PathSegment> mst = new Vector<>();
		DisjointSet dsu = new DisjointSet(_arrVertices.size());
		ArrayList<Edge> edges = new ArrayList<>();
		for (Edge e : _arrEdges)
			edges.add(e);
		Collections.sort(edges, (a, b) -> a.getCost() - b.getCost());
		for (Edge e : edges) {
			int u = e._verFirstVertex.getIdx(), v = e._verSecondVertex.getIdx();
			if (dsu.union(u, v)) {
				mst.add(new PathSegment(null, e));
			}
		}

		return mst;
	}

	// finds shortest paths using bellman ford dynamic programming
	// algorithm and returns all such paths starting from given
	// vertex. Use Edge._nEdgeCost attribute in finding the
	// shortest path [35 pts]
	public Vector<Vector<PathSegment>> findShortestPathBF(String strStartVertexUniqueID) throws GraphException {
		int V = _arrVertices.size();
		int INF = (int) 1e9;
		int[] dist = new int[V];
		Arrays.fill(dist, INF);
		int s = -1;
		for (int i = 0; i < V; i++)
			if (_arrVertices.get(i)._strUniqueID.toString().equals(strStartVertexUniqueID)) {
				s = i;
				break;
			}
		
		if (s == -1) {
			throw new GraphException("Vertex not found!");
		}
		
		dist[s] = 0;
		// relax all E edges V-1 times
		Edge[] parent = new Edge[V];
		Vector<Vector<PathSegment>> segments = new Vector<>();
		for (int i = 0; i < V - 1; i++)
			for (int u = 0; u < V; u++)
				for (AdjacentVertexNode adjN : _arrVertices.get(u)._lstAdjacencyList) {
					int v = adjN.AdjacentVertex.getIdx();
					int newCost = dist[u] + adjN.ConnectingEdge.getCost();
					if (newCost < dist[v]) {
						dist[v] = newCost;
						parent[v] = adjN.ConnectingEdge;
					}
				}
		for (int i = 0; i < V; i++)
			segments.add(new Vector<>());
		segments.get(s).add(new PathSegment(_arrVertices.get(s), null));
		for (int i = 0; i < V; i++) {
			buildBFPath(i, parent, segments);
		}
		return segments;
	}

	private void buildBFPath(int u, Edge[] parent, Vector<Vector<PathSegment>> segments) {
		if (segments.get(u).size() != 0)
			return;
		Edge e = parent[u];
		if (e == null)
			return;
		Vector<PathSegment> curr = segments.get(u);
		int v = e._verFirstVertex.getIdx();
		if (e._verFirstVertex == _arrVertices.get(u)) {
			v = e._verSecondVertex.getIdx();
		}
		buildBFPath(v, parent, segments);
		for (PathSegment ps : segments.get(v)) {
			curr.add(ps);
		}
		curr.remove(curr.size() - 1);
		curr.add(new PathSegment(_arrVertices.get(v), e));
		curr.add(new PathSegment(_arrVertices.get(u), null));
	}

	// finds all shortest paths using Floyd�Warshall dynamic
	// programming algorithm and returns all such paths. Use
	// Edge._nEdgeCost attribute in finding the shortest path
	// [35 pts]
	@SuppressWarnings("unchecked")
	public Vector<Vector<PathSegment>> findAllShortestPathsFW() throws GraphException {
		int V = _arrVertices.size();
		int[][] dist = new int[V][V];
		int INF = (int) 1e9;
		for (int[] a : dist)
			Arrays.fill(a, INF);
		Edge[][] parent = new Edge[V][V];
		for (Edge e : _arrEdges) {
			int u = e._verFirstVertex.getIdx(), v = e._verSecondVertex.getIdx();
			int cost = e.getCost();
			if (cost < dist[u][v]) {
				parent[u][v] = e;
				dist[u][v] = cost;
				dist[v][u] = cost;
				parent[v][u] = e;
			}
		}
		for (int i = 0; i < V; i++)
			dist[i][i] = 0;
		for (int k = 0; k < V; k++)
			for (int i = 0; i < V; i++)
				for (int j = 0; j < V; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						parent[i][j] = parent[k][j];
					}
				}
		Vector<Vector<PathSegment>> segments = new Vector<>();
		Vector<PathSegment>[][] paths = new Vector[V][V];
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				paths[i][j] = new Vector<>();
			}
		}
		for (int i = 0; i < V; i++)
			for (int j = 0; j < V; j++) {
				buildFWPath(i, j, parent, paths);
				segments.add(paths[i][j]);
			}

		return segments;
	}

	private void buildFWPath(int u, int v, Edge[][] parent, Vector<PathSegment>[][] segments) {
		if (segments[u][v].size() != 0)
			return;
		if (u == v) {
			segments[u][v].add(new PathSegment(_arrVertices.get(u), null));
			return;
		}
		Edge e = parent[u][v];
		if (e == null)
			return;
		Vector<PathSegment> curr = segments[u][v];
		int k = e._verFirstVertex.getIdx();
		if (e._verFirstVertex == _arrVertices.get(v)) {
			k = e._verSecondVertex.getIdx();
		}
		buildFWPath(u, k, parent, segments);
		for (PathSegment ps : segments[u][k]) {
			curr.add(ps);
		}
		curr.remove(curr.size() - 1);
		curr.add(new PathSegment(_arrVertices.get(k), e));
		curr.add(new PathSegment(_arrVertices.get(v), null));
	}

}
