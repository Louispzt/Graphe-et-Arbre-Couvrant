public class Pos {
    public int x;
    public int y;
    private Pos parent;

    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Pos(int x, int y, Pos parent){
        this(x,y);
        this.parent = parent;
    }

    public Pos getParent() {
        return parent;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ')';
    }
}
