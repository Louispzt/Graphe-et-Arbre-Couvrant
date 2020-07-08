import java.util.*;

public class Prim extends MSTAlg{

    public Prim(){
        CostOfV[] tree = new CostOfV[Main.graphe.size()];
        for (int i = 0; i < Main.graphe.size(); i++){
            tree[i] = new CostOfV();
        }

        int start = new Random().nextInt(Main.graphe.size());
        tree[start].cost = 0;

        for (int i = 0; i < tree.length; i++) {
            final int min = min(tree);
            if (min != -1) {
                tree[min].picked = true;

                for (Edge edge : Main.graphe.get(min).getEdges().values()) {
                    final int d = edge.getWeight();
                    if (tree[edge.getV2Nb() - 1].cost > d && !tree[edge.getV2Nb()-1].picked) {
                        tree[edge.getV2Nb() - 1].cost = d;
                        tree[edge.getV2Nb() - 1].edge = edge;
                    }
                }
            }
        }

        for (CostOfV costOfV : tree){
            if (costOfV.edge != null) {
                spanningTree.add(costOfV.edge);
                weight += costOfV.cost;
            }
        }
    }

    private static int min(CostOfV[] tree) {
        int x = Integer.MAX_VALUE;
        int y = -1;
        for (int i=0; i<tree.length; i++) {
            if (!tree[i].picked && tree[i].cost < x) {
                y = i;
                x = tree[i].cost;
            }
        }
        return y;
    }

    @Override
    public String toString() {
        return "Prim {" + super.toString();
    }

    class CostOfV{
        private int cost;
        private Edge edge;
        private boolean picked;

        public CostOfV(){
            this.edge = null;
            cost = Integer.MAX_VALUE;
            picked = false;
        }

        @Override
        public String toString() {
            return edge + ":" + cost;
        }
    }
}
