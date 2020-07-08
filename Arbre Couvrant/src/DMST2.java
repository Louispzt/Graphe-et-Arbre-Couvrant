import java.util.*;

public class DMST2 extends MSTAlg{
        ArrayList<Vertex> R;
        int[] degree;

        public DMST2(int d){
            Random rand = new Random();
            degree = new int[Main.graphe.size()];

            for (Vertex vertex : Main.graphe){
                for (Edge edge : vertex.getEdges().values()){
                    if (edge.getV1().getVertexNb() > edge.getV2().getVertexNb())
                        edgeList.add(edge);
                }
            }

            R = new ArrayList<>();
            R.add(Main.graphe.get(rand.nextInt(Main.graphe.size())));
            find(d);
        }

        public void find(int d) {
            Iterator<Edge> itr = edgeList.iterator();
            while (R.size() < Main.graphe.size() && itr.hasNext()){
                Edge edge = itr.next();
                if (R.contains(edge.getV1()) && degree[edge.getV1Nb()-1] < d && degree[edge.getV2Nb()-1] < d){
                    if (!R.contains(edge.getV2())){
                        spanningTree.add(edge);
                        weight += edge.getWeight();
                        degree[edge.getV1Nb()-1]++;
                        degree[edge.getV2Nb()-1]++;
                        R.add(edge.getV2());
                        itr = edgeList.iterator();
                    }
                }
                else if (R.contains(edge.getV2()) && degree[edge.getV1Nb()-1] < d && degree[edge.getV2Nb()-1] < d){
                    if (!R.contains(edge.getV1())) {
                        spanningTree.add(edge);
                        weight += edge.getWeight();
                        degree[edge.getV1Nb()-1]++;
                        degree[edge.getV2Nb()-1]++;
                        R.add(edge.getV1());
                        itr = edgeList.iterator();
                    }
                }
            }
        }

        @Override
        public String toString() {
            return "d-MST3 {" + super.toString();
        }
    }
