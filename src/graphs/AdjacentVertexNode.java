package graphs;

public class AdjacentVertexNode {
	protected Vertex AdjacentVertex;
	protected Edge ConnectingEdge;
	
	public AdjacentVertexNode(Vertex vertex, Edge edge){
		AdjacentVertex = vertex;
		ConnectingEdge = edge;
	}
	
	public Edge getConnectingEdge(){
		return ConnectingEdge;
	}
	
	public Vertex getAdjacentVertex(){
		return AdjacentVertex;
	}
}
