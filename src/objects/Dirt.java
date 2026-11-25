package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Dirt extends Terrain { 

    public Dirt(Point2D position) {
        super(position, 5);
    }

    @Override
    public String getName() {
        return "dirt";
    }

}
