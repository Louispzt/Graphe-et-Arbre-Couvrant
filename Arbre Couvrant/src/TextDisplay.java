import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TextDisplay extends JTextPane {

    public TextDisplay(String str){
        super();
        this.setMargin(new Insets(5,5,5,5));
        this.setFont(new Font(Font.SANS_SERIF, 3, 20));;
        this.setForeground(Color.white);
        this.setText(str);
        this.setCaretPosition(0);
        this.setBackground(new Color(50,50,50));
    }

    public TextDisplay(){
        super();
        this.setFont(new Font(super.getFont().getName(), super.getFont().getStyle(), 14));;
        this.setMargin(new Insets(5,5,5,5));
        this.setForeground(Color.white);
        reloadText();
        this.setBackground(new Color(50,50,50));
    }

    public void reloadText(){
        StringBuilder str = new StringBuilder();
        str.append("This graphe has ").append(Main.nbOfVertex).append(" vertex and ").append(Main.nbOfEdges).append(" edges\n\n");
        for (Vertex vertex : Main.graphe) {
            str.append(vertex.toString());
            str.append("\n\n");
        }
        this.setText(str.toString());
        this.setCaretPosition(0);
        this.repaint();
        this.revalidate();
    }
}
