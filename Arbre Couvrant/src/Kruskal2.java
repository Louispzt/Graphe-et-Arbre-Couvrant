import java.util.*;

public class Kruskal2 extends MSTAlg {
    int n = 0;
    ArrayList<ArrayList<Edge>> vertices;

    public Kruskal2() {
        vertices = new ArrayList<>();

        for (Vertex vertex : Main.graphe) {
            vertices.add(new ArrayList<>());
        }

        for (Vertex vertex : Main.graphe){
            for (Edge edge : vertex.getEdges().values()){
                if (edge.getV1().getVertexNb() > edge.getV2().getVertexNb()) {
                    edgeList.add(edge);
                    vertices.get(edge.getV1Nb() - 1).add(edge);
                    vertices.get(edge.getV2Nb() - 1).add(edge);
                }
            }
        }
        find();
    }

    public void find() {
        Iterator<Edge> itr = edgeList.descendingIterator();
        while (edgeList.size() >= Main.graphe.size() && itr.hasNext()) {
            Edge edge = itr.next();
            n = 0;
            isConnected(edge, edge.getV1(), new boolean[Main.graphe.size()]);
            if (n == Main.graphe.size()) {;
                vertices.get(edge.getV1Nb()-1).remove(edge);
                vertices.get(edge.getV2Nb()-1).remove(edge);
                itr.remove();
            }
        }
        for (Edge edge : edgeList) {
            spanningTree.add(edge);
            weight += edge.getWeight();
        }
    }

    public void isConnected(Edge edgeTested, Vertex vertex, boolean[] visited) {
        for (Edge edge : vertices.get(vertex.getVertexNb()-1)) {
            if (edge != edgeTested) {
                if (!visited[edge.getV2Nb() - 1]) {
                    visited[edge.getV2Nb() - 1] = true;
                    n++;
                    isConnected(edgeTested, edge.getV2(), visited);
                }
                if (!visited[edge.getV1Nb() - 1]) {
                    visited[edge.getV1Nb() - 1] = true;
                    n++;
                    isConnected(edgeTested, edge.getV1(), visited);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Kruskal2 {" + super.toString();
    }
}