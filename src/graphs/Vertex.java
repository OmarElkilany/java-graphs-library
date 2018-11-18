package graphs;

public class Vertex {
	protected StringBuffer _strUniqueID, // a unique id identifying vertex
			_strData; // data associated with vertex
	protected int _nX, _nY; // Coordinates of vertex on some map. Assume 0,0 is
							// bottom left.

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
}
