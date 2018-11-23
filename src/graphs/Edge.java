package graphs;

public class Edge {
	protected StringBuffer _strUniqueID, // a unique id identifying edge
						   
						   _strData; 	 // data associated with this edge.
										 // Data could be name of edge or
										 // any meaningful property for
										 // an edge.
	
	protected int _nEdgeCost; // cost of traversing this edge
	
	public Vertex _verFirstVertex;
	public Vertex _verSecondVertex;

	public Edge(String ID, String EdgeData, int Cost, Vertex first, Vertex second){
		_strUniqueID = new StringBuffer(ID);
		_strData = new StringBuffer(EdgeData);
		_nEdgeCost = Cost;
		
		_verFirstVertex = first;
		_verSecondVertex = second;
	}
	
	public StringBuffer getUniqueID() {
		return _strUniqueID;
	}

	public StringBuffer getData() {
		return _strData;
	}

	public int getCost() {
		return _nEdgeCost;
	}
}