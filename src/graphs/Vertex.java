package graphs;

import java.util.LinkedList;

public class Vertex {
	protected StringBuffer _strUniqueID, // a unique id identifying vertex
						   _strData; 	 // data associated with vertex
	protected int _nX, _nY; // Coordinates of vertex on some map. Assume 0,0 is bottom left.
	
	protected LinkedList<AdjacentVertexNode> _lstAdjacencyList;
	
	private String color, predecessorID; // variables for DFS
	
	public Vertex(String vertexID, String vertexData, int X, int Y){
		_strUniqueID = new StringBuffer(vertexID);
		_strData = new StringBuffer(vertexData);
		
		_nX = X;
		_nY = Y;
	}
	
	public StringBuffer getUniqueID() {
		return _strUniqueID;
	}

	public StringBuffer getData() {
		return _strData;
	}

	public int getX() {
		return _nX;
	}

	public int getY() {
		return _nY;
	}
	
	public LinkedList<AdjacentVertexNode> getAdjacencyList(){
		return _lstAdjacencyList;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPredecessorID() {
		return predecessorID;
	}

	public void setPredecessorID(String predecessorID) {
		this.predecessorID = predecessorID;
	}
	
}
