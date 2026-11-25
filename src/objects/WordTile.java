package objects;

import java.util.Objects;

import pt.iscte.poo.utils.Point2D;

//
public final class WordTile extends GameObject implements Pushable {
    private final WordType type;


    public WordTile(Point2D position, WordType type) {
        super(position, 20);
        this.type = Objects.requireNonNull(type);
    }

    public WordType getType() {
        return type;
    }


    @Override
    public boolean blocksEntry() {
        return true;
    }


    @Override
    public String getName() {
        return type.getSpriteName();
    }

    @Override
    public boolean canBePushed(pt.iscte.poo.game.Room room, boolean direct) {
        return true;
    }
}
