package sh.vertex.ldcdviewer.ui.building;

public class FloorJsonObject {
    private Integer w;

    private Integer x;

    private Integer h;

    private Integer y;

    private Integer type;

    private String Name;

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    @Override
    public String toString() {
        return "ClassPojo [w = " + w + ", x = " + x + ", h = " + h + ", y = " + y + ", type = " + type + ", Name = " + Name + "]";
    }
}
