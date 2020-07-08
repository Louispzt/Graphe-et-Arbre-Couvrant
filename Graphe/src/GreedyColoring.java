import java.util.ArrayList;

public class GreedyColoring extends ColorMethod {
    ListGrah listGrah;
    ArrayList<Integer> sortList;

    public GreedyColoring(ArrayList<Integer> sortList){
        this.sortList = sortList;
        colorList = new int[sortList.size()];
        nbColor = 0;
        listGrah = color();
    }

    public ListGrah color(){
        for (int i : sortList){
            ArrayList<Edge> successors = Main.graphe.get(i).successors;
            boolean[] colorUsed = new boolean[nbColor + 1];
            for (Edge edge : successors){   //On passe sur tous les sommets et on met leur couleur dans colorUsed
                if (colorList[edge.getVertex() - 1] > 0)
                    colorUsed[colorList[edge.getVertex() - 1] - 1] = true;
            }
            int col = 0;
            while(colorUsed[col]) //on cherche l'indice i de la couleur qui n'a pas été utilisé, couleur = utiliser = i + 1;
                col++;
            colorList[i] = col+1;
            if (col+1 > nbColor)
                nbColor = col+1;
        }
        return new ListGrah(colorList, nbColor);
    }

    @Override
    public String toString() {
        ColorListToString colorListToString = new ColorListToString(colorList);
        return "GreedyColoring :\n" +
                "colorList = " + colorListToString.str + "\n" +
                "nbcolor = " + nbColor + "\n";
    }
}
