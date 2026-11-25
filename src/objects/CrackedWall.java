package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class CrackedWall extends Terrain {
    
    public CrackedWall(Point2D position) {
        super(position, 10);
    }

    @Override
    public String getName() {
        return "cracked-wall";
    }

}
