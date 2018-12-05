package graphs;

public class DisjointSet {

	int[] parent, rank;

	public DisjointSet(int n) {
		parent = new int[n];
		rank = new int[n];
		for (int i = 0; i < n; i++)
			parent[i] = i;
	}

	public int findSet(int x) {
		return parent[x] == x ? x : (parent[x] = findSet(parent[x]));
	}

	public boolean union(int x, int y) {
		x = findSet(x);
		y = findSet(y);
		if (x == y)
			return false;
		if (rank[x] > rank[y]) {
			parent[y] = x;
		} else {
			parent[x] = y;
			if (rank[x] == rank[y])
				rank[y]++;
		}
		return true;
	}
}
