import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Fenetre extends JFrame{
    DisplayGraphe mouseDragTest;
    TextDisplay textDisplay;
    TextDisplay colorDisplay;
    BoutonEnum onDisplay;
    JLabel currentFile;
    int sort;
    int method;

    public Fenetre(DisplayGraphe mouseDragTest){
        this.setTitle("Bouton");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        sort = 0;
        method = 0;

        this.mouseDragTest= mouseDragTest;
        this.getContentPane().add(this.mouseDragTest, BorderLayout.CENTER, 0);
        onDisplay = BoutonEnum.GRAPHE;




        Box boxNorth = Box.createHorizontalBox();
        boxNorth.add(new Bouton("Ouvrir un Graphe", BoutonEnum.OPEN));
        boxNorth.add(new Bouton("Voir le fichier texte", BoutonEnum.TXT));
        boxNorth.add(new Bouton("Voir le Graphe", BoutonEnum.GRAPHE));
        boxNorth.add(new Bouton("Voir la Liste de Coloration", BoutonEnum.COLOR));
        //boxNorth.add(new Bouton("Save", BoutonEnum.SAVE));
        currentFile = new JLabel(" Current File : " + Main.file);
        boxNorth.add(currentFile);

        JPanel wrapperNorth = new JPanel();
        wrapperNorth.setLayout(new BoxLayout(wrapperNorth, BoxLayout.X_AXIS));
        wrapperNorth.add(boxNorth);
        this.getContentPane().add(wrapperNorth, BorderLayout.NORTH,1);
        //Au sud

        JPanel panWest = new JPanel(new GridLayout(1,4,5,5));
        panWest.add(new Bouton("Croissant", BoutonEnum.INCREASING));
        panWest.add(new Bouton("Décroissant", BoutonEnum.DECREASING));
        panWest.add(new Bouton("Aléatoire", BoutonEnum.RANDOM));
        panWest.add(new Bouton("Sens Naturel", BoutonEnum.EDGEINCREASE));

        JPanel wrapperSouth = new JPanel(new GridBagLayout());
        wrapperSouth.add(panWest);

        this.getContentPane().add(wrapperSouth, BorderLayout.SOUTH,1);

        JPanel panEast = new JPanel(new GridLayout(4,1,5,5));
        panEast.add(new Bouton("GreedyColoring", BoutonEnum.GREEDY));
        panEast.add(new Bouton("Welshpowell",  BoutonEnum.WP));
        panEast.add(new Bouton("DSATUR0", BoutonEnum.DSATUR_0));
        panEast.add(new Bouton("DSATUR°", BoutonEnum.DSATUR_DEGREE));

        JPanel wrapperEast = new JPanel(new GridBagLayout());
        wrapperEast.setDoubleBuffered(true);
        wrapperEast.add(panEast);


        this.getContentPane().add(wrapperEast, BorderLayout.EAST,3);


        JPanel boxWest = new JPanel(new GridLayout(3, 1,5,5));
        boxWest.add(new Bouton("A la ligne", BoutonEnum.LIGNE));
        boxWest.add(new Bouton("Circulaire", BoutonEnum.CIRCULAIRE));
        boxWest.add(new Bouton("Ellipsoïde", BoutonEnum.ELLIPSE));

        JPanel wrapperWest = new JPanel(new GridBagLayout());
        wrapperWest.add(boxWest);

        this.getContentPane().add(wrapperWest, BorderLayout.WEST,4);
        this.setVisible(true);
        mouseDragTest.colorMethod = Main.graphList.get(method).get(sort);
        mouseDragTest.changeColorDisplay();
    }

    public void changeContent(BoutonEnum bEnum) throws IOException {
        switch (bEnum){
            case OPEN:
                Main.reload();
                mouseDragTest.recompute();
                textDisplay = new TextDisplay(Main.graphe);
                currentFile.setText(" Current File : " + Main.file);
                reloadDisplay();
                break;
            case TXT:
                this.getContentPane().remove(0);
                if (textDisplay == null){
                    textDisplay = new TextDisplay(Main.graphe);
                }
                this.getContentPane().add(new JScrollPane (textDisplay), BorderLayout.CENTER,0);
                onDisplay = BoutonEnum.TXT;
                reloadDisplay();
                break;
            case GRAPHE:
                this.getContentPane().remove(0);
                this.getContentPane().add(mouseDragTest, BorderLayout.CENTER,0);
                onDisplay = BoutonEnum.GRAPHE;
                reloadDisplay();
                break;
            case COLOR:
                this.getContentPane().remove(0);
                if (colorDisplay == null){
                    colorDisplay = new TextDisplay();
                }
                this.getContentPane().add(new JScrollPane (colorDisplay), BorderLayout.CENTER,0);
                onDisplay = BoutonEnum.COLOR;
                reloadDisplay();
                break;
            case SAVE:
                break;
            case GREEDY:
                method = 0;
                reloadDisplay();
                break;
            case WP:
                method = 1;
                reloadDisplay();
                break;
            case DSATUR_0:
                method = 2;
                reloadDisplay();
                break;
            case DSATUR_DEGREE:
                method = 3;
                reloadDisplay();
                break;
            case INCREASING:
                sort = 0;
                reloadDisplay();
                break;
            case DECREASING:
                sort = 1;
                reloadDisplay();
                break;
            case RANDOM:
                if (sort == 2){
                    ArrayList<Integer> sortList = new QuickSort(SortEnumerator.RANDOM).sortList;
                    Main.graphList.get(0).set(2, new GreedyColoring(new ArrayList<>(sortList)));
                    Main.graphList.get(1).set(2, new WelshPowell(new ArrayList<>(sortList)));
                    Main.graphList.get(2).set(2, new Dsatur0(new ArrayList<>(sortList)));
                    Main.graphList.get(3).set(2, new Dsatur_degree(new ArrayList<>(sortList)));
                    Main.sortMap.set(2,(new ArrayList<>(sortList)));
                    mouseDragTest.repaint();
                }
                else
                    sort = 2;
                reloadDisplay();
                break;
            case EDGEINCREASE:
                sort = 3;
                reloadDisplay();
                break;
            case REINITIALISER:
                mouseDragTest.reloadZoom();
                break;
            case LIGNE:
                mouseDragTest.polyType = 0;
                mouseDragTest.reloadZoom();
                break;
            case CIRCULAIRE:
                mouseDragTest.polyType = 1;
                mouseDragTest.reloadZoom();
                break;
            case ELLIPSE:
                mouseDragTest.polyType = 2;
                mouseDragTest.reloadZoom();
                break;
        }
    }

    public void reloadDisplay(){
        if (mouseDragTest.colorMethod.colorList != Main.graphList.get(method).get(sort).colorList) {
            mouseDragTest.colorMethod = Main.graphList.get(method).get(sort);
            mouseDragTest.repaint();
        }
        if (onDisplay == BoutonEnum.TXT)
            textDisplay.changeMethod(sort);
        else if (onDisplay == BoutonEnum.COLOR)
            colorDisplay.reloadColor();
        repaint();
        revalidate();
    }
}