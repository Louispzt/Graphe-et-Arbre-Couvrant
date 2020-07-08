import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedVertex extends Pos{
    private final int vertexNb;
    private LinkedVertex parent;
    public Map<LinkedVertex, Integer> childMap;
    public int level;

    public LinkedVertex(int vertexNb, LinkedVertex parent) {
        super(0,0, parent);
        this.vertexNb = vertexNb;
        childMap = new LinkedHashMap<>();
        this.parent = parent;
        if (parent == null)
            level = -1;
        else
            level = parent.level + 1;
    }

    public void addChild(LinkedVertex child, int value){
        childMap.put(child, value);
    }

    public int getVertexNb() {
        return vertexNb;
    }

    public LinkedVertex getParent() {
        return parent;
    }

    public Map<LinkedVertex, Integer> getChildMap() {
        return childMap;
    }

    @Override
    public String toString() {
        if (!childMap.isEmpty())
            return "(" + vertexNb +
                    "_" + level +
                    "{" + x + "," + y +
                    "}) to " + childMap;
        return "("+ vertexNb +
                "_" + level +
                "{" + x + "," + y +
                "})";
    }
}
