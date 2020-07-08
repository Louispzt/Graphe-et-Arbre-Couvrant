import java.util.*;

public class Kruskal1 extends MSTAlg{

    public Kruskal1(){
        super();

        for (Vertex vertex : Main.graphe){
            for (Edge edge : vertex.getEdges().values()){
                if (edge.getV1().getVertexNb() > edge.getV2().getVertexNb())
                    edgeList.add(edge);
            }
        }

        find();
    }

    public void find(){
        Iterator<Edge> itr = edgeList.iterator();
        Links links = new Links();

        while (spanningTree.size() < Main.graphe.size() - 1 && itr.hasNext()) {
            Edge edge = itr.next();

            Vertex v1 = links.find(edge.getV1()).vertex;
            Vertex v2 = links.find(edge.getV2()).vertex;

            if (v1.getVertexNb() != v2.getVertexNb()) {
                spanningTree.add(edge);
                weight += edge.getWeight();
                links.union(v1, v2);
            }
        }
    }

    private class Links{
        private Map<Vertex, Link> map;

        private Links(){
            map = new HashMap<>();
            for (Vertex vertex : Main.graphe){
                map.put(vertex, new Link(vertex));
            }
        }

        private Link find(Vertex vertex) {
            if (!map.get(vertex).vertex.equals(vertex))
                map.replace(vertex, find(map.get(vertex).vertex));

            return map.get(vertex);
        }

        private void union(Vertex v1, Vertex v2){
            Vertex rootV1 = find(v1).vertex;
            Vertex rootV2 = find(v2).vertex;
            if (map.get(rootV1).rank < map.get(rootV2).rank)
                map.replace(rootV1, map.get(rootV2));
            else {
                map.replace(rootV2, map.get(rootV1));
                if ((map.get(rootV1).rank > map.get(rootV2).rank)){
                    map.get(rootV1).rank++;
                }
            }
        }

        private class Link{
            private final Vertex vertex;
            private Vertex linkedTo;
            private int rank;

            private Link(Vertex vertex){
                this.vertex = vertex;
                this.linkedTo = vertex;
                rank = 0;
            }

            private void setLink(Vertex parent){
                this.linkedTo = parent;
            }

            private Vertex getLink() {
                return linkedTo;
            }

            @Override
            public String toString() {
                return vertex.getVertexNb() + ":" + linkedTo.getVertexNb();
            }
        }

        @Override
        public String toString() {
            StringBuilder str = new StringBuilder("{");
            Iterator<Vertex> itr = Main.graphe.iterator();
            while (itr.hasNext()){
                Vertex vertex = itr.next();
                str.append(vertex.getVertexNb()).append(":").append(map.get(vertex).vertex.getVertexNb());
                if (itr.hasNext())
                    str.append(", ");
            }
            str.append("}");
            return String.valueOf(str);
        }
    }

    @Override
    public String toString() {
        return "Kruskal1 {" + super.toString();
    }
}

