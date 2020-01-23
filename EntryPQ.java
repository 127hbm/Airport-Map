//
//  Name:    Huynh, Minh
//  Project: 4  
//  Due:     12/10/2019
//  Course:  cs-2400-01-f19
//
//   Description:
//          Implement a graph data structure for a practical application. 
//
import java.util.Comparator;
 
class EntryPQ<T> implements Comparator<EntryPQ<T>> {
        private VertexInterface<T> vertex;
        private VertexInterface<T> frontVertex;
        private double weight;
        
        EntryPQ(VertexInterface<T> vertex, double weight, VertexInterface<T> frontVertex) {
            this.vertex = vertex;
            this.weight = weight;
            this.frontVertex = frontVertex;
        }
        EntryPQ() {
            this(null, 0, null);
        }
        
        public VertexInterface<T> getVertex() {
            return vertex;
        }
        
        public VertexInterface<T> getPredecessor() {
            return frontVertex;
        }
        
        public double getWeight() {
            return weight;
        }
        
        @Override
        public int compare(EntryPQ<T> entry1, EntryPQ<T> entry2) {
            if (entry1.weight < entry2.weight)
                return -1;
            if (entry1.weight == entry2.weight)
                return 0;
            return 1; 
        }
    }
