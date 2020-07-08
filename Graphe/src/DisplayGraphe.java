import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DisplayGraphe extends JPanel {
    String[] methodNames;
    public int polyType;
    ArrayList<OvalString> ovalList;
    OvalString oSelectionned;
    private Point mousePt;
    public static FontMetrics fm;
    float zoom;
    ArrayList<Edge> graphe;
    ColorList colorList;
    ColorMethod colorMethod;
    ArrayList<ArrayList<ColorMethod>> graphList;
    TextDisplay colorDisplay;
    int dx;
    int dy;


    public DisplayGraphe(){
        this.setDoubleBuffered(true);
        polyType = 0;
        zoom = 1;
        mousePt = getMousePosition();
        Font font;
        oSelectionned = null;
        this.graphe = Main.graphe;
        this.graphList = Main.graphList;
        setBackground(new Color(50,50,50));
        ovalList = new ArrayList<>();
        colorDisplay = new TextDisplay("");
        colorList = new ColorList();
        methodNames = new String[]{"GreedyColoring","WelshPowell","DSATUR 0","DSATUR degree"};

        dx = 0;
        dy = 0;




        this.requestFocus();


        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                for (int i = ovalList.size() - 1; i >= 0 ; i--){
                    OvalString o = ovalList.get(i);
                    if (Math.pow((e.getPoint().x - o.x )/ (o.ovalW / 2),2) + Math.pow((e.getPoint().y - o.y )/ (o.ovalH / 2), 2)  <= 1){
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
                }
                else
                    for (OvalString o : ovalList)
                        o.update(dx,dy);
                mousePt = e.getPoint();
                repaint();
            }
        });

        this.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double fact = 1+ 1/(double)3;
                if (e.getWheelRotation() > 0)
                    fact = 1/fact;
                if (zoom * fact >= 0.5) {
                    zoom *= fact;
                    for (OvalString o : ovalList) {
                        int x = (int) (-e.getX() * (fact - 1) + o.x * fact);
                        int y = (int) (-e.getY() * (fact - 1) + o.y * fact);
                        ovalList.get(o.vertex - 1).set(x, y);
                    }
                    mousePt = e.getPoint();
                    repaint();
                }
            }
        });
        this.add(colorDisplay);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Serif", Font.BOLD , 30));
        fm = g.getFontMetrics();

        colorDisplay.setText(methodNames[Main.fenetre.method] + " -> "+ colorMethod.nbColor + " Couleurs utilisées");
        if (ovalList.isEmpty())
            recompute();
        colorList.makeLenN(colorMethod.nbColor);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Edge edge1;
        g2.setColor(Color.darkGray);
        if (oSelectionned == null)
            g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        for (OvalString o1 : ovalList) {
            edge1 = graphe.get(o1.vertex - 1);
            for (Edge successor : edge1.successors) {
                if (successor.getVertex() > edge1.getVertex() && o1 != oSelectionned){
                    OvalString o2 = ovalList.get(successor.getVertex() - 1);
                    if (o2 != oSelectionned){
                        g2.drawLine((int) o1.x, (int) o1.y, (int) o2.x, (int) o2.y);
                    }
                }
            }
        }

        if (oSelectionned != null){
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(2));
            for (Edge edge : graphe.get(oSelectionned.vertex - 1).successors) {
                OvalString o2 = ovalList.get(edge.getVertex() - 1);
                g2.drawLine((int)oSelectionned.x, (int) oSelectionned.y, (int) o2.x, (int) o2.y);
            }
        }

        for (int i = 0; i < ovalList.size(); i++){
            OvalString oS = ovalList.get(i);
            oS.update();
            if (oSelectionned != null && (oS != oSelectionned) && !graphe.get(oS.vertex - 1).successors.contains(graphe.get(oSelectionned.vertex - 1))) {
                g.setColor(colorList.getDarkColor(colorMethod.colorList[i] - 1));
                g.fillOval(oS.ovalX, oS.ovalY, oS.ovalW, oS.ovalH);
                g.setColor(colorList.getDarkInvColor(colorMethod.colorList[i] - 1));
                g.drawString(String.valueOf(oS.vertex), oS.wordX, oS.wordY);
            } else {
                g.setColor(colorList.getColor(colorMethod.colorList[i] - 1));
                g.fillOval(oS.ovalX, oS.ovalY, oS.ovalW, oS.ovalH);
                g.setColor(colorList.getInvColor(colorMethod.colorList[i] - 1));
                g.drawString(String.valueOf(oS.vertex), oS.wordX, oS.wordY);
            }
        }
    }

    public void recompute(){
        ovalList.clear();
        oSelectionned = null;
        
        if (polyType == 0){
            double col = (Math.ceil(Math.sqrt(graphe.size())));
            double line = Math.ceil((float) graphe.size() / col);

            double W = getWidth() / (col + 1);
            double H = getHeight() / (line + 1);

            for (int i = 1; i <= graphe.size(); i++) {
                OvalString o = new OvalString(W + W * ((i - 1) % col), (1 + (int) ((i - 1) / col)) * H, i, 10);
                ovalList.add(o);
            }
        }
        else if (polyType == 1 || polyType == 2){
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
            }
        }
    }

    public void reloadZoom(){
        zoom = 1;
        recompute();
        repaint();
    }

    public void changeColorDisplay(){
        String str = colorMethod.nbColor + " Couleurs utilisées";
        colorDisplay.setText(str);
    }
}
