package graphs;

//the following class could be used as the building block of a path where a
//path consists of path segments and each path segment consist of a
//vertex and associated edge with it.
public class PathSegment {
	protected Vertex _vertex; // the vertex in this path segment
	protected Edge _edge; // the edge associated with this vertex

	public PathSegment(Vertex vertex, Edge edge) {
		this._vertex = vertex;
		this._edge = edge;
	}

	public PathSegment() {

	}

	public Vertex getVertex() {
		return _vertex;
	}

	public Edge getEdge() {
		return _edge;
	}

	public String toString() {
		return getVertex().getUniqueID().toString() + " "
				+ (getEdge() != null ? "--(" + getEdge().getUniqueID().toString() + ")--> " : "");
	}
}
