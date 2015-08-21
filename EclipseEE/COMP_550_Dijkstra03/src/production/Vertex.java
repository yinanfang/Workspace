package production;

import java.util.*;

public class Vertex implements Comparable<Vertex> {

	private Integer id;
	private Integer position_x;
	private Integer position_y;
	private Float distance; // Distance from the source
	private Map<Integer, Float> neighbor = new HashMap<Integer, Float>();

	public Vertex(Integer id, Integer pos_x, Integer pos_y) {
		this.id = id;
		this.position_x = pos_x;
		this.position_y = pos_y;
	}
	
	public Vertex(Integer id, Float distance) {
		this.id = id;
		this.distance = distance;
	}

	@Override
	public int compareTo(Vertex node) {
		if (this.distance < node.getDistance()) {
			return -1;
		}else if (this.distance == node.getDistance()) {
			return 0;
		}else{
			return 1;
		}
	}
	
	public String toString() {
		System.out.println("Node #" + id + " info dump: ");
		System.out.println(">>>Vertex info: id="
				+ id + ", distance=" + distance + ", position_x="
				+ position_x + ", position_y=" + position_y + ", neighbot= " + neighbor);
		return null;
	}

	// Getters Setup
	public Integer getId() {
		return this.id;
	}

	public Integer getPosition_x() {
		return this.position_x;
	}

	public Integer getPosition_y() {
		return this.position_y;
	}

	public Float getDistance() {
		return this.distance;
	}
	public void setDistance(Float distance) {
		 this.distance = distance;
	}

	public Map<Integer, Float> getNeighbor() {
		return this.neighbor;
	}

}
