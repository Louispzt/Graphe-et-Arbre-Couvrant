import java.util.ArrayList;
import java.util.List;

public class Dsatur_degree extends ColorMethod {
    ListGrah listGrah;
    ArrayList<Integer> sortList;
    int[] dsats;

    public Dsatur_degree(ArrayList<Integer> sortList){
        this.sortList = sortList;
        colorList = new int[sortList.size()];
        dsats = new int[sortList.size()];
        nbColor = 0;
        listGrah = color();
    }

    public ListGrah color(){
        boolean init = false;
        int val;
        List<Integer> maxDSAT = new ArrayList<>();
        while (!sortList.isEmpty()){
            maxDSAT.clear();
            val = -1;               //-1 car si un sommet n'a pas de successeur, son dsat sera de 0
            for (int i : sortList){
                if (!init) {
                    dsats[i] = Main.graphe.get(i).successors.size();
                }
                if (dsats[i] > val){
                    maxDSAT.clear();
                    maxDSAT.add(i);
                    val = dsats[i];
                }
                else if (dsats[i] == val)
                    maxDSAT.add(i);
            }
            init = true;
            int maxDegree = -1;     //même raisonnement
            int ind = 0;
            for (int i : maxDSAT){      //on récupère l'indice du sommet de degré maximal
                if (Main.graphe.get(i).successors.size() > maxDegree){
                    maxDegree = Main.graphe.get(i).successors.size();
                    ind = i;
                }
            }
            boolean[] colorUsed = new boolean[nbColor + 1];         //On prends nbColor + 1 pour faciliter le test en dessous.
            for (Edge edge : Main.graphe.get(ind).successors){      //On passe sur tous les sommets et on met leur couleur dans colorUsed
                if (colorList[edge.getVertex() - 1] > 0)
                    colorUsed[colorList[edge.getVertex() - 1] - 1] = true;
            }
            int i = 0;
            while(colorUsed[i])     //on cherche l'indice i de la couleur qui n'a pas été utilisé, couleur = utiliser = i + 1;
                i++;
            colorList[ind] = i+1;
            for (Edge edge : Main.graphe.get(ind).successors){ //incrementer dsats de tous les voisins sans compter les couleurs -> probant.
                int n = 1;
                int nbcolor = 0;
                for (Edge neigh : Main.graphe.get(edge.getVertex() - 1).successors){
                    if (colorList[neigh.getVertex() - 1] != 0){
                        nbcolor ++;
                        if (colorList[neigh.getVertex() - 1] == i + 1 && neigh.getVertex() - 1 != ind){
                            n=0;
                            break;
                        }
                    }
                }
                if (nbcolor == n){ //nbcolor = 1 et n =1
                    dsats[edge.getVertex() - 1] = 1;
                }
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
