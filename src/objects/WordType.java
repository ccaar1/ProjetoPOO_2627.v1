package objects;

/** The word tokens recognised by the level format. */
public enum WordType {
    BOX("word-box"), IS("word-is"), WIN("word-win"),
    MOVE("word-move"), STOP("word-stop");

    private final String spriteName;

    /** Associates a token with its sprite. */
    WordType(String spriteName) {
        this.spriteName = spriteName;
    }

    /** Returns the sprite name without its extension. */
    public String getSpriteName() {
        return spriteName;
    }
}