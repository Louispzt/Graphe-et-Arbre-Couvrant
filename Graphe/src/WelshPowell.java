import java.util.ArrayList;

public class WelshPowell extends ColorMethod {
    ListGrah listGrah;

    public WelshPowell(ArrayList<Integer> sortList){
        colorList = new int[sortList.size()];
        listGrah = color(sortList);
    }

    public ListGrah color(ArrayList<Integer> sortList){
        nbColor = 0;
        boolean sameColor;
        ArrayList<Integer> toRemove = new ArrayList<>();
        while (!sortList.isEmpty()){
            nbColor++;
            colorList[sortList.get(0)] = nbColor;
            sortList.remove(0);
            toRemove.clear();
            for (int i : sortList) {
                sameColor = false;
                for (Edge successor : Main.graphe.get(i).successors) {
                    if (colorList[successor.getVertex() - 1] == nbColor) {
                        sameColor = true;
                        break;
                    }
                }
                if (!sameColor) {
                    colorList[i] = nbColor;
                    toRemove.add(i);
                }
            }
            sortList.removeAll(toRemove);
        }
        return new ListGrah(colorList, nbColor);
    }
}




