/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//
//  Name:    Huynh, Minh
//  Project: 4  
//  Due:     12/10/2019
//  Course:  cs-2400-01-f19
//
//   Description:
//          Implement a graph data structure for a practical application. 
//
import java.util.Iterator;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashMap;
public class DirectedGraph<T> implements GraphInterface<T> {
    private Map<T, VertexInterface<T>> vertices;
    private int edgeCount;
    
    public DirectedGraph() {
        vertices = new HashMap<>();
        edgeCount = 0;
    }
    
    public boolean addVertex(T vertexLabel) {
        VertexInterface<T> addOutcome = vertices.put(vertexLabel, new Vertex<>(vertexLabel));
        // add new pair into the hashmap
        return addOutcome == null; // if the addition is successful
    }
    public boolean addEdge(T begin, T end, double edgeWeight) {
        if(edgeWeight == 0)
            return removeEdge(begin, end);
        else {
            boolean result = false;
            VertexInterface<T> beginVertex = vertices.get(begin); // look the vertex associated with the begin label in the map
            VertexInterface<T> endVertex = vertices.get(end);
            if (beginVertex != null && endVertex != null)
                result = beginVertex.connect(endVertex, edgeWeight);
            if(result) // the addition is successful
                edgeCount++;
            return result;
        }
    }
    public boolean addEdge(T begin, T end) {
        return addEdge(begin, end, 0);
    }
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        VertexInterface<T> beginVertex = vertices.get(begin); // look the vertex associated with the begin label in the map
        VertexInterface<T> endVertex = vertices.get(end);
        if (beginVertex != null && endVertex != null) {
            Iterator<VertexInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor))
                    found = true;
            }// end while
        } // end if
        return found;
    }
    
    // this removes the edge in the graph
    private boolean removeEdge(T begin, T end) {
        VertexInterface<T> beginVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);
        if (beginVertex != null && endVertex != null) 
            return beginVertex.disconnect(endVertex);
        return false; // if the begin and end are not legal
    }
    
    public boolean isEmpty() {
        return vertices.isEmpty();
    }
    public int getNumberOfVertices() {
        return vertices.size(); // size of the map
    }
    public int getNumberOfEdges() {
        return edgeCount;
    }
    public void clear() {
        throw new UnsupportedOperationException();
    }
    public T getVertex(T vertexLabel) {
        if (vertices.get(vertexLabel) == null) 
            return null;        
        else 
            return vertices.get(vertexLabel).getLabel(); 
    }
    // this sets the fields visited, previousVertex and cost to their initial value
    protected void resetVertices() {
        VertexInterface<T> nextVertex = null;
        // loops through the map and reset all the vertices
        for(Map.Entry<T, VertexInterface<T>> entry : vertices.entrySet()) {
            nextVertex = entry.getValue();
            nextVertex.unvisit();
            nextVertex.setCost(0);
            nextVertex.setPredecessor(null);
        }// end for            
    }
    
    public Queue<T> getBreathFirstTraversal(T origin) {
        resetVertices();
        Queue<T> traversalOrder = new LinkedList<>();
        Queue<VertexInterface<T>> vertexQueue = new LinkedList();
        VertexInterface<T> originVertex = vertices.get(origin); 
        originVertex.visit();
        traversalOrder.add(origin); // enqueue vertex label
        vertexQueue.add(originVertex); // enqueue vertex
        while (!vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.poll(); // dequeue the vertex queue
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while(neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    traversalOrder.add(nextNeighbor.getLabel());
                    vertexQueue.add(nextNeighbor);
                } //end if
            }// end while
        }// end while
        return traversalOrder;
    }
    public Queue<T> getDepthFirstTraversal(T origin) {
        throw new UnsupportedOperationException();
    }
    public Stack<T> getTopologicalOrder() {
        throw new UnsupportedOperationException();
    }
    public int getShortestPath(T begin, T end, Stack<T> path) {
        resetVertices();
        boolean done = false;
        Queue<VertexInterface<T>> vertexQueue = new LinkedList<>();
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);
        originVertex.visit();        
        vertexQueue.add(originVertex); 
        while (!done && !vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.poll();
            Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
            while (!done && neighbors.hasNext()) {
                VertexInterface<T> nextNeighbor = neighbors.next();
                if(!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(1 + frontVertex.getCost());
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.add(nextNeighbor);
                } // end if
                if (nextNeighbor.equals(endVertex))
                    done = true;
            } // end while
        } //end while
        // Traversal ends; construct the path
        int pathLength = (int) endVertex.getCost();
        path.push(endVertex.getLabel()); // path is the stack passed into this method
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor()) { 
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }// end while 
        return pathLength;
    }
    
    public double getCheapestPath(T begin, T end, Stack<T> path) {
        resetVertices();
        boolean done = false;
        Queue<EntryPQ<T>> vertexQueue = new PriorityQueue<>(100, new EntryPQ<T>());
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> endVertex = vertices.get(end);
        vertexQueue.add(new EntryPQ(originVertex, 0, null)); // add originVertex to the priority queue.
        while (!done && !vertexQueue.isEmpty()) {
            EntryPQ<T> frontEntry = vertexQueue.poll(); 
            VertexInterface<T> frontVertex = frontEntry.getVertex(); // vertex in frontEntry
            if (!frontVertex.isVisited()) {
                frontVertex.visit();
                frontVertex.setCost(frontEntry.getWeight());
                frontVertex.setPredecessor(frontEntry.getPredecessor());               
            
                if (frontVertex.equals(endVertex))
                    done = true;
                else {
                    Iterator<VertexInterface<T>> neighbors = frontVertex.getNeighborIterator();
                    while (!done && neighbors.hasNext()) {
                        VertexInterface<T> nextNeighbor = neighbors.next();
                        double weightOfEdgeToNextNeighbor = frontVertex.getWeightOfEdge(nextNeighbor);
                        if (!nextNeighbor.isVisited()) {
                            double nextCost = weightOfEdgeToNextNeighbor + frontVertex.getCost();
                            vertexQueue.add(new EntryPQ(nextNeighbor, nextCost, frontVertex));
                        } 
                    }// end while
                } // end else
            }// end if
        }// end while
        // Traversal ends; construct cheapest path
        double pathCost = endVertex.getCost();
        path.push(endVertex.getLabel());
        VertexInterface<T> vertex = endVertex;
        while (vertex.hasPredecessor()) { 
            vertex = vertex.getPredecessor();
            path.push(vertex.getLabel());
        }// end while 
        return pathCost;
    } 
   
}
