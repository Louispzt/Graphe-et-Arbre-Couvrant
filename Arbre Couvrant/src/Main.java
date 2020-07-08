import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    static ArrayList<Vertex> graphe;
    static ArrayList<MSTAlg> graphList;
    static ArrayList<ArrayList<Integer>> sortMap;
    static DisplayGraphe mouseDragTest;
    static Fenetre fenetre;
    static int nbOfVertex;
    static int nbOfEdges;
    static String file;
    static Kruskal1 kruskal1;
    static MSTAlg prim;
    static MSTAlg kruskal2;
    static MSTAlg dmst1;
    static MSTAlg dmst2;
    static int degree;

    public static void main(String[] args) throws IOException {
        degree = 2;
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
        dialog.setFile("*.mst");
        dialog.setVisible(true);
        String directory = dialog.getDirectory();
        if (dialog.getFile() == null){
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
        else{
            file = dialog.getFile();
        }
        text.open(directory + file);

        ArrayList<ArrayList<Integer>> arrayMST = text.getArrayLists();

        System.out.println(arrayMST);

        graphe.clear();
        graphList.clear();
        sortMap.clear();
        nbOfVertex = arrayMST.get(0).get(0);
        nbOfEdges = 0;

        for (int i = 0; i < arrayMST.get(0).get(0); i++){
            graphe.add(new Vertex(i+1));
        }

        for (int i = 1; i < arrayMST.size(); i++){
            graphe.get(arrayMST.get(i).get(0) - 1).addEdge(graphe.get(arrayMST.get(i).get(1) - 1), arrayMST.get(i).get(2));
            graphe.get(arrayMST.get(i).get(1) - 1).addEdge(graphe.get(arrayMST.get(i).get(0) - 1), arrayMST.get(i).get(2));
            nbOfEdges+=2;
        }

        long t1 = System.nanoTime();
        kruskal1 = new Kruskal1();
        System.out.println((System.nanoTime() - t1)/1000000 + "ms for " + kruskal1.toString());

        t1 = System.nanoTime();
        kruskal2 = new Kruskal2();
        System.out.println((System.nanoTime() - t1)/1000000 + "ms for " + kruskal2.toString());

        t1 = System.nanoTime();
        prim = new Prim();
        System.out.println((System.nanoTime() - t1)/1000000 + "ms for " + prim.toString());

        t1 = System.nanoTime();
        dmst1 = new DMST1(degree);
        System.out.println((System.nanoTime() - t1)/1000000 + "ms for " + dmst1.toString());

        t1 = System.nanoTime();
        dmst2 = new DMST1(degree);
        System.out.println((System.nanoTime() - t1)/1000000 + "ms for " + dmst2.toString());

        graphList.addAll(Arrays.asList(kruskal1, kruskal2, prim, dmst1, dmst2));
    }
}
