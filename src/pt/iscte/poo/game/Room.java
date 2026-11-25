package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import objects.*;

import pt.iscte.poo.utils.*;

public class Room {

    private List<GameObject> objects;
    private String roomName;
    private Baba player;

    private int cols;
    private int rows;
    private int moves;

    private EnumSet<BoxProperty> properties =
        EnumSet.noneOf(BoxProperty.class);

    /** Creates an empty room. */
    public Room() {
        objects = new ArrayList<>();
    }

    /** Defines the source file name. */
    private void setName(String name) {
        roomName = name;
    }

    /** Returns the source file name. */
    public String getName() {
        return roomName;
    }

    /** Adds an element to the room. */
    public void addObject(GameObject obj) {
        objects.add(obj);
    }

    /** Returns a snapshot of the game objects. */
    public List<GameObject> getObjects() {
        return List.copyOf(objects);
    }

    /** Returns the player. */
    public Baba getPlayer() {
        return player;
    }

    /** Returns successful player movements in this attempt. */
    public int getMoves() {
        return moves;
    }

    /** Checks whether a position belongs to the map. */
    public boolean isInside(Point2D position) {
        int x = position.getX();
        int y = position.getY();

        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    /** Returns every element at a position, including the floor. */
    public List<GameObject> getObjectsAt(Point2D position) {
        List<GameObject> result = new ArrayList<>();

        for (GameObject object : objects) {
            if (object.getPosition().equals(position)) {
                result.add(object);
            }
        }

        return result;
    }

    /** Checks whether a wall occupies a position. */
    public boolean isWall(Point2D position) {
        for (GameObject object : getObjectsAt(position)) {
            if (object instanceof Wall) {
                return true;
            }
        }

        return false;
    }

    /** Reads the map and the implemented object entries. */
    public static Room readRoom(File f) {
        Room r = new Room();
        r.setName(f.getName());

        try (Scanner scanner = new Scanner(f)) {
            if (!scanner.hasNextLine()) {
                throw new IllegalArgumentException("Empty room file.");
            }

            String dimensions = scanner.nextLine();
            String[] values = dimensions.split(",", -1);

            if (values.length != 2) {
                throw new IllegalArgumentException(
                    "Expected dimensions: cols,rows"
                );
            }

            r.cols = Integer.parseInt(values[0].trim());
            r.rows = Integer.parseInt(values[1].trim());

            if (r.cols <= 0 || r.rows <= 0) {
                throw new IllegalArgumentException(
                    "Room dimensions must be positive."
                );
            }

            for (int y = 0; y < r.rows; y++) {
                if (!scanner.hasNextLine()) {
                    throw new IllegalArgumentException(
                        "Missing map row: " + y
                    );
                }

                String linha = scanner.nextLine();

                if (linha.length() != r.cols) {
                    throw new IllegalArgumentException(
                        "Invalid map width at row " + y
                    );
                }

                for (int x = 0; x < r.cols; x++) {
                    char element = linha.charAt(x);
                    Point2D position = new Point2D(x, y);

                    r.addObject(new Dirt(position));

                    if (element == 'g') {
                        r.addObject(new Grass(position));
                    }

                    if (element == 'X') {
                        r.addObject(new Wall(position));
                    } else if (element == 'x') {
                        r.addObject(new CrackedWall(position));
                    }
                }
            }

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.isBlank()) {
                    continue;
                }

                String[] dados = linha.split(",", -1);

                if (dados.length < 3) {
                    throw new IllegalArgumentException(
                        "Invalid object entry: " + linha
                    );
                }

                String nome = dados[0].trim();
                int x = Integer.parseInt(dados[1].trim());
                int y = Integer.parseInt(dados[2].trim());

                Point2D position = new Point2D(x, y);

                if (!r.isInside(position)) {
                    throw new IllegalArgumentException(
                        "Object outside the map: " + linha
                    );
                }

                if (nome.equals("baba")) {
                    if (r.player != null || dados.length > 4) {
                        throw new IllegalArgumentException(
                            "Invalid or duplicate Baba: " + linha
                        );
                    }

                    String direction = "r";

                    if (dados.length == 4 && !dados[3].isBlank()) {
                        direction = dados[3].trim();
                    }

                    if (!direction.matches("[urdl]")) {
                        throw new IllegalArgumentException(
                            "Invalid Baba direction: " + linha
                        );
                    }

                    if (r.isWall(position)) {
                        throw new IllegalArgumentException(
                            "Baba starts inside a wall."
                        );
                    }

                    r.player = new Baba(position, direction);
                    r.addObject(r.player);

                } else {
                    if (dados.length != 3) {
                        throw new IllegalArgumentException(
                            "Expected name,x,y: " + linha
                        );
                    }

                    switch (nome) {
                        case "rock":
                            r.addObject(new Rock(position));
                            break;

                        case "box":
                            r.addObject(new Box(position));
                            break;

                        case "flag":
                            r.addObject(new Flag(position));
                            break;

                        case "wordBox":
                            r.addObject(new WordTile(position, WordType.BOX));
                            break;

                        case "wordIs":
                            r.addObject(new WordTile(position, WordType.IS));
                            break;

                        case "wordWin":
                            r.addObject(new WordTile(position, WordType.WIN));
                            break;

                        case "wordMove":
                            r.addObject(new WordTile(position, WordType.MOVE));
                            break;

                        case "wordStop":
                            r.addObject(new WordTile(position, WordType.STOP));
                            break;

                        case "bomb":
                            // Implemented in the next development stage.
                            break;

                        default:
                            throw new IllegalArgumentException(
                                "Unknown object: " + nome
                            );
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new IllegalStateException(
                "Room file not found: " + f.getPath(), e
            );
        }

        if (r.player == null) {
            throw new IllegalArgumentException(
                "Missing Baba in " + f.getName()
            );
        }

        r.evaluateRules();
        return r;
    }

    /** Checks a currently active box property. */
    public boolean hasProperty(BoxProperty property) {
        return properties.contains(property);
    }

    /** Rebuilds the properties from the current word positions. */
    public void evaluateRules() {
        properties.clear();

        for (GameObject object : objects) {
            if (object instanceof WordTile word
                    && word.getType() == WordType.BOX) {

                evaluateSentence(word.getPosition(), Direction.RIGHT);
                evaluateSentence(word.getPosition(), Direction.DOWN);
            }
        }
    }

    /** Reads a possible BOX IS property sentence in one direction. */
    private void evaluateSentence(Point2D start, Direction direction) {
        Point2D middle = start.plus(direction.asVector());
        Point2D end = middle.plus(direction.asVector());

        if (!hasWordAt(middle, WordType.IS)) {
            return;
        }

        if (hasWordAt(end, WordType.WIN)) {
            properties.add(BoxProperty.WIN);
        }

        if (hasWordAt(end, WordType.MOVE)) {
            properties.add(BoxProperty.MOVE);
        }

        if (hasWordAt(end, WordType.STOP)) {
            properties.add(BoxProperty.STOP);
        }
    }

    /** Checks for a specific word at a position. */
    private boolean hasWordAt(Point2D position, WordType type) {
        for (GameObject object : getObjectsAt(position)) {
            if (object instanceof WordTile word
                    && word.getType() == type) {
                return true;
            }
        }

        return false;
    }

    /** Checks the property combinations that require a restart. */
    public boolean hasRuleConflict() {
        return hasProperty(BoxProperty.MOVE)
            && (hasProperty(BoxProperty.STOP)
                || hasProperty(BoxProperty.WIN));
    }

    /** Tries a player movement and a possible chain of pushes. */
    public boolean tryMove(Direction direction) {
        Point2D destination =
            player.getPosition().plus(direction.asVector());

        Set<Pushable> plan = new LinkedHashSet<>();

        if (!planEntry(destination, direction, true, false, plan)) {
            return false;
        }

        for (Pushable object : plan) {
            Point2D next = object.getPosition().plus(direction.asVector());

            object.setPosition(next);
        }

        player.move(direction);
        moves++;
        evaluateRules();

        return true;
    }

    /** Validates the whole chain before any position is changed. */
    private boolean planEntry(
            Point2D destination,
            Direction direction,
            boolean direct,
            boolean previousWasWord,
            Set<Pushable> plan) {

        if (!isInside(destination) || isWall(destination)) {
            return false;
        }

        for (GameObject object : getObjectsAt(destination)) {
            if (object instanceof Pushable pushable
                    && pushable.canBePushed(this, direct)) {

                if (previousWasWord && object instanceof WordTile) {
                    return false;
                }

                Point2D next =
                    destination.plus(direction.asVector());

                boolean possible = planEntry(
                    next,
                    direction,
                    false,
                    object instanceof WordTile,
                    plan
                );

                if (!possible) {
                    return false;
                }

                plan.add(pushable);

            } else if (object instanceof Box
                    && hasProperty(BoxProperty.STOP)) {
                return false;
            }
        }

        return true;
    }

    /** Checks the default objective and the BOX IS WIN objective. */
    public boolean isWon() {
        for (GameObject object : getObjectsAt(player.getPosition())) {
            if (object instanceof Flag) {
                return true;
            }

            if (object instanceof Box && hasProperty(BoxProperty.WIN)) {
                return true;
            }
        }

        return false;
    }
}