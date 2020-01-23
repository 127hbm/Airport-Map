//
//  Name:    Huynh, Minh
//  Project: 4  
//  Due:     12/10/2019
//  Course:  cs-2400-01-f19
//
//   Description:
//          Implement a graph data structure for a practical application. 
//
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
public class AirportApp {
    public static void main (String[] args) {
        GraphInterface<Entry> mygraph = new DirectedGraph<>();
        try {
            Scanner data = new Scanner(new File("airports.csv"));
            while(data.hasNextLine()) {
                String[] tokens = data.nextLine().split(",");
                Entry airport1 = new Entry(tokens[0],tokens[1]);
                mygraph.addVertex(airport1);
            }
            data = new Scanner(new File("distances.csv"));
            while(data.hasNextLine()) {
                String[] tokens1 = data.nextLine().split(",");
                mygraph.addEdge(new Entry(tokens1[0]), new Entry(tokens1[1]), Double.parseDouble(tokens1[2]));
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        
        Stack<Entry> path = new Stack<>();        
        Scanner keyboard = new Scanner(System.in);         
        System.out.print("Command? ");
        String input = keyboard.nextLine(); // command
        while(!input.equals("E")) {
            if (input.equals("Q")) { // prompt for information
                System.out.print("Airport code: ");
                String input1 = keyboard.nextLine(); // airport code
                Entry airport2 = new Entry(input1);
                Entry airport = mygraph.getVertex(airport2);
                if (airport != null)
                    System.out.println(airport.getInformation());
                else 
                    System.out.println("The given code does not exist.");
            }
            if (input.equals("D")) { // prompt for the minimum distance. Assume that each airport has at least one way to get to.
                System.out.print("Airport codes: ");
                String[] inputs = keyboard.nextLine().split(" ");
                String input1 = inputs[0];
                String input2 = inputs[1];              
                Entry begin = mygraph.getVertex(new Entry(input1));
                Entry end  = mygraph.getVertex(new Entry(input2));
                if (begin != null && end != null) { // the graph has two vertices
                    double path3 = mygraph.getCheapestPath(begin, end, path);

                    System.out.println("The minimum distance between " + begin.getInformation() + " and " + end.getInformation() + " is " + path3 + " through the route:" );
                    ArrayList<Entry> route = new ArrayList<>();
                    while (!path.empty()) {
                        route.add(path.pop()); 
                    } 
                    Entry[] route1 = new Entry[route.size()];
                    route.toArray(route1);
                    for (int i = 1; i < route1.length - 1; i++) {
                        System.out.println(route1[i].getInformation());
                    }
                }                
                else {
                    System.out.println("The airport codes are not legal.");
                }
            }// end if  
            if (input.equals("I")) { // insert new connection
                System.out.print("Aiport codes and distance: ");
                String[] inputs = keyboard.nextLine().split(" ");
                String input1 = inputs[0];
                String input2 = inputs[1];  
                Entry begin = mygraph.getVertex(new Entry(input1));
                Entry end  = mygraph.getVertex(new Entry(input2));
                if (begin == null || end == null) {
                    System.out.println("The airport codes are invalid.");
                }                
                else if (mygraph.hasEdge(begin, end)) { // if the edge already exists, do nothing
                    System.out.println("The connection already exists.");
                }
                else {
                    double distance = Double.parseDouble(inputs[2]);
                    mygraph.addEdge(begin, end, distance);
                    System.out.println("You have inserted a connection from " + begin.getInformation() + " to " + end.getInformation() + " with a distance of " + distance + ".");
                }// end else
            }// end if
            if (input.equals("R")) {
                System.out.print("Airport codes: ");
                String[] codes = keyboard.nextLine().split(" ");
                String input1 = codes[0];
                String input2 = codes[1];
                Entry begin = mygraph.getVertex(new Entry(input1));
                Entry end  = mygraph.getVertex(new Entry(input2));
                if (!mygraph.hasEdge(begin, end)) { // the edge does not exist
                    System.out.println("The connection does not exist.");
                }
                else {
                    mygraph.addEdge(begin, end, 0); // remove the edge from begin to end
                    System.out.println("The connection from " + begin.getInformation() + " to " + end.getInformation() + " has been removed.");
                }
            }// end if
            if (input.equals("H")) {
                System.out.println("Q Query the airport information by entering the airport code.\n" +
                                   "D Find the minimum distance between two airports.\n" +
                                   "I Insert a connection by entering two airport codes and distance.\n" +
                                   "R Remove an existing connection by entering two airport codes.\n" +
                                   "H Display this message.\n" +
                                   "E Exit. ");
            }
            
            System.out.print("Command? ");
            input = keyboard.nextLine();
        } // end while
        
    }
    
}
