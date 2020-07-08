
import java.awt.*;

public class OvalString{
    public double x;
    public double y;
    public int ovalW;
    public int ovalH;
    public int ovalX;
    public int ovalY;
    public int wordX;
    public int wordY;
    public int weight;
    public int vertex;

    public OvalString(double x, double y, int vertex, int weight){
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.vertex = vertex;
    }

    public void update(){
        FontMetrics fm = DisplayGraphe.fm;
        ovalW = 40 + fm.stringWidth(String.valueOf(vertex));
        ovalH = 20 + fm.getMaxAscent();
        ovalX = (int) x - ovalW / 2;
        ovalY = (int) y - ovalH / 2;
        wordX = (int) x + 20 - ovalW / 2 ;
        wordY = (int) y + fm.getMaxAscent() / 3;
    }

    public void update(double dx, double dy){
        x += dx;
        y += dy;
        update();
    }

    @Override
    public String toString() {
        return "OvalString{" +
                "x=" + x +
                ", y=" + y +
                ", ovalW=" + ovalW +
                ", ovalH=" + ovalH +
                ", ovalX=" + ovalX +
                ", ovalY=" + ovalY +
                ", wordX=" + wordX +
                ", wordY=" + wordY +
                ", word='" + vertex + '\'' +
                '}';
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
