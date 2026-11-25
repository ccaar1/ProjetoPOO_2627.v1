package objects;

import pt.iscte.poo.utils.Point2D;

// classe do objeto pedra
public final class Rock extends PhysicalObject implements Pushable {

    public Rock(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "rock";
    }

    @Override
    public boolean canBePushed(pt.iscte.poo.game.Room room, boolean direct) {
        return true;
    }
}
