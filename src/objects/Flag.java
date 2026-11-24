package objects;

import pt.iscte.poo.utils.Point2D;

public final class Flag extends PhysicalObject implements Pushable {
    //cria na posição fornecida
    public Flag(Point2D position) {
        super(position);
    }

    //devolve o nome da sprite
    @Override
    public String getName() {
        return "flag";
    }
    //determina se o objeto pode ser empurrado
    @Override
    public boolean canBePushed(pt.iscte.poo.game.Room room, boolean direct) {
        return !direct;
    }
}