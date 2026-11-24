package objects;

import pt.iscte.poo.utils.Point2D;

/** Represents the rock tile loaded from a level. */
public final class Rock extends PhysicalObject implements Pushable {
    /** Creates the tile at the supplied position. */
    public Rock(Point2D position) {
        super(position);
    }

    /** Returns the sprite name. */
    @Override
    public String getName() {
        return "rock";
    }
    /** Determines whether this object accepts a direct or transmitted push. */
    @Override
    public boolean canBePushed(pt.iscte.poo.game.Room room, boolean direct) {
        return true;
    }
}
