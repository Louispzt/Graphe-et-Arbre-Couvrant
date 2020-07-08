public class Edge implements Comparable {
    private final Vertex v1;
    private final Vertex v2;
    private final int weight;

    public Edge(Vertex v1, Vertex v2, int weight){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Edge){
            switch (Integer.compare(weight, ((Edge) o).weight)){
                case -1:
                    return -1;
                case 1:
                    return 1;
                case 0:
                    switch (Integer.compare(v1.getVertexNb(), ((Edge) o).v1.getVertexNb())) {
                        case -1:
                            return -1;
                        case 1:
                            return 1;
                        case 0:
                            return Integer.compare(v2.getVertexNb(), ((Edge) o).v2.getVertexNb());
                    }
            }
        }
        System.out.println("oui");
        return 0;
    }

    @Override
    public String toString() {
        return "(" + v1.getVertexNb() +
                "," + v2.getVertexNb() +
                "):" + weight;
    }

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public int getV1Nb() {
        return v1.getVertexNb();
    }

    public int getV2Nb() {
        return v2.getVertexNb();
    }

    public int getWeight() {
        return weight;
    }
}
