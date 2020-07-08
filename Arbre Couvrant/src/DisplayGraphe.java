import utils.ColorGradient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.*;

public class DisplayGraphe extends JPanel {
    String[] methodNames;
    public int polyType;
    ArrayList<OvalString> ovalList;
    ArrayList<OvalString> ovalListCopy;
    OvalString oSelectionned;
    private Point mousePt;
    public static FontMetrics fm;
    double zoom;
    ArrayList<Vertex> graphe;
    int dx;
    int dy;
    double fact;
    Point2D.Double offset;
    ColorGradient gradient;
    static int width, height;
    static Map<Integer, MutedInteger> levelMap;
    static Map<Integer, MutedInteger> levelMapCopy;
    int endTree;
    int countEndTree;
    TextDisplay weight;
    int deg;

    public DisplayGraphe(){
        this.setDoubleBuffered(true);
        polyType = 0;
        zoom = 1;
        mousePt = getMousePosition();
        Font font;
        fact = 11/10d;
        oSelectionned = null;
        this.graphe = Main.graphe;
        setBackground(new Color(50,50,50));
        ovalList = new ArrayList<>();
        ovalListCopy = new ArrayList<>();
        methodNames = new String[]{"Kruskal1","Kruskal2","Prim","d-MST 1", "d-MST 2", "d-MST 3"};
        levelMap = new TreeMap<>();
        levelMapCopy = new TreeMap<>();
        endTree = 1;
        countEndTree = 1;
        weight = new TextDisplay();
        deg = 0;

        dx = 0;
        dy = 0;
        offset = new Point2D.Double(0,0);
        width = getWidth();
        height = getHeight();

        gradient= new ColorGradient(Color.GREEN, 1.0, Color.RED, 1.0);
        gradient.addColor(Color.YELLOW, 0.1, 1);

        this.requestFocus();


        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                for (int i = ovalList.size() - 1; i >= 0 ; i--){
                    OvalString o = ovalList.get(i);
                    if (Math.pow((e.getPoint().x - o.x )/ (o.ovalW / 2f),2) + Math.pow((e.getPoint().y - o.y )/ (o.ovalH / 2f), 2)  <= 1){
                        oSelectionned = o;
                        repaint();
                        return;
                    }
                }
                oSelectionned = null;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                dx = e.getX() - mousePt.x;
                dy = e.getY() - mousePt.y;
                if (oSelectionned != null)
                {
                    oSelectionned.update(dx, dy);
                    ovalListCopy.get(oSelectionned.vertex - 1).update(dx/zoom, dy/zoom);
                }
                else {
                    for (OvalString o : ovalList)
                        o.update(dx, dy);
                    offset.x += dx;
                    offset.y += dy;
                }
                mousePt = e.getPoint();
                repaint();
            }
        });

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    fact = 10 / 11d;
                }
                else if (e.getWheelRotation() < 0){
                    fact = 11 / 10d;
                }
                if (zoom*fact < 100 && zoom*fact > 0.05){
                    zoom *= fact;
                    offset.x = offset.x * fact - e.getX() * (fact - 1);
                    offset.y = offset.y * fact - e.getY() * (fact - 1);
                    for (OvalString o : ovalListCopy) {
                        double x = offset.x + o.x * zoom;
                        double y = offset.y + o.y * zoom;
                        ovalList.get(o.vertex - 1).set(x, y);
                    }
                    mousePt = e.getPoint();
                    repaint();
                }
            }
        });
        this.add(weight);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        deg = 0;
        width = getWidth();
        height = getHeight();
        g.setFont(new Font("Serif", Font.BOLD , 30));
        fm = g.getFontMetrics();
        if (ovalList.isEmpty())
            recompute();

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Vertex vertex1;

        g.setFont(new Font("Serif", Font.PLAIN , 25));
        g2.setColor(Color.CYAN);
        g2.setStroke(new BasicStroke(4));
        int[] degree = new int[Main.graphe.size()];
        for (Edge edge : Main.graphList.get(Main.fenetre.method).spanningTree){
            OvalString o1 = ovalList.get(edge.getV1().getVertexNb()-1);
            OvalString o2 = ovalList.get(edge.getV2().getVertexNb()-1);
            degree[edge.getV1().getVertexNb()-1]++;
            degree[edge.getV2().getVertexNb()-1]++;
            g2.drawLine((int) o1.x, (int) o1.y, (int) o2.x, (int) o2.y);
        }
        Vertex v0 = new Vertex(0);
        Edge max = new Edge(v0, v0 , 0);
        Edge min = new Edge(v0, v0, 0);
        if (oSelectionned != null){
            g2.setStroke(new BasicStroke(1));
            max = graphe.get(oSelectionned.vertex - 1).getEdges().values().stream().max(Edge::compareTo).get();
            min = graphe.get(oSelectionned.vertex - 1).getEdges().values().stream().min(Edge::compareTo).get();
            for (int vertex : graphe.get(oSelectionned.vertex - 1).getEdges().keySet()) {
                OvalString o2 = ovalList.get(vertex - 1);
                g2.setColor(gradient.getColor((graphe.get(oSelectionned.vertex - 1).getEdges().get(vertex).getWeight() - min.getWeight())/(double) (max.getWeight() - min.getWeight())));
                g2.drawLine((int)oSelectionned.x, (int) oSelectionned.y, (int) o2.x, (int) o2.y);
                g2.drawString(String.valueOf(graphe.get(oSelectionned.vertex - 1).getEdges().get(vertex).getWeight()), (int) (oSelectionned.x + o2.x) / 2, (int) (oSelectionned.y + o2.y) / 2);
            }
        }

        g.setFont(new Font("Serif", Font.BOLD , 30));

        for (OvalString oS : ovalList) {
            if (degree[oS.vertex-1] > deg){
                deg = degree[oS.vertex-1];
            }
            oS.update();
            if (oSelectionned != null && (oS != oSelectionned) && graphe.get(oSelectionned.vertex - 1).getEdges().containsKey(oS.vertex)) {
                g2.setColor(gradient.getColor((graphe.get(oSelectionned.vertex - 1).getEdges().get(oS.vertex).getWeight() - min.getWeight())/(double) (max.getWeight() - min.getWeight())));
                g2.fillOval(oS.ovalX, oS.ovalY, oS.ovalW, oS.ovalH);
                g2.setColor(Color.black);
                g2.drawString(String.valueOf(oS.vertex), oS.wordX, oS.wordY);
            }
            else {
                if (oSelectionned != null && oS == oSelectionned){
                    g2.setColor(Color.cyan);
                    g2.fillOval(oS.ovalX-5, oS.ovalY-5, oS.ovalW+10, oS.ovalH+10);
                    g2.setColor(Color.white);
                    g2.fillOval(oS.ovalX, oS.ovalY, oS.ovalW, oS.ovalH);
                }
                else{
                    g2.setColor(Color.gray);
                    g2.fillOval(oS.ovalX, oS.ovalY, oS.ovalW, oS.ovalH);
                }
                g2.setColor(Color.black);
                g2.drawString(String.valueOf(oS.vertex), oS.wordX, oS.wordY);
            }
        }

        weight.setText(methodNames[Main.fenetre.method] + " -> poids de " + Main.graphList.get(Main.fenetre.method).weight + "\ndegré max : " + deg);
    }

    public void recompute(){
        ovalList.clear();
        ovalListCopy.clear();
        oSelectionned = null;

        if (polyType == 0){
            double col = (Math.ceil(Math.sqrt(graphe.size())));
            double line = Math.ceil((float) graphe.size() / col);

            double W = getWidth() / (col + 1);
            double H = getHeight() / (line + 1);

            for (int i = 1; i <= graphe.size(); i++) {
                OvalString o = new OvalString(W + W * ((i - 1) % col), (1 + (int) ((i - 1) / col)) * H, i, 10);
                ovalList.add(o);
                ovalListCopy.add(new OvalString(W + W * ((i - 1) % col), (1 + (int) ((i - 1) / col)) * H, i, 10));
            }
        }
        else if (polyType == 1){
            double x;
            double y;
            int lenX = getWidth() / 2;
            int lenY = getHeight() / 2;
            if (polyType == 1){
                int len = Math.min(lenX, lenY);
                lenX = len;
                lenY = len;
            }
            for (int i = 1; i <= graphe.size(); i++){
                x = (getWidth() >> 1) + Math.cos((i - 1) / (double) graphe.size() * 2 * Math.PI) * lenX * 0.9;
                y = (getHeight() >> 1) + Math.sin((i - 1) / (double) graphe.size() * 2 * Math.PI) * lenY * 0.9;
                OvalString o = new OvalString(x, y, i, 10);
                ovalList.add(o);
                ovalListCopy.add(new OvalString(x, y, i, 10));
            }
        }
        else if (polyType == 2){
            levelMap.clear();
            levelMapCopy.clear();
            endTree = 0;
            countEndTree = 0;
            Vertex[] spanningTree = new Vertex[graphe.size()];
            for (int i = 0; i < graphe.size(); i++){
                spanningTree[i] = new Vertex(i+1);
            }
            for (Edge edge : Main.graphList.get(Main.fenetre.method).spanningTree){
                spanningTree[edge.getV2Nb()-1].addEdge(spanningTree[edge.getV1Nb()-1], edge.getWeight());
                spanningTree[edge.getV1Nb()-1].addEdge(spanningTree[edge.getV2Nb()-1], edge.getWeight());
            }



            Vertex max = spanningTree[0];
            int size = 0;
            for (Vertex vertex : spanningTree){
                if (vertex.getSize() > max.getSize()) {
                    max = vertex;
                    size = 0;
                }
                if (vertex.getSize() == max.getSize()){
                    int size2 = 0;
                    if (size == 0) {
                        for (Edge edge : max.getEdges().values()) {
                            size += edge.getV2().getSize();
                        }
                    }
                    for (Edge edge : vertex.getEdges().values()){
                        size2 += edge.getV2().getSize();
                    }
                    if (size2 > size)
                        max = vertex;
                }
            }
            //System.out.println(Arrays.toString(spanningTree));

            //System.out.println(max);

            LinkedVertex head = new LinkedVertex(max.getVertexNb(), null);

            doTree(head, spanningTree);

            //System.out.println(head);
            //System.out.println(levelMap);
            head.setPos(getWidth()/2, Math.max(80, getHeight() / (2+levelMapCopy.size())));
            doPos(head);

            //System.out.println(head);

            doOvalString(head);

            ovalList.sort(Comparator.comparingInt(OvalString::getVertex));
            ovalListCopy.sort(Comparator.comparingInt(OvalString::getVertex));
        }
    }

    private void doOvalString(LinkedVertex linkedVertex) {
        ovalList.add(new OvalString(linkedVertex.x, linkedVertex.y, linkedVertex.getVertexNb(),10));
        ovalListCopy.add(new OvalString(linkedVertex.x, linkedVertex.y, linkedVertex.getVertexNb(),10));
        for (LinkedVertex linkedVertex1 : linkedVertex.getChildMap().keySet()){
            doOvalString(linkedVertex1);
        }
    }

    public void doTree(LinkedVertex linkedVertex, Vertex[] spanningTree){
        for (Edge edge : spanningTree[linkedVertex.getVertexNb()-1].getEdges().values()){
            if (linkedVertex.getParent() == null || linkedVertex.getParent().getVertexNb() != edge.getV2Nb()){
                doTree(new LinkedVertex(edge.getV2Nb(), linkedVertex), spanningTree);
            }
        }
        if (linkedVertex.getParent() != null){
            MutedInteger count = levelMap.get(linkedVertex.level);
            if (count == null) {
                levelMap.put(linkedVertex.level, new MutedInteger());
                levelMapCopy.put(linkedVertex.level, new MutedInteger());
            }
            else {
                count.increment();
                levelMapCopy.get(linkedVertex.level).increment();
            }
            linkedVertex.getParent().addChild(linkedVertex, spanningTree[linkedVertex.getVertexNb()-1].getEdges().get(linkedVertex.getParent().getVertexNb()).getWeight());
        }
        if (linkedVertex.getChildMap().isEmpty()) {
            endTree++;
            countEndTree++;
        }
    }

    public void doPos(LinkedVertex linkedVertex){
        if (linkedVertex.getParent() != null){
            linkedVertex.y += linkedVertex.getParent().y + getHeight() / (2+levelMapCopy.size());
        }
        for (LinkedVertex linkedVertex1 : linkedVertex.getChildMap().keySet()){
            doPos(linkedVertex1);
        }
        if (linkedVertex.childMap.isEmpty()){
            countEndTree--;
            linkedVertex.x = getWidth()/endTree/2 + countEndTree * getWidth()/endTree;
        }
        else{
            int x = 0;
            for (LinkedVertex linkedVertex1 : linkedVertex.getChildMap().keySet()){
                x += linkedVertex1.x;
            }
            linkedVertex.x = x / linkedVertex.getChildMap().size();
        }

    }

    public void reloadZoom(){
        zoom = 1;
        offset = new Point2D.Double(0,0);
        recompute();
        repaint();
    }
}
