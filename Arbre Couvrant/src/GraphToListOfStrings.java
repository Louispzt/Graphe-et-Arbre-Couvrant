import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GraphToListOfStrings {

    ArrayList<ArrayList<Integer>> arrayLists;

    public GraphToListOfStrings(){
        arrayLists = new ArrayList<>();
    }

    public void open(String URL) throws IOException{
        File file = new File(URL);
        //System.out.println(file.exists());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String str = "";
        while ((line = br.readLine()) != null) {
            if (!line.startsWith("c")){
                if (line.startsWith("p")){
                    String[] words = line.replaceAll("[^\\d.]+ ", "").split(" ");
                    arrayLists.add(new ArrayList<>());
                    arrayLists.get(0).add(Integer.parseInt(words[0]));
                    arrayLists.get(0).add(Integer.parseInt(words[1]));
                }
                if (line.startsWith("e")){
                    String[] getEdges = line.replaceAll("[^\\d.]+ ", "").split(" ");
                    arrayLists.add(new ArrayList<>());
                    arrayLists.get(arrayLists.size() - 1).add(Integer.parseInt(getEdges[0]));
                    arrayLists.get(arrayLists.size() - 1).add(Integer.parseInt(getEdges[1]));
                    if (getEdges.length > 2) {
                        arrayLists.get(arrayLists.size() - 1).add(Integer.parseInt(getEdges[2]));
                    }
                    else{
                        arrayLists.get(arrayLists.size() - 1).add(-1);
                    }
                }
            }
        }
        br.close();
    }

    public ArrayList<ArrayList<Integer>> getArrayLists() {
        return arrayLists;
    }

    public String toString(){
        String str = "";
        for (int i = 0; i < arrayLists.size(); i++){
            str += "{" + arrayLists.get(i).get(0) + " " + arrayLists.get(i).get(1) + "} ";
        }
        return str;
    }
}