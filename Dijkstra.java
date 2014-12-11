// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// Dijkstra.java
// implements Dijkstra's algorithm on city vertices 
// *****************************


import java.util.*; 

public class Dijkstra {

	public static HashMap<String, Vertex> cities; 
	public static BinaryHeap<Vertex> distances; 

	public Dijkstra() {

		cities = new HashMap<String, Vertex>(); 
		distances = new BinaryHeap<Vertex>(); 

	}

	public static void doDijkstra(String sourceName) throws CityNotFoundException {
		resetDistances(); // clear previous path distances if there are any
		if (!cities.containsKey(sourceName)) {
			throw new CityNotFoundException(
				"invalid city name: " + sourceName); 
		} else {
			Vertex source = cities.get(sourceName); 
			source.pathDistance = 0; 
			distances.insert(source);

			while (!allKnown()) {

				Vertex v = distances.deleteMin(); 
				v.known = true; 

				for (Edge e : v.adjacents) {
					Vertex w = e.adjacent; 
					if (!w.known) {
						double vwdistance = e.distance; 
						if (v.pathDistance + vwdistance < w.pathDistance) {
							// update w 
							w.pathDistance = v.pathDistance + vwdistance; 
							w.path = v; 
							distances.insert(w); 
						}
					}
				}

			}

		}
	}

	public void getRoute(String destinationName) 
	throws CityNotFoundException {
		if (!cities.containsKey(destinationName)) {
			throw new CityNotFoundException(
				"invalid city name: " + destinationName); 
		} else {
			Vertex destination = cities.get(destinationName); 
			printPath(destination); 
			System.out.println("\ntotal distance: " + destination.pathDistance); 
		}
	}

	void printPath(Vertex v) { 
		if (v.path != null) {
			printPath(v.path); 
			System.out.print( " to " ); 
		} 
		System.out.print(v.name); 
	} 

	public static void resetDistances() {
		// reset path distances of all vertices in hashmap
		Collection<Vertex> c = cities.values(); 
		for (Vertex v : c) {
			v.pathDistance = Double.POSITIVE_INFINITY; 
			v.path = null;
			v.known = false; 
		}
	}

	private static boolean allKnown() {
		// checks if all the vertices have been marked as known 
		Collection<Vertex> c = cities.values(); 
		for (Vertex v : c) {
			if (!v.known) {
				return false; 
			}
		}
		return true;
	}

	public void readDistance(String s) {
		// takes a string formatted like "city1name city2name distance"
		String[] data = s.split(" "); 
		String city1name = data[0]; 
		String city2name = data[1]; 
		double distance = Double.parseDouble(data[2]);

		Vertex city1; Vertex city2; 
		// check if vertices already created and add/get to cities as needed
		if (!cities.containsKey(city1name)) {
			city1 = new Vertex(city1name); 
			distances.insert(city1);
			cities.put(city1name, city1); 
		} else { 
			city1 = cities.get(city1name); } 
		if (!cities.containsKey(city2name)) {
			city2 = new Vertex(city2name); 
			distances.insert(city2);
			cities.put(city2name, city2); 
		} else {
			city2 = cities.get(city2name); }

		// make edges (one in each direction) and attach 
		makeEdges(city1, city2, distance); 

	}

	private void makeEdges(Vertex city1, Vertex city2, double distance) {
		Edge e1 = new Edge(city2, distance); 
		city1.addAdjacent(e1); 
		Edge e2 = new Edge(city1, distance); 
		city2.addAdjacent(e2);
	}

	public void addCoordinates(String s) {
		// takes a string formatted like "cityname x-value y-value" 
		String[] data = s.split("\\s+"); 
		String cityName = data[0]; 
		double x = Double.parseDouble(data[1]); 
		double y = Double.parseDouble(data[2]); 

		if (cities.containsKey(cityName)) {
			Vertex v = cities.get(cityName); 
			v.x = x; v.y = y; 
		}
	}

}