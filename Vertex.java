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
import java.util.NoSuchElementException;
import java.util.List;
import java.util.ArrayList;
class Vertex<T> implements VertexInterface<T> {
    private T label;
    private List<Edge> edgeList;
    private boolean visited;
    private VertexInterface<T> previousVertex;
    private double cost;
    
    public Vertex(T vertexLabel) {
        label = vertexLabel;
        edgeList = new ArrayList<>();
        visited = false;
        previousVertex = null;
        cost = 0;
    }// end constructor
    public Vertex() {
        this(null);
    }
    
    public T getLabel() {
        return label;
    }
    public void visit() {
        visited = true;
    }
    public void unvisit() {
        visited = false;
    }
    public boolean isVisited() {
        return visited == true;
    }
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        boolean duplicateEdge = false;
        VertexInterface<T> nextNeighbor = null;
        if (!this.equals(endVertex)) {// Vertices are distinct
            while (!duplicateEdge && neighbors.hasNext()) { // check if the edge to be added exists
                nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) 
                    duplicateEdge = true;
            }// end while
            if (!duplicateEdge) {
                edgeList.add(new Edge(endVertex, edgeWeight));
                result = true;
            }
        } // end if
        return result;
    }// end connect
    
    public boolean connect(VertexInterface<T> endVertex) {
        return connect(endVertex, 0);
    }
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeighborIterator();
    }
    
    public Iterator<Double> getWeightIterator() {
        return new WeightIterator();
    }
    
    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    }
    
    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> result = null;
        VertexInterface<T> nextNeighbor = null;
        Iterator<VertexInterface<T>> neighbors = getNeighborIterator();
        while (neighbors.hasNext() && result == null) {
            nextNeighbor = neighbors.next();
            if(!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while
        return result;
    }
    
    public boolean disconnect(VertexInterface<T> endVertex) {
        boolean done = false;
        Iterator<Edge> edges = edgeList.listIterator();
        VertexInterface<T> nextNeighbor = null;
        while(!done && edges.hasNext()) {
            Edge edgeToNextNeighbor = edges.next();
            if (edgeToNextNeighbor.getEndVertex().equals(endVertex)) {
                edgeList.remove(edgeToNextNeighbor); // remove the edge when it is found
                done = true;
            } 
        } // end while
        return done;
    }
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }
    public boolean hasPredecessor() {
        return previousVertex != null;
    }
    public void setCost(double newCost) {
        cost = newCost;
    }
    public double getCost(){
        return cost;
    }
    
    @Override
    // Two vertices are equal if their labels are equal.
    public boolean equals (Object other) {
        boolean result;
        if (other == null || getClass() != other.getClass())
            result = false;
        else {
            Vertex<T> otherVertex = (Vertex<T>) other;
            result = label.equals(otherVertex.label);
        }
        return result;
    } // end equals
    
    public double getWeightOfEdge(VertexInterface<T> endVertex) {
        double result = 0.0;
        boolean found = false;
        Iterator<Edge> edges = edgeList.listIterator();
        VertexInterface<T> nextNeighbor = null;
        while(!found && edges.hasNext()) {
            Edge edgeToNextNeighbor = edges.next();
            if (edgeToNextNeighbor.getEndVertex().equals(endVertex)) {
                result = edgeToNextNeighbor.getWeight();
                found = true;
            } // only call this method when this edge exists.
        }
        return result;
    }      
    protected class Edge {
        private VertexInterface<T> vertex; // the end vertex of the edge
        private double weight;
        protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
            vertex = endVertex;
            weight = edgeWeight;
        }
        protected Edge(VertexInterface<T> endVertex) {
            vertex = endVertex;
            weight = 0;
        }
        protected VertexInterface<T> getEndVertex() {
            return vertex;
        }
        protected double getWeight() {
            return weight;
        }
    }// end class Edge
    
    private class NeighborIterator<T> implements Iterator<VertexInterface<T>> {
        private Iterator<Edge> edges;
        public NeighborIterator() {
            edges = edgeList.listIterator();
        }
        public boolean hasNext() {
            return edges.hasNext();
        }
        public VertexInterface<T> next() {
            VertexInterface<T> nextNeighbor = null;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextNeighbor = (VertexInterface<T>) edgeToNextNeighbor.getEndVertex();
            }
            else
                throw new NoSuchElementException();
            return nextNeighbor;
        }// end next
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }// end NeighborIterator
    
    
    private class WeightIterator<T> implements Iterator<Double> {
        private Iterator<Edge> edges;
        public WeightIterator() {
            edges = edgeList.listIterator();
        }
        public boolean hasNext() {
            return edges.hasNext();
        }
        public Double next() {
            double nextWeight = 0;
            if (edges.hasNext()) {
                Edge edgeToNextNeighbor = edges.next();
                nextWeight = (double) edgeToNextNeighbor.getWeight();
            }
            else
                throw new NoSuchElementException();
            return nextWeight; //auto-boxing
        }// end next
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }// end WeightIterator
}
