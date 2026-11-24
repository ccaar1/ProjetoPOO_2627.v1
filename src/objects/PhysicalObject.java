package objects;

import pt.iscte.poo.utils.Point2D;

//  Base class for non-word objects listed after the terrain grid.
public abstract class PhysicalObject extends GameObject {
    /** Creates an object above the terrain. */
    protected PhysicalObject(Point2D position) {
        super(position, 15);
    }
}