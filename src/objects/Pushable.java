package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public interface Pushable {
    boolean canBePushed(Room room, boolean direct);
    Point2D getPosition();
    void setPosition(Point2D position);
}