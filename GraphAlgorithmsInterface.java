
import java.util.Queue;
import java.util.Stack;

public interface GraphAlgorithmsInterface<T> {
    public Queue<T> getBreathFirstTraversal(T origin);
    public Queue<T> getDepthFirstTraversal(T origin);
    public Stack<T> getTopologicalOrder();
    public int getShortestPath(T begin, T end, Stack<T> path);
    public double getCheapestPath(T begin, T end, Stack<T> path);   
}
