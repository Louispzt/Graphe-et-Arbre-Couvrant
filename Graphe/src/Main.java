import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static ArrayList<Edge> graphe;
    static ArrayList<ArrayList<ColorMethod>> graphList;
    static ArrayList<ArrayList<Integer>> sortMap;
    static DisplayGraphe mouseDragTest;
    static Fenetre fenetre;
    static JFrame f;//a enelver inutile
    static int nbOfVertex;
    static int nbOfEdges;
    static String file;

    public static void main(String[] args) throws IOException {
        graphe = new ArrayList<>();
        graphList = new ArrayList<>();
        sortMap = new ArrayList<>();

        reload();
        mouseDragTest = new DisplayGraphe();
        fenetre = new Fenetre(mouseDragTest);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setTitle("Projet Graphe");
    }

    public static void reload() throws IOException {
        GraphToListOfStrings text = new GraphToListOfStrings();
        FileDialog dialog = new FileDialog((Frame)null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setFile("*.col");
        dialog.setVisible(true);
        file = dialog.getFile();
        String directory = dialog.getDirectory();
        if (file == null){
            if (graphe.isEmpty()){
                JInternalFrame frame = new JInternalFrame();
                JOptionPane.showMessageDialog(frame,
                        "File not found or not specified",
                        "Error file not found",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }
            JInternalFrame frame = new JInternalFrame();
            JOptionPane.showMessageDialog(frame,
                    "File not found or not specified.\nUsing old one.",
                    "Error file not found",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        text.open(directory + file);

        ArrayList<ArrayList<Integer>> arrayLists = text.getArrayLists();

        graphe.clear();
        graphList.clear();
        sortMap.clear();
        nbOfVertex = arrayLists.get(0).get(0);
        nbOfEdges = arrayLists.get(0).get(1);

        for (int i = 0; i < arrayLists.get(0).get(0); i++){
            graphe.add(new Edge(i+1));
        }
        for (int i = 1; i < arrayLists.size(); i++){
            graphe.get(arrayLists.get(i).get(0) - 1).addSuccessor(graphe.get(arrayLists.get(i).get(1) - 1));
            graphe.get(arrayLists.get(i).get(1) - 1).addSuccessor(graphe.get(arrayLists.get(i).get(0) - 1));
        }

        for (int i = 0; i < 4; i++){
            graphList.add(new ArrayList<>());
        }

        for (SortEnumerator se : SortEnumerator.values()){
            ArrayList<Integer> sortList = new QuickSort(se).sortList;
            System.out.println(se + " " + sortList.toString());
            long t1 = System.nanoTime();
            graphList.get(0).add(new GreedyColoring(sortList));
            System.out.println("Greedy done in " + (System.nanoTime() - t1)/1000000d + " ms");
            t1 = System.nanoTime();
            graphList.get(1).add(new WelshPowell(new ArrayList<>(sortList)));
            System.out.println("WP done in " + (System.nanoTime() - t1)/1000000d + " ms");
            t1 = System.nanoTime();
            graphList.get(2).add(new Dsatur0(new ArrayList<>(sortList)));
            System.out.println("DSATUR0 done in " + (System.nanoTime() - t1)/1000000d + " ms");
            t1 = System.nanoTime();
            graphList.get(3).add(new Dsatur_degree(new ArrayList<>(sortList)));
            System.out.println("DSATUR_degree done in " + (System.nanoTime() - t1)/1000000d + " ms");
            sortMap.add(new ArrayList<>(sortList));
        }
    }
}
