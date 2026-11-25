package objects;


public enum WordType {
    BOX("word-box"), IS("word-is"), WIN("word-win"),
    MOVE("word-move"), STOP("word-stop");

    private final String spriteName;


    WordType(String spriteName) {
        this.spriteName = spriteName;
    }


    public String getSpriteName() {
        return spriteName;
    }
}