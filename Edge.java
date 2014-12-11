// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// Edge.java
// *****************************


public class Edge implements Comparable<Edge> {

		public Vertex thisOne; 
		public Vertex adjacent; 
		public double distance; 

		public Edge(Vertex a, double d) {
			adjacent = a; 
			distance = d; 
		}
		public Edge(Vertex a, Vertex b, double d) {
			thisOne = a; 
			adjacent = b; 
			distance = d; 
		}

		public int compareTo(Edge other) {
			if (this.distance < other.distance) {
				return -1; 
			} else if (this.distance == other.distance) {
				return 0; 
			} else { 
				return 1; }
		}

		public Vertex getu() {
			return thisOne;
		}

		public Vertex getv() {
			return adjacent;
		}

	}