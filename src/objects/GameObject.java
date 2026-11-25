package objects;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile {

    private Point2D position;
    private final int layer;

    public GameObject(Point2D position, int layer) {
        this.position = position;
        this.layer = layer;
    }

    public void setPosition(Point2D position) {

        this.position = position;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    //devolve se a tile bloqueia a passagem sem ser empurrada
    public boolean blocksEntry() {
        return false;
    }

    @Override
    public int getLayer() {
        return layer;
    }

}
