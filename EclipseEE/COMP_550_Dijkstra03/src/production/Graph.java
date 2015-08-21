package production;

import java.util.*;
import java.util.Map.Entry;
import java.util.PriorityQueue;

public class Graph {
	private Integer num_node;
	private Integer num_edge;
	private Map<Integer, Vertex> nodes = new HashMap<Integer, Vertex>();
	private Map<Integer, Float> distance_graph;
	private Map<Integer, Integer> previous;
	private PriorityQueue<Vertex> queue;
	private String path;

	public Graph(Integer nodes, Integer edges) {
		this.num_node = nodes;
		this.num_edge = edges;
	}

	public void addNode(Integer id, Vertex node) {
		nodes.put(id, node);
	}

	// add the neighbor into the HashMap
	public void addNeighbor(Integer id01, Integer id02) {
		Float distance_x = (float) Math.pow(
				(nodes.get(id01).getPosition_x() - nodes.get(id02)
						.getPosition_x()), 2);
		Float distance_y = (float) Math.pow(
				(nodes.get(id01).getPosition_y() - nodes.get(id02)
						.getPosition_y()), 2);
		Float distance = (float) Math.sqrt(distance_x + distance_y);
		nodes.get(id01).getNeighbor().put(id02, distance);
		nodes.get(id02).getNeighbor().put(id01, distance);
	}
	
	public void runDijkstra(Integer from, Integer destination) {
		resetVariables();
		
		for (Entry<Integer, Vertex> entry : nodes.entrySet()) {
			Vertex node_item = entry.getValue();
			if (node_item.getId().equals(from)) {
				node_item.setDistance((float) 0);
				previous.put(from, null);
				queue.add(node_item);
				distance_graph.put(from, (float) 0);
			}else {
				node_item.setDistance(Float.MAX_VALUE);
				distance_graph.put(node_item.getId(), Float.MAX_VALUE);
				queue.add(node_item);
			}
		}
		System.out.println("\n>>>>> Finished Initialization......");
		
		System.out.println("\n>>>>> Start Dijkstra loop ++++++++++\n++++++++++++++++++++++++++++++++++++++++\n++++++++++++++++++++++++++++++++++++++++");
		while (!queue.isEmpty()){
			Vertex node_min = queue.poll();
			System.out.println("Obtained node_min with id: #" + node_min.getId() + "with distance: " + node_min.getDistance());
			if (!node_min.getId().equals(destination)) {      // distance local map! neighbor field
//				System.out.println(">>> Processing node #" + node_min.getId() + "...");
				for (Integer neighbor_Id : node_min.getNeighbor().keySet()) {
					Float distance_new = distance_graph.get(node_min.getId()) + node_min.getNeighbor().get(neighbor_Id);
//					System.out.println("The distance_new to node #" + neighbor_Id + ">> " + distance_new + "; Original distance: " + distance_graph.get(neighbor_Id));
					// Relax
					if (distance_new < distance_graph.get(neighbor_Id)){
//						System.out.println(">>> Updating node #"+neighbor_Id+" in distance_graph...");
						distance_graph.put(neighbor_Id, distance_new);
						previous.put(neighbor_Id, node_min.getId());
						for(Vertex vertex : queue) {
							if (vertex.getId() == neighbor_Id) {
								queue.remove(vertex);
								vertex.setDistance(distance_new);
								queue.add(vertex);
								break;
							}
						}
					}
				}
			}else{
				System.out.println("Reached the destination...");
				break;
				
			}
			
		}
		outputResult(from, destination);
	}

	public void outputResult(Integer from, Integer destination){
		System.out.println(">>>>>>>>>>>>>>>>>>>> Dijkstra Result:");
		System.out.println(">>>>>>>>>>>>>>>>>>>> Distance from point #"+from+" to #"+destination+" is: "+distance_graph.get(destination));
		path = "[" + destination + "]";
		Integer previous_id = destination;
		for(int i=0; true; i++){
			System.out.println("previous_id: >> " + previous_id);
			previous_id = previous.get(previous_id);
			if (previous_id != null){
				path = "[" + previous_id + "] => " + path;
			}else{
				break;
			}
		}
		System.out.println(path);

	}
	
	public void resetVariables(){
		System.out.println("Initializing and resetting Graph variables......");
		distance_graph = new HashMap<Integer, Float>();
		previous = new HashMap<Integer, Integer>();
		queue = new PriorityQueue<Vertex>();
		path = "";
	}
	
	public void checkCurrentQueueAndGraph(){
		System.out.println(">>>>> Checking Current Queue and Graph <<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(">>>>> The Queue:");
		for(Vertex vertex : queue) {
			System.out.println("Node #: " + vertex.getId() + "'s distance: " + vertex.getDistance());
		}
		System.out.println(">>>>> The distance_graph Map:");
		for(Integer key : distance_graph.keySet()) {
			System.out.println("Dijkstra's node #" + key + "'s distance: " + distance_graph.get(key));
		}
		System.out.println(">>>>> The previous Map:");
		for(Integer key : previous.keySet()) {
			System.out.println("#" + key + "'s previous node is: " + previous.get(key));
		}
		
		
		System.out.println(">>>>> Finished Checking...");
	}
	
	// toString method to print out the map
	public String toString() {
		for (Entry<Integer, Vertex> entry : nodes.entrySet()) {
			entry.toString();
		}
		return null;
	}

	// Getters Setup
	public Integer getId() {
		return this.num_node;
	}
	
	public Map<Integer, Integer> getPrevious() {
		return this.previous;
	}

	public Integer getNumOfNode() {
		return this.num_node;
	}
	
	public Integer getNumOfEdge() {
		return this.num_edge;
	}

	public Vertex getNodeAtIndex(Integer index) {
		return this.nodes.get(index);
	}

	public PriorityQueue<Vertex> getNeighbor() {
		return this.queue;
	}
}
