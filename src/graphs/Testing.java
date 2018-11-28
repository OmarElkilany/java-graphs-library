package graphs;

public class Testing {
	
	public static void main(String[]args) throws GraphException{
		Graph testGraph = new Graph();
		
		// {2, 3}, {12, 30}, {40, 50}, {5, 1}, {12, 10}, {3, 4}
		testGraph.insertVertex("A", "AAA", 1, 1);
		testGraph.insertVertex("B", "BAA", 5, 5);
		testGraph.insertVertex("C", "CAA", 6, 6);
		testGraph.insertVertex("D", "DAA", 10, 10);
		testGraph.insertVertex("E", "EAA", 12, 10);
		//testGraph.insertVertex("F", "FAA", 3, 4);
		
		Vertex[] closePair = testGraph.closestPair();
		
		System.out.println(closePair[0].getUniqueID().toString());
		System.out.println(closePair[1].getUniqueID().toString());
	}
}
