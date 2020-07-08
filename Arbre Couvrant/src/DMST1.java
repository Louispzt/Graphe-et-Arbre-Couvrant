import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class DMST1 extends MSTAlg{
    int[] connected;
    ArrayList<TreeSet<Edge>> dArray;

    public DMST1(int d){
        edgeList = new TreeSet<>();
        connected = new int[Main.graphe.size()];
        dArray = new ArrayList<>();

        Prim prim = new Prim();
        for (int i = 0; i < Main.graphe.size(); i++){
            dArray.add(new TreeSet<>());
        }
        for (Edge edge : prim.spanningTree){
            dArray.get(edge.getV1Nb()-1).add(edge);
            dArray.get(edge.getV2Nb()-1).add(edge);
        }

        for (Vertex vertex : Main.graphe){
            connected[vertex.getVertexNb() - 1] = vertex.getVertexNb();
            for (Edge edge : vertex.getEdges().values()){
                if (edge.getV1().getVertexNb() > edge.getV2().getVertexNb())
                    edgeList.add(edge);
            }
        }
        find(d);
    }

    public void find(int d){
        spanningTree = new ArrayList<>();

        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < Main.graphe.size(); i++){
            vertices.add(new Vertex(i+1));
        }

        dArray.sort(Comparator.comparingInt(TreeSet::size));


        for (int i = 0; i < dArray.size(); i++){
            if (dArray.get(i).size() == 1) {
                for (Edge edge : dArray.get(i)) {
                    if (connected[edge.getV1().getVertexNb() - 1] != connected[edge.getV2().getVertexNb() - 1] || connected[edge.getV1().getVertexNb() - 1] == 0) {
                        if (vertices.get(edge.getV1Nb() - 1).getEdges().size() < d && vertices.get(edge.getV2Nb() - 1).getEdges().size() < d) {
                            spanningTree.add(edge);
                            vertices.get(edge.getV1Nb() - 1).addEdge(edge);
                            vertices.get(edge.getV2Nb() - 1).addEdge(edge);
                            weight += edge.getWeight();
                            int conn = connected[edge.getV2().getVertexNb() - 1];
                            for (Vertex vertex : Main.graphe) {
                                if (connected[vertex.getVertexNb() - 1] == conn)
                                    connected[vertex.getVertexNb() - 1] = connected[edge.getV1().getVertexNb() - 1];
                            }
                            break;
                        }
                    }
                }
            }
        }
        Iterator<Edge> itr = edgeList.iterator();
        while (spanningTree.size() < Main.graphe.size() - 1 && itr.hasNext()){
            Edge edge = itr.next();
            if (connected[edge.getV1().getVertexNb()-1] != connected[edge.getV2().getVertexNb()-1] || connected[edge.getV1().getVertexNb()-1] == 0){
                if (vertices.get(edge.getV1Nb()-1).getEdges().size() < d && vertices.get(edge.getV2Nb()-1).getEdges().size() < d) {
                    spanningTree.add(edge);
                    vertices.get(edge.getV1Nb() - 1).addEdge(edge);
                    vertices.get(edge.getV2Nb() - 1).addEdge(edge);
                    weight += edge.getWeight();
                    int conn = connected[edge.getV2().getVertexNb() - 1];
                    for (Vertex vertex : Main.graphe) {
                        if (connected[vertex.getVertexNb() - 1] == conn)
                            connected[vertex.getVertexNb() - 1] = connected[edge.getV1().getVertexNb() - 1];
                    }
                }
            }
        }
    }


    @Override
    public String toString() {
        return "d-MST2 {" + super.toString();
    }
}
