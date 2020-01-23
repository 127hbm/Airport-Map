//
//  Name:    Huynh, Minh
//  Project: 4  
//  Due:     12/10/2019
//  Course:  cs-2400-01-f19
//
//   Description:
//          Implement a graph data structure for a practical application. 
//
public interface BasicGraphInterface<T> {
    public boolean addVertex(T vertexLabel);
    public boolean addEdge(T begin, T end, double edgeWeight);
    public boolean addEdge(T begin, T end);
    public boolean hasEdge(T begin, T end);
    public boolean isEmpty();
    public int getNumberOfVertices();
    public int getNumberOfEdges();
    public void clear();
    
}
