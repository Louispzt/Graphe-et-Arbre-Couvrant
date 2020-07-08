import java.util.*;

public class Vertex {
    private int vertexNb;
    private TreeMap<Integer, Edge> edges;
    private TreeSet<Edge> setEdges;

    public Vertex(int vertexNb){
        this.setVertexNb(vertexNb);
        edges = new TreeMap<>();
        setEdges = new TreeSet<>();
    }

    public void addEdge(Vertex vertex, int weight){
        Edge edge = new Edge(this, vertex, weight);
        setEdges.add(edge);
        edges.put(vertex.vertexNb, edge);
    }


    public String successorsToString(){
        StringBuilder str = new StringBuilder();
        for (int linkedTo : edges.keySet()){
            str.append(' ').append(linkedTo).append(':').append(edges.get(linkedTo).getWeight());
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return "(" + getVertexNb() +
                ")" + successorsToString();
    }

    public int getVertexNb() {
        return vertexNb;
    }

    public TreeMap<Integer, Edge> getEdges() {
        return edges;
    }

    public void setVertexNb(int vertexNb) {
        this.vertexNb = vertexNb;
    }

    public int getSize(){
        return edges.size();
    }

    public TreeSet<Edge> getSetEdges() {
        return setEdges;
    }

    public int getNegSize(){
        return -edges.size();
    }

    public void addEdge(Edge edge) {
        if (edge.getV1Nb() != this.getVertexNb())
            addEdge(edge.getV1(), edge.getWeight());
        else
            addEdge(edge.getV2(), edge.getWeight());
    }
}
