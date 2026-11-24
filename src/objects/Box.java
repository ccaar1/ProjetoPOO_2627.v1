package objects;

import pt.iscte.poo.utils.Point2D;

//class para a box
public final class Box extends PhysicalObject implements Pushable {

    public Box(Point2D position) {
        super(position);
    }


    @Override
    public String getName() {
        return "box";
    }

    @Override
    public boolean canBePushed(pt.iscte.poo.game.Room room, boolean direct) {
        return room.hasProperty(objects.BoxProperty.MOVE);
    }
}
