import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.io.IOException;

public class Fenetre extends JFrame{
    DisplayGraphe displayGraphe;
    TextDisplay textDisplay;
    TextDisplay colorDisplay;
    BoutonEnum onDisplay;
    JLabel currentFile;
    int method;
    JTextField dField;

    public Fenetre(DisplayGraphe displayGraphe){
        this.setTitle("Bouton");
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        method = 0;

        this.displayGraphe = displayGraphe;
        this.getContentPane().add(this.displayGraphe, BorderLayout.CENTER, 0);
        onDisplay = BoutonEnum.GRAPHE;


        Box boxNorth = Box.createHorizontalBox();
        boxNorth.add(new Bouton("Ouvrir un Graphe", BoutonEnum.OPEN));
        boxNorth.add(new Bouton("Voir le fichier texte", BoutonEnum.TXT));
        boxNorth.add(new Bouton("Voir le Graphe", BoutonEnum.GRAPHE));
        currentFile = new JLabel(" Current File : " + Main.file);
        boxNorth.add(currentFile);

        JPanel wrapperNorth = new JPanel();
        wrapperNorth.setLayout(new BoxLayout(wrapperNorth, BoxLayout.X_AXIS));
        wrapperNorth.add(boxNorth);
        this.getContentPane().add(wrapperNorth, BorderLayout.NORTH,1);
        //Au sud

        /*
        JPanel panWest = new JPanel(new GridLayout(1,4,5,5));
        panWest.add(new Bouton("Croissant", BoutonEnum.INCREASING));
        panWest.add(new Bouton("Décroissant", BoutonEnum.DECREASING));
        panWest.add(new Bouton("Aléatoire", BoutonEnum.RANDOM));
        panWest.add(new Bouton("Sens Naturel", BoutonEnum.EDGEINCREASE));

        JPanel wrapperSouth = new JPanel(new GridBagLayout());
        wrapperSouth.add(panWest);

        this.getContentPane().add(wrapperSouth, BorderLayout.SOUTH,1);

         */

        JPanel panEast = new JPanel(new GridLayout(7,1,5,5));
        panEast.add(new Bouton("KRUSKAL1", BoutonEnum.KRUSKAL1));
        panEast.add(new Bouton("KRUSKAL2",  BoutonEnum.KRUSKAL2));
        panEast.add(new Bouton("PRIM", BoutonEnum.PRIM));

        JPanel jp = new JPanel();
        jp.add(new JLabel("max d :"));
        dField = new JTextField(4);
        dField.setText("2");
        PlainDocument doc = (PlainDocument) dField.getDocument();
        doc.setDocumentFilter(new IntFilter());
        jp.add(dField);

        panEast.add(jp);
        panEast.add(new Bouton("DMST1", BoutonEnum.DMST1));
        panEast.add(new Bouton("DMST2", BoutonEnum.DMST2));

        JPanel wrapperEast = new JPanel(new GridBagLayout());
        wrapperEast.setDoubleBuffered(true);
        wrapperEast.add(panEast);


        this.getContentPane().add(wrapperEast, BorderLayout.EAST,2);


        JPanel boxWest = new JPanel(new GridLayout(3, 1,5,5));
        boxWest.add(new Bouton("Aligné", BoutonEnum.LIGNE));
        boxWest.add(new Bouton("Circulaire", BoutonEnum.CIRCULAIRE));
        boxWest.add(new Bouton("Arbre", BoutonEnum.ARBRE));

        JPanel wrapperWest = new JPanel(new GridBagLayout());
        wrapperWest.add(boxWest);

        this.getContentPane().add(wrapperWest, BorderLayout.WEST,3);
        this.setVisible(true);
    }

    public void changeContent(BoutonEnum bEnum) throws IOException{
        switch (bEnum){
            case OPEN:
                Main.reload();
                displayGraphe.recompute();
                currentFile.setText(" Current File : " + Main.file);
                reloadDisplay();
                break;
            case TXT:
                this.getContentPane().remove(0);
                if (textDisplay == null){
                    textDisplay = new TextDisplay();
                }
                this.getContentPane().add(new JScrollPane (textDisplay), BorderLayout.CENTER,0);
                onDisplay = BoutonEnum.TXT;
                reloadDisplay();
                break;
            case GRAPHE:
                this.getContentPane().remove(0);
                this.getContentPane().add(displayGraphe, BorderLayout.CENTER,0);
                onDisplay = BoutonEnum.GRAPHE;
                reloadDisplay();
                break;
            case LIGNE:
                displayGraphe.polyType = 0;
                displayGraphe.reloadZoom();
                break;
            case CIRCULAIRE:
                displayGraphe.polyType = 1;
                displayGraphe.reloadZoom();
                break;
            case ARBRE:
                displayGraphe.polyType = 2;
                displayGraphe.reloadZoom();
                break;
            case KRUSKAL1:
                method = 0;
                reloadDisplay();
                break;
            case KRUSKAL2:
                method = 1;
                reloadDisplay();
                break;
            case PRIM:
                method = 2;
                reloadDisplay();
                break;
            case DMST1:
                if (Main.degree != Integer.parseInt(dField.getText())){
                    Main.degree = Integer.parseInt(dField.getText());
                    Main.graphList.set(3, new DMST1(Main.degree));
                    Main.graphList.set(4, new DMST1(Main.degree));
                }
                method = 3;
                reloadDisplay();
                break;
            case DMST2:
                if (Main.degree != Integer.parseInt(dField.getText())){
                    Main.degree = Integer.parseInt(dField.getText());
                    Main.graphList.set(3, new DMST1(Main.degree));
                    Main.graphList.set(4, new DMST1(Main.degree));
                }
                method = 4;
                reloadDisplay();
                break;
        }
    }

    public void reloadDisplay(){
        if (displayGraphe.polyType == 2){
            displayGraphe.recompute();
        }
        if (onDisplay == BoutonEnum.GRAPHE){
            displayGraphe.repaint();
        }
        if (onDisplay == BoutonEnum.TXT)
            textDisplay.reloadText();
        repaint();
        revalidate();
    }
}