package production;

import java.io.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Production_Main {

	static FileProcess map_file = null;
	static Graph graph;
	static String file_line_str = null;

	public static void main(String[] args) {
		System.out.println("=============== Start Program ==============");
		run_Client();		
		System.out.println("=============== Program Terminated ==============");
	}

	static void run_Client() {	
		importFile();		
		makeGraph();	
		drawGraph();
		testSelectedVertices();
		testArbitraryGraph();
	}
	
	private static void drawGraph(){
		int minX = 0;
		int maxX = 10000;
		int minY = 0;
		int maxY = 6000;
		double aspectRatio = 1;
		
		try {
	            Display.setDisplayMode(new DisplayMode((int)(600*aspectRatio),600));
	            Display.create();
	        } catch (LWJGLException e) {
	            e.printStackTrace();
	            System.exit(0);
	        }
	        
	        GL11.glMatrixMode(GL11.GL_PROJECTION);
	        GL11.glLoadIdentity();
	        GL11.glOrtho(minX -200, maxX + 200, minY - 200, maxY + 200, 1, -1);
	        GL11.glMatrixMode(GL11.GL_MODELVIEW);
	        
	        while (!Display.isCloseRequested()) {
	            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);    
	            GL11.glColor3f(1.0f,0.0f,0.0f);
	            GL11.glBegin(GL11.GL_LINES);
	            for(int j = 0; j < graph.getNumOfNode(); j++){
	            	Vertex v = graph.getNodeAtIndex(j);
	            	for(Integer key : v.getNeighbor().keySet()) {
	            		Vertex neighbor = graph.getNodeAtIndex(key);
	            		GL11.glVertex2d(v.getPosition_x(), v.getPosition_y());
	            		GL11.glVertex2d(neighbor.getPosition_x(), neighbor.getPosition_y());
	        		}       
	            }
	            GL11.glEnd();
	            // For entire map
//	            GL11.glColor3f(1.0f,1.0f,0.0f);
//	            GL11.glBegin(GL11.GL_POINTS);
//	            for(int j = 0; j < graph.getNumOfNode(); j++){
//	            	Vertex v = graph.getNodeAtIndex(j);
//	            	GL11.glVertex2d(v.getPosition_x(), v.getPosition_y());         
//	            }
//	            GL11.glEnd();
	            
	            graph.runDijkstra(1, 999);
	            GL11.glColor3f(1.0f,1.0f,1.0f);
	            GL11.glBegin(GL11.GL_LINE_STRIP);
	            Integer previous_id = 99;
	            GL11.glVertex2d(graph.getNodeAtIndex(99).getPosition_x(), graph.getNodeAtIndex(99).getPosition_y());
	    		for(int i=0; true; i++){
	    			previous_id = graph.getPrevious().get(previous_id);
	    			if (previous_id != null){
	    				GL11.glVertex2d(graph.getNodeAtIndex(previous_id).getPosition_x(), graph.getNodeAtIndex(previous_id).getPosition_y());
	    			}else{
	    				break;
	    			}
	    		}
	            GL11.glEnd();
	            Display.update();
	        }
	        
	        Display.destroy();
	}
	
	private static void importFile () {
		try {
			map_file = new FileProcess("usa.txt", false);
			System.out.println("\n=====> File imported successfully <=====");
		} catch (FileNotFoundException e) {
			System.out.println("File Not Exist!");
			System.exit(0);
		}
		System.out.println("\n=====> File processed successfully <=====");
	}
	
	private static void makeGraph() {
		System.out.println("\n=====> Start making graph <=====");
		file_line_str = map_file.readLine();
		Integer num_vertex = Integer.parseInt(file_line_str.split("\\s+")[0]);
		Integer num_edge = Integer.parseInt(file_line_str.split("\\s+")[1]);
		graph = new Graph(num_vertex, num_edge);
		
		System.out.println("\nProcessing new nodes......");
		Integer id;
		Integer x_position;
		Integer y_position;
		while ((file_line_str = map_file.readLine()) != null ) {
			if (file_line_str.length() > 0){
				file_line_str = file_line_str.trim();
				id = Integer.parseInt(file_line_str.split("\\s+")[0]);
				x_position = Integer.parseInt(file_line_str.split("\\s+")[1]);
				y_position = Integer.parseInt(file_line_str.split("\\s+")[2]);
				Vertex newNode = new Vertex(id, x_position, y_position);
				graph.addNode(id, newNode);
			}else{
				break;
			}	
		}	
		System.out.println("\nAdding new neighbors......");
		Integer neighbor01;
		Integer neighbor02;
		while ((file_line_str = map_file.readLine()) != null ) {
			if (file_line_str.length() > 0){
				file_line_str = file_line_str.trim();
				neighbor01 = Integer.parseInt(file_line_str.split("\\s+")[0]);
				neighbor02 = Integer.parseInt(file_line_str.split("\\s+")[1]);
				graph.addNeighbor(neighbor01, neighbor02);	
			}else{
				break;
			}	
		}
		System.out.println("\nChecking graph entries......");
//		graph.toString();
		System.out.println("\n=====> Finished making graph <=====");

	}

	private static void testSelectedVertices() {
		System.out.println("\n=====> Start selected vertices test <=====");
		
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Testing vertex #0 and #5 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		graph.runDijkstra(0, 5);
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END Testing vertex #0 and #5 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Testing vertex #3 and #4 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		graph.runDijkstra(3, 4);
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END Testing vertex #3 and #4 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Testing vertex #1 and #5 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		graph.runDijkstra(1, 5);
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END Testing vertex #1 and #5 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Testing vertex #1 and #99 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		graph.runDijkstra(1, 99);
		System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END Testing vertex #1 and #99 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
		
		System.out.println("\n=====> End selected vertices test <=====");
	}

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int destination01, destination02;
	private static void testArbitraryGraph() {
		// Start testing user input
		while (true) {
		System.out.println("\n=====> Accepting Inputs <=====");
		try {
			System.out.println("Please enter Destination 01: ");
			destination01 = Integer.parseInt(br.readLine());
			System.out.println("Please enter Destination 02: ");
			destination02 = Integer.parseInt(br.readLine());

			System.out
					.println("Start calculating shortest path from Destination "
							+ destination01
							+ "to Destination "
							+ destination02 + " ......");
			
			graph.runDijkstra(destination01, destination02);
			
			
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.println("NumberFormatException ...!!!");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Other Errors ...!!!");
		}
	}
		
	}


}