import java.util.ArrayList;

public class Edge {
    private int edgeNb;
    ArrayList<Edge> successors;

    public Edge(int edgeNb){
        this.setEdgeNb(edgeNb);
        successors = new ArrayList<>();
    }

    public void addSuccessor(Edge sommet){
        if (!successors.contains(sommet))
            successors.add(sommet);
    }

    public String successorsToString(){
        StringBuilder str = new StringBuilder();
        for (Edge edge: successors){
            str.append(' ').append(edge.getVertex());
        }
        return str.toString();
    }

    @Override
    public String toString() {
        return "{" + getVertex() +
                ":" + successorsToString() +
                "}";
    }

    public int getVertex() {
        return edgeNb;
    }

    public void setEdgeNb(int edgeNb) {
        this.edgeNb = edgeNb;
    }
}
