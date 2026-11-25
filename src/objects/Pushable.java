package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

//interface que contém as regras que determinam se pode ser movído
public interface Pushable {
    boolean canBePushed(Room room, boolean direct);
    Point2D getPosition();
    void setPosition(Point2D position);
}