import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class GC20141006_Square {
	static int S_x, S_y, T_x, T_y;
	static int Max_x;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanner;
		
		System.out.println("input a number");
		String firstLine = br.readLine();
		scanner = new Scanner(firstLine);
		S_x = Integer.parseInt(scanner.next());
		S_y = Integer.parseInt(scanner.next());
		String secondLine = br.readLine();
		scanner = new Scanner(secondLine);
		T_x = Integer.parseInt(scanner.next());
		T_y = Integer.parseInt(scanner.next());
//		System.out.println("S_x" + S_x + S_y + T_x + T_y);
		
		
		
//		String s = "Hello World! 3 + 3.0 = 6 ";
//		// create a new scanner with the specified String Object
//		Scanner scanner = new Scanner(s);
//		// find the next token and print it
//		System.out.println("" + scanner.next());
//		// find the next token and print it
//		System.out.println("" + scanner.next());
//		// close the scanner
//		scanner.close();
		
		
	}
	
	
	
	// BFS search from tree. Nuts Position will not contains the first nut that grabed by the squiral
	public int bfs(Node root, int numberOfNuts, int Max_x, int Max_y, Hashtable<Integer, Integer> nutPosition)
    {
        //Since queue is a interface
		if (numberOfNuts == 0) {
			return 0;
		}
		
        Queue<Node> queue = new LinkedList<Node>();
        int numberOfNutsFound = 0;
        int totalSteps = 0;
        
         //Adds to end of queue
        root.step = 0;
        queue.add(root);
        while(!queue.isEmpty() && numberOfNutsFound < numberOfNuts)
        {
            //removes from front of queue
            Node r = queue.remove(); 
            int R_x = r.x;
            int R_y = r.y;
            
            if(nutPosition.get(R_x) == R_y){
            	numberOfNutsFound++;
            	totalSteps += r.step; 
            }
            
            // Add neighbors
            Node newNode;
            if (r.x < Max_x){
            	newNode = new Node(R_x+1, R_y);
            }
            if (r.y < Max_y){
            	newNode = new Node(R_x, R_y+1);
            }
            if (r.x > 0){
            	newNode = new Node(R_x-1, R_y);
            }
            if (r.y > 0){
            	newNode = new Node(R_x, R_y-1);
            }
            newNode.step = r.step+1;
        	queue.add(newNode);
        }
        return totalSteps;
    }
}

class Node {

    public int x;
    public int y;
    public boolean visited;
    public int step;

    public Node(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
