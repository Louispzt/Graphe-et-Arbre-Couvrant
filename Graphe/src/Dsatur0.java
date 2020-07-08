import java.util.ArrayList;
import java.util.List;

public class Dsatur0 extends ColorMethod {
    ListGrah listGrah;
    ArrayList<Integer> sortList;
    int[] dsats;

    public Dsatur0(ArrayList<Integer> sortList){
        this.sortList = sortList;
        colorList = new int[sortList.size()];       //initialise tous les éléments à 0, donc aussi le nombre de voisins coloriés, le DSAT est donc déjà initialisé.
        dsats = new int[sortList.size()];
        nbColor = 0;
        listGrah = color();
    }

    public ListGrah color(){
        List<Integer> maxDSAT = new ArrayList<>();
        int maxDegree;
        int ind;
        while (!sortList.isEmpty()) {
            maxDSAT.clear();
            int val = -1;               //-1 car si un sommet n'a pas de successeur, son dsat sera de 0
            for (int i : sortList) {
                if (dsats[i] > val) {   //recherche de la liste des sommets ayant un dsat maximal, en O(n)
                    maxDSAT.clear();
                    maxDSAT.add(i);
                    val = dsats[i];
                } else if (dsats[i] == val)
                    maxDSAT.add(i);
            }
            maxDegree = -1;             //même raisonnement
            ind = 0;
            for (int i : maxDSAT) {     //on récupère l'indice du premier sommet ayant un degré maximal
                if (Main.graphe.get(i).successors.size() > maxDegree) {
                    maxDegree = Main.graphe.get(i).successors.size();
                    ind = i;
                }
            }
            boolean[] colorUsed = new boolean[nbColor + 1];         //On prends nbColor + 1 pour faciliter le test en dessous.
            for (Edge edge : Main.graphe.get(ind).successors) {      //On passe sur tous les sommets et on met leur couleur dans colorUsed
                if (colorList[edge.getVertex() - 1] > 0)
                    colorUsed[colorList[edge.getVertex() - 1] - 1] = true;
            }
            int i = 0;
            while (colorUsed[i])     //on cherche l'indice i de la couleur qui n'a pas été utilisé;
                i++;
            colorList[ind] = i + 1;
            for (Edge edge : Main.graphe.get(ind).successors){ //incrementer dsats de tous les voisins.
                boolean newColor = true;
                for (Edge neighbor : Main.graphe.get(edge.getVertex() - 1).successors){ //avant d'incrémenter, on vérifie qu'aucun de ses voisins n'a déjà cette couleur.
                    if (colorList[neighbor.getVertex() - 1] == i + 1 && neighbor.getVertex() - 1 != ind){
                        newColor = false;
                        break;
                    }
                }
                if (newColor)
                    dsats[edge.getVertex() - 1]++;
            }
            sortList.remove(Integer.valueOf(ind));
            if (i+1 > nbColor)
                nbColor = i+1;
        }
        return new ListGrah(colorList, nbColor);
    }

    @Override
    public String toString() {
        ColorListToString colorListToString = new ColorListToString(colorList);
        return "DSATUR :\n" +
                "colorList = " + colorListToString.str + "\n" +
                "nbcolor = " + nbColor + "\n";
    }
}
