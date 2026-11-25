package objects;

import pt.iscte.poo.utils.Point2D;

public final class Grass extends Terrain {

    public Grass(Point2D position) {
        super(position, 5);
    }

    @Override
    public String getName() {
        return "grass";
    }
}
