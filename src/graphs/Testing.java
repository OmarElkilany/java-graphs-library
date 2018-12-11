package graphs;

import java.util.Arrays;
import java.util.Vector;

public class Testing {

	public static void main(String[] args) throws GraphException, InterruptedException {
		System.out.println("testing of the insertions, deletions, end and opposite");
		runTestCase0();
		System.out.println();
		System.out.println();
		System.out.println("testing dfs");
		runTestCase1();
		System.out.println();
		System.out.println();
		System.out.println("testing bfs");
		runTestCase2();
		System.out.println();
		System.out.println();
		System.out.println("testing pathDFS");
		runTestCase3();
		System.out.println();
		System.out.println();
		System.out.println("testing closest pair");
		runTestCase4();
		System.out.println();
		System.out.println();
		System.out.println("testing MST");
		runTestCase5();
		System.out.println();
		System.out.println();
		System.out.println("testing BF");
		runTestCase6();
		System.out.println();
		System.out.println();
		System.out.println("testing FW");
		runTestCase7();
		
	}

	// tests the graph insertions, deletions, and so
	static void runTestCase0() throws GraphException, InterruptedException {
		Graph g = new Graph();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 0, 0);
		g.insertVertex("3", "3", 0, 0);
		g.insertVertex("4", "4", 0, 0);
		g.insertVertex("5", "5", 0, 0);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		g.insertEdge("4", "5", "58", "58", 58);
		g.insertEdge("3", "5", "34", "34", 34);
		System.out.println("EDGES");
		System.out.println(g.edges().toString());
		System.out.println("Verticies");
		System.out.println(g.vertices().toString());
		g.removeEdge("34");
		System.out.println("EDGES after removing edge 34");
		System.out.println(g.edges().toString());
		g.removeVertex("3");
		System.out.println("EDGES and VERTICIES after removing vertex 3");
		System.out.println(g.edges().toString());
		System.out.println(g.vertices().toString());
		try {
			System.out.println(g.endVertices("34"));
		} catch (Exception e) {
			System.out.println(e.getMessage()+" and That is correct 34 was deleted");
		}
		System.out.println("the edge 58 connects "+Arrays.toString(g.endVertices("58")));
		System.out.println("vertex opposite to 1 in the edge 88 is "+g.opposite("1", "88"));
	}

	// tests DFS
	static void runTestCase1() throws GraphException {
		Graph g = new Graph();
		GradingVisitor gVisitor = new GradingVisitor();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 0, 0);
		g.insertVertex("3", "3", 0, 0);
		g.insertVertex("4", "4", 0, 0);
		g.insertVertex("5", "5", 0, 0);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		g.insertEdge("4", "5", "58", "58", 58);
		g.insertEdge("3", "5", "34", "34", 34);
		g.dfs("1", gVisitor);
		System.out.println(gVisitor.getResult());

	}

	// tests BFS
	static void runTestCase2() throws GraphException {
		Graph g = new Graph();
		GradingVisitor gVisitor = new GradingVisitor();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 0, 0);
		g.insertVertex("3", "3", 0, 0);
		g.insertVertex("4", "4", 0, 0);
		g.insertVertex("5", "5", 0, 0);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		g.insertEdge("4", "5", "58", "58", 58);
		g.insertEdge("3", "5", "34", "34", 34);
		g.bfs("2", gVisitor);
		System.out.println(gVisitor.getResult());

	}

	// tests pathDFS
	static void runTestCase3() throws GraphException {
		Graph g = new Graph();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 0, 0);
		g.insertVertex("3", "3", 0, 0);
		g.insertVertex("4", "4", 0, 0);
		g.insertVertex("5", "5", 0, 0);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		g.insertEdge("4", "5", "58", "58", 58);
		g.insertEdge("3", "5", "34", "34", 34);
		Vector<PathSegment> path = g.pathDFS("1", "5");
		for (PathSegment ps : path) {
			System.out.print(ps);
		}
		if (path.size() == 0) {
			System.out.println("no path found between them");
		}
		System.out.println();

	}

	// tests Closest Pair
	static void runTestCase4() throws GraphException {
		Graph testGraph = new Graph();

		testGraph.insertVertex("A", "AAA", 1, 1);
		testGraph.insertVertex("B", "BAA", 5, 5);
		testGraph.insertVertex("C", "CAA", 1, 7);
		testGraph.insertVertex("D", "DAA", 10, 20);
		testGraph.insertVertex("E", "EAA", 12, 10);
		testGraph.insertVertex("F", "FAA", 1, 4);

		Vertex[] closePair = testGraph.closestPair();

		System.out.println(closePair[0].getUniqueID().toString());
		System.out.println(closePair[1].getUniqueID().toString());
	}
	static void runTestCase5() throws GraphException {
		Graph g = new Graph();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 0, 0);
		g.insertVertex("3", "3", 0, 0);
		g.insertVertex("4", "4", 0, 0);
		g.insertVertex("5", "5", 0, 0);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		g.insertEdge("4", "5", "58", "58", 58);
		g.insertEdge("3", "5", "34", "34", 34);
		Vector<PathSegment> seg= g.minSpanningTree();

		System.out.println(seg);
	}
	static void runTestCase6() throws GraphException {
		Graph g = new Graph();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 0, 0);
		g.insertVertex("3", "3", 0, 0);
		g.insertVertex("4", "4", 0, 0);
		g.insertVertex("5", "5", 0, 0);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		g.insertEdge("4", "5", "58", "58", 58);
		g.insertEdge("3", "5", "34", "34", 34);
		Vector<Vector<PathSegment>> seg= g.findShortestPathBF("5");
		for(Vector<PathSegment>v: seg) {
			for(PathSegment p: v) {
				System.out.print(p);
			}
			System.out.println();
		}
	}
	static void runTestCase7() throws GraphException {
		Graph g = new Graph();
		g.insertVertex("1", "1", 0, 0);
		g.insertVertex("2", "2", 0, 0);
		g.insertVertex("3", "3", 0, 0);
		g.insertVertex("4", "4", 0, 0);
		g.insertVertex("5", "5", 0, 0);
		g.insertEdge("1", "4", "88", "88", 5);
		g.insertEdge("1", "2", "2", "2", 2);
		g.insertEdge("2", "3", "14", "14", 14);
		g.insertEdge("2", "4", "99", "99", 5);
		g.insertEdge("2", "5", "4", "4", 4);
		g.insertEdge("4", "5", "58", "58", 58);
		g.insertEdge("3", "5", "34", "34", 34);
		Vector<Vector<PathSegment>> seg= g.findAllShortestPathsFW();
		for(Vector<PathSegment>v: seg) {
			for(PathSegment p: v) {
				System.out.print(p);
			}
			System.out.println();
		}
	}



	static class GradingVisitor implements Visitor {
		protected String _strResult = new String();

		public void visit(Vertex v) {
			_strResult += "v=" + v.getUniqueID() + " ";
		}

		public void visit(Edge e) {
			_strResult += "e=" + e.getUniqueID() + " ";
		}

		public String getResult() {
			return _strResult;
		}
	}
}
