package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Wall extends Terrain {

    public Wall(Point2D position) {
        super(position, 10);
    }

    @Override
    public String getName() {
        return "wall";
    }

}
