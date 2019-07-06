package com.p5_5.com;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class KriskalAlgorithm {
	static int length = 1000;

	public static void start(String file) {
		BufferedReader rd = null;
		// Since the initial size of file is not known till it is read we can
		// initialize it to larger value.we can avoid this by making use of an
		// ArrayList and iterating through it
		// we can also use for (int i = 2; i < b.length; i = i + 2) { here first
		// cell of input file is taken as source, the next as destination
		// followed by distance
		int rcount = 0;
		String[] u = new String[length];
		String[][] v = new String[length][length];
		int[] ccount = new int[length];
		int[][] cost = new int[length][length];
		
		try {

			rd = new BufferedReader(new FileReader(file));
			String row = "";
			while ((row = rd.readLine()) != null) {
				// the split is used to split the contents of file as they are
				// read
				String[] b = row.split(",");
				int size = b.length;

				// logic used to separate the costs(distances) from source and
				// destination

				ccount[rcount] = (size - 1) / 2;
				u[rcount] = b[0];
				for (int i = 1, j = 0; i < size; i++) {
					if (i % 2 == 1) {
						v[rcount][j] = b[i];

					}
					// as we need the cost(destination) as integer we do a
					// parseInt
					if (i % 2 == 0) {
						cost[rcount][j++] = Integer.parseInt(b[i]);

					}

				}

				rcount++;
			}
			int vertex = rcount; // Number of vertices in graph
			int edgecount = 0; // Number of edges in graph
			for (int l = 0; l <= vertex; l++)
				edgecount += ccount[l];

			ArrayList<KriskalAlgorithm.Edge> edge = new ArrayList<>();

			for (int i = 0; i < edgecount; i++) {
				for (int j = 0; j < ccount[i]; j++) {
					edge.add(new Edge(i, verify(i, j, v, u, rcount), cost[i][j]));
				}
			}
			ArrayList<KriskalAlgorithm.Edge> graph = new ArrayList<>();
			graph = kruskal(edge, vertex);
			int sum = 0;

			System.out
					.println("The various edges possible in the minimum spanning tree is");

			for (Edge s : graph) {

				// finding sum of all paths by adding individual costs
				sum = sum + s.distance;
				System.out.println(u[s.src] + "(Source)" + " to " + u[s.des]
						+ " (Destination)" + ", Distance is: " + s.distance);
			}
			System.out.println("Sum of Distances in the tree is=" + sum);

		}
		// Exception handling
		catch (IOException e1) {
			e1.printStackTrace();

		}

		finally {
			try {
				rd.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	private static int verify(int i, int j, String[][] v2, String[] u, int count) {

		int ret = 1;
		for (int k = 0; k < count; k++)
			if (v2[i][j].equals(u[k]))
				ret = k;
		return ret;
	}

	// Kruskal algorithm as per pseudocode given in the text book
	static ArrayList<Edge> kruskal(List<Edge> edges, int verticescount) {
		// Author's Disjoint set has been used here
		DisjSets ds = new DisjSets(verticescount);
		// A priority queue is used to form the minimum spanning tree and this
		// helps to select edges of lowest cost as per the algorithm.

		PriorityQueue<Edge> pq = new PriorityQueue<>(edges);
		ArrayList<Edge> tree = new ArrayList<>();

		while (tree.size() != verticescount - 1) {
			// poll has been used to remove element from top of queue
			Edge e = pq.poll();

			int uset = ds.find(e.getu());
			int vset = ds.find(e.getv());
			// checking if the graph becomes cyclic or not by adding the edge,
			// if not that edge is added to the tree
			if (uset != vset) {
				tree.add(e);
				ds.union(uset, vset);
			}
		}
		return tree;
	}

	static class Edge implements Comparable<Edge> {
		int src, des;
		int distance;

		public Edge(int src, int des, int cost) {
			this.src = src;
			this.des = des;
			this.distance = cost;
		}

		@Override
		// used to comapre the costs of all edges
		public int compareTo(Edge newEdge) {

			return Integer.valueOf(this.distance).compareTo(
					Integer.valueOf(newEdge.distance));
		}

		public int getu() {
			// TODO Auto-generated method stub
			return src;
		}

		public int getv() {
			// TODO Auto-generated method stub
			return des;
		}
	};

}