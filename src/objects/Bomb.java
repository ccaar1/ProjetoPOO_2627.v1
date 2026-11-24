package objects;

import pt.iscte.poo.utils.Point2D;

//representa a bomba
public final class Bomb extends PhysicalObject implements Pushable {
    // criar na posição fornecida
    public Bomb(Point2D position) {
        super(position);
    }

    //devolve o nome da sprite
    @Override
    public String getName() {
        return "bomb";
    }
    //determina se pode ser empurrado
    @Override
    public boolean canBePushed(pt.iscte.poo.game.Room room, boolean direct) {
        return true;
    }
}