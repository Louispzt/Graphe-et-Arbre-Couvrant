import java.util.*;

public abstract class MSTAlg {
    TreeSet<Edge> edgeList;
    List<Edge> spanningTree;
    int weight;

    public MSTAlg(){
        edgeList = new TreeSet<>();
        spanningTree = new ArrayList<>();
    }



    @Override
    public String toString() {
        return weight + " : " +
                spanningTree.size() + " " +
                spanningTree.toString() + '}';
    }
}
