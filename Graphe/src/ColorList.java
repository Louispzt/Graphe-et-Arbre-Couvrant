import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorList {
    private int i;
    private int h;
    private List<Integer> errors;
    private ArrayList<Color> colorList;
    private ArrayList<Color> darkColorList;
    private ArrayList<Color> invColorList;
    private ArrayList<Color> darkInvColorList;

    public ColorList(){
        colorList = new ArrayList<>();
        invColorList = new ArrayList<>();
        darkColorList = new ArrayList<>();
        darkInvColorList = new ArrayList<>();
        for (int j = 0; j < 3; j++){
            colorList.add(Color.getHSBColor(j/3f,1,1));
            darkColorList.add(Color.getHSBColor(j/3f,1,0.3f));
            float hue = (1/2f + j/3f <= 1 ? 1/2f + j/3f : 1/2f + j/3f - 1);
            invColorList.add(Color.getHSBColor(hue,1,1));
            darkInvColorList.add(Color.getHSBColor(hue,1,0.3f));
        }
        errors = Arrays.asList(831, 957 - 1, 1085 - 2, 1211 - 3, 1339 - 4, 1465 - 5);
        i = 1;
        h = 6;
    }

    public void makeLenN(int num_colors){
        if (colorList.size() >= num_colors)
            return;
        for (i = 1; colorList.size() < num_colors; i+=2){
            if (i > h){
                h*=2;
                i=1;
            }
            colorList.add(Color.getHSBColor(i/(float) h,1,1));
            darkColorList.add(Color.getHSBColor(i/(float) h,1,0.5f));
            invColorList.add(Color.getHSBColor(1/2f + i/(float) h,1,1f));
            darkInvColorList.add(Color.getHSBColor(1/2f + i/(float) h,1,0.5f));
        }
        for (int error : errors){
            if (colorList.size() >= error) {
                colorList.remove(error);
                darkColorList.remove(error);
                invColorList.remove(error);
                darkInvColorList.remove(error);
                errors.remove(0);
            }
        }
    }

    public ArrayList<Color> getColorList() {
        return colorList;
    }

    public Color getColor(int i) {
        return colorList.get(i);
    }

    public Color getInvColor(int i) {
        return invColorList.get(i);
    }

    public Color getDarkColor(int i) {
        return darkColorList.get(i);
    }

    public Color getDarkInvColor(int i) {
        return darkInvColorList.get(i);
    }
}
