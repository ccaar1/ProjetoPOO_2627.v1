package objects;

import java.util.Objects;

import pt.iscte.poo.utils.Point2D;

/** A word on the board that can participate in a rule. */
public final class WordTile extends GameObject implements Pushable {
    private final WordType type;

    /** Creates a word tile of the supplied type. */
    public WordTile(Point2D position, WordType type) {
        super(position, 20);
        this.type = Objects.requireNonNull(type);
    }

    /** Returns the word represented by this tile. */
    public WordType getType() {
        return type;
    }

    /** Blocks entry unless movement rules allow pushing this object. */
    @Override
    public boolean blocksEntry() {
        return true;
    }

    /** Returns the sprite name for this word. */
    @Override
    public String getName() {
        return type.getSpriteName();
    }
    /** Determines whether this object accepts a direct or transmitted push. */
    @Override
    public boolean canBePushed(pt.iscte.poo.game.Room room, boolean direct) {
        return true;
    }
}
