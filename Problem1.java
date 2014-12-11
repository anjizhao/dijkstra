// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// Problem1.java
// uses Dijkstra's algorithm to find shortest paths between cities
// *****************************

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*; 
import java.io.*; 
import java.awt.geom.*;

public class Problem1 {

	private static JFrame frame; 
	private static Dijkstra dijks; 
	private static JPanel graph; 
	private static Vertices vertices; 
	private static JTextField text1; 
	private static JTextField text2; 
	private static JLabel to; 
	private static JButton start; 
	private static String source; 
	private static String destination;
	private static JPanel top;  
	private static JPanel bottom; 
	private static JLabel message; 
	
	public static void main(String[] args) throws IOException {
		
		// File inFile1 = new File("citypairs.dat"); 
		// File inFile2 = new File("cityxy.txt"); 
		File inFile1 = new File(args[0]); 
		File inFile2 = new File(args[1]); 

		BufferedReader reader1 = new BufferedReader(
			new InputStreamReader(new FileInputStream(inFile1))); 

		BufferedReader reader2 = new BufferedReader(
			new InputStreamReader(new FileInputStream(inFile2))); 

		Dijkstra dijks = new Dijkstra(); 

		readDistances(inFile1, reader1, dijks); 
		addCoordinates(inFile2, reader2, dijks); 

		makeGUI(); 

		vertices = new Vertices(); 
		vertices.revalidate(); 
		vertices.repaint(); 

	}

	private static void makeGUI() {

		final int TEXTFIELD_WIDTH = 12; 

		frame = new JFrame(); 

		vertices = new Vertices(); 

		source = ""; 
		destination = ""; 

		text1 = new JTextField(TEXTFIELD_WIDTH); 
		text2 = new JTextField(TEXTFIELD_WIDTH); 
		to = new JLabel(" to "); 
		text1.setText("source city");
		text2.setText("destination"); 

		start = new JButton("go"); 
		start.addActionListener(new StartListener()); 

		message = new JLabel(); 
		message.setText("enter source and destination cities");

		bottom = new JPanel(); 
		bottom.add(text1); 
		bottom.add(to);
		bottom.add(text2); 
		bottom.add(start); 

		top = new JPanel();
		top.add(message); 

		frame.setLayout(new BorderLayout()); 
		frame.add(top, BorderLayout.NORTH);
		frame.add(vertices, BorderLayout.CENTER);
		frame.add(bottom, BorderLayout.SOUTH); 

		frame.setTitle("Dijkstra's Algorithm");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(1000,650);
		frame.setVisible(true);

	}

	private static void readDistances( 
	File inFile, BufferedReader reader, Dijkstra d) 
	throws IOException {
		String s = ""; 
		while ((s = reader.readLine()) != null) {
			d.readDistance(s); 
		}
	}

	private static void addCoordinates(
	File inFile, BufferedReader reader, Dijkstra d) 
	throws IOException {
		String s = ""; 
		while ((s = reader.readLine()) != null) {
			d.addCoordinates(s); 
		}
	}

	private static void updateSource(String s) {
		source = s; 
	}

	private static void updateDestination(String d) {
		destination = d; 
	}

	public static class Vertices extends JPanel {

		Collection<Vertex> c = dijks.cities.values(); 

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g; 

			for (Vertex v : c) {
				Ellipse2D circle2D = new Ellipse2D.Double(v.x/3, (490-v.y/3), 6, 6);
				g2.draw(circle2D);
				g2.drawString((String) v.name, (int) v.x/3, (int) (490-v.y/3)); 
			}

			// draw edges 
			Vertex v = dijks.cities.get(destination); 
			while (v != null && v.path != null) {
				g2.drawLine((int) v.x/3+3, (int) (493-v.y/3), (int) v.path.x/3+3, (int) (493 - v.path.y/3));  
				v = v.path; 
			} 
		} 

	}

	private static class StartListener implements ActionListener {

		public StartListener() {

		}

		public void actionPerformed(ActionEvent event) {
			message.setText(" "); 
			String s = text1.getText(); 
			String d = text2.getText(); 
			updateSource(s); 
			updateDestination(d); 
			try {
				dijks.doDijkstra(s); 
				if (!dijks.cities.containsKey(d)) {
					throw new CityNotFoundException(
						"invalid city name: " + d);
				}
				Vertices newGraph = new Vertices(); 
				vertices = newGraph; 
				message.setText("total distance: " + 
					dijks.cities.get(d).pathDistance); 
			} catch (CityNotFoundException e) {
				message.setText(e.getMessage()); 
			}
			message.revalidate();
			message.repaint();
			vertices.revalidate();
			vertices.repaint();
			frame.pack();
			frame.setSize(1000,650);
		}

	}

}