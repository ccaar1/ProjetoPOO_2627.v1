package objects;

import pt.iscte.poo.utils.Point2D;

public final class Hole extends Terrain {
    public Hole(Point2D position) {
        super(position, 10);
    }

    @Override
    public String getName() {
        return "hole";
    }
}