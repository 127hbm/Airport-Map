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
public interface VertexInterface<T> {
    public T getLabel();
    public void visit();
    public void unvisit();
    public boolean isVisited();
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight);
    public boolean connect(VertexInterface<T> endVertex);
    public Iterator<VertexInterface<T>> getNeighborIterator();
    public Iterator<Double> getWeightIterator();
    public boolean hasNeighbor();
    public VertexInterface<T> getUnvisitedNeighbor();
    public void setPredecessor(VertexInterface<T> predecessor);
    public VertexInterface<T> getPredecessor();
    public boolean hasPredecessor();
    public void setCost(double newCost);
    public double getCost();
    public double getWeightOfEdge(VertexInterface<T> endVertex); // added for getting the cheapest path
    public boolean disconnect(VertexInterface<T> endVertex); // return true if the removal is sucessful
}
