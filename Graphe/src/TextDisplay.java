import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TextDisplay extends JTextPane {
    ArrayList<Edge> graphe;

    public TextDisplay(String str){
        super();
        this.setMargin(new Insets(5,5,5,5));
        this.setFont(new Font(Font.SANS_SERIF, 3, 20));;
        this.setForeground(Color.white);
        this.setText(str);
        this.setCaretPosition(0);
        this.setBackground(new Color(50,50,50));
    }

    public TextDisplay(ArrayList<Edge> graphe){
        super();
        this.graphe = graphe;
        this.setFont(new Font(super.getFont().getName(), super.getFont().getStyle(), 14));;
        this.setMargin(new Insets(5,5,5,5));
        this.setForeground(Color.white);
        changeMethod(0);
        this.setBackground(new Color(50,50,50));
    }

    public TextDisplay(){
        super();
        this.setMargin(new Insets(5,5,5,5));
        this.setForeground(Color.white);
        reloadColor();
        this.setBackground(new Color(50,50,50));
    }

    public void reloadColor(){
        StringBuilder str = new StringBuilder();
        ColorMethod col = Main.mouseDragTest.colorMethod;
        str.append("This graphe was colored with ").append(col.nbColor).append(" colors, the detail of this coloration can be found below according to the color of the vertex using ");
        switch (Main.fenetre.method){
            case 0:
                str.append("WelshPowell Algorithm, ");
                break;
            case 1:
                str.append("GreedyColoring Algorithm, ");
                break;
            case 2:
                str.append("DSATUR Algorithm, ");
                break;
            case 3:
                str.append("DSATUR3 Algorithm, ");
                break;
            case 4:
                str.append("DSATUR4 Algorithm, ");
                break;
        }
        switch (Main.fenetre.sort){
            case 0:
                str.append("sorted by degree of vertex in ascending order.\n\n");
                break;
            case 1:
                str.append("sorted by degree of vertex in descending order.\n\n");
                break;
            case 2:
                str.append("randomly sorted.\n\n");
                break;
            case 3:
                str.append("sorted by value of vertex in ascending order.\n\n");
        }
        String[] strings = new String[col.nbColor];
        for (int i : Main.sortMap.get(Main.fenetre.sort)) {
            strings[col.colorList[i] - 1] += (i + 1) + " ";
        }
        for (int i = 0; i < strings.length; i++)
            str.append("Color ").append(i + 1).append(" -> ").append(strings[i].substring(4)).append("\n\n");
        this.setText(str.toString());
        this.setCaretPosition(0);
        this.repaint();
        this.revalidate();
    }

    public void changeMethod(int n){
        StringBuilder str = new StringBuilder();
        str.append("This graphe has ").append(Main.nbOfVertex).append(" vertex and ").append(Main.nbOfEdges).append(" edges ");
        switch (Main.fenetre.sort){
            case 0:
                str.append("sorted by degree of vertex in ascending order.\n\n");
                break;
            case 1:
                str.append("sorted by degree of vertex in descending order.\n\n");
                break;
            case 2:
                str.append("randomly sorted.\n\n");
                break;
            case 3:
                str.append("sorted by value of vertex in ascending order.\n\n");
        }
        for (int i : Main.sortMap.get(Main.fenetre.sort)) {
            str.append(graphe.get(i).getVertex()).append(" : ");
            for (Edge neighboor : graphe.get(i).successors) {
                str.append(neighboor.getVertex()).append("  ");
            }
            str.append("\n\n");
        }
        this.setText(str.toString());
        this.setCaretPosition(0);
        this.repaint();
        this.revalidate();
    }
}
