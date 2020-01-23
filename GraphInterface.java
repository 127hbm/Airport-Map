//
//  Name:    Huynh, Minh
//  Project: 4  
//  Due:     12/10/2019
//  Course:  cs-2400-01-f19
//
//   Description:
//          Implement a graph data structure for a practical application. 
// 

public interface GraphInterface<T> extends BasicGraphInterface<T>, GraphAlgorithmsInterface<T> {
    public T getVertex(T vertexLabel); // return the vertex label in the graph; return null if it is not found
                                       // this method is added for the purpose of this project
 
}
