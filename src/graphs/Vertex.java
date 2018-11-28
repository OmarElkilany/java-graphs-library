package graphs;

import java.util.Comparator;
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
		_lstAdjacencyList = new LinkedList<>();
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

	
	// DFS Helper methods
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
	
	
	// Closest pair helper methods
	public double getDistance(Vertex vertex2){
		return Math.sqrt(Math.pow(this._nX - vertex2._nX, 2) + Math.pow(this._nY - vertex2._nY, 2));
	}
	
	public static Comparator<Vertex> sortbyX = new Comparator<Vertex>() {

		@Override
		public int compare(Vertex ver1, Vertex ver2) {
			return ver1._nX - ver2._nX;
		}
	};
	
	public static Comparator<Vertex> sortbyY = new Comparator<Vertex>() {

		@Override
		public int compare(Vertex ver1, Vertex ver2) {
			return ver1._nY - ver2._nY;
		}
	};
	public String toString() {
		return getUniqueID().toString();
	}
	
}
