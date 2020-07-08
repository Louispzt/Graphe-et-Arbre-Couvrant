public class ColorListToString {
    String str;
    public ColorListToString(int[] colorList){
        str = "";
        StringBuilder strBuilder = new StringBuilder("[");
        for (int i = 0; i< colorList.length; i++){
            strBuilder.append(i + 1).append("->").append(colorList[i]).append(", ");
        }
        if (colorList.length > 0)
            str = strBuilder.substring(0, strBuilder.length() - 2) + "]";
    }
}
