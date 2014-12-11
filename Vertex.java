// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// Vertex.java
// *****************************

import java.util.*; 

public class Vertex implements Comparable<Vertex> {
		
		public ArrayList<Edge> adjacents; // i still don't know what this is lol 
		public boolean known; 
		public double pathDistance; 
		public Vertex path; 			// 
		public String name; 
		public double x; 
		public double y; 
		
		public Vertex(String n) { 

			adjacents = new ArrayList<Edge>(); 
			known = false; 
			pathDistance = Double.POSITIVE_INFINITY;
			path = null; 
			name = n; 
		}
		
		public void addAdjacent(Edge e) {
			adjacents.add(e); 
		}
		
		public int compareTo(Vertex other) {
			if (this.pathDistance < other.pathDistance) {
				return -1; 
			} else if (this.pathDistance == other.pathDistance) {
				return 0; 
			} else { 
				return 1; }
		}

	}