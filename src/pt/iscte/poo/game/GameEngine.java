package pt.iscte.poo.game;

import java.io.File;

import objects.Baba;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

    private static final GameEngine INSTANCE = new GameEngine();
    private static final String ROOMS_DIRECTORY = "rooms";

    private final File roomFile = new File(ROOMS_DIRECTORY, "room0.txt");
    private Room currentRoom;
    private int lastTickProcessed = 0;

    private GameEngine() {
        currentRoom = Room.readRoom(roomFile);
        updateGUI(); //atualizar GUI e importante
    }

	public static GameEngine getInstance() {
		return INSTANCE;
	}

    @Override
    public void update(Observed source) {
        ImageGUI gui = ImageGUI.getInstance();

        if (gui.wasKeyPressed()) {
            int key = gui.keyPressed();
            if (Direction.isDirection(key) && !currentRoom.isWon()
                    && currentRoom.tryMove(Direction.directionFor(key))) {
                boolean restarted = currentRoom.hasRuleConflict();
                if (restarted) {
                    currentRoom = Room.readRoom(roomFile);
                }

                updateGUI();
                if (restarted) {
                    gui.setStatusMessage("Regras incompatíveis. " + statusMessage());
                }
            }
        }

        int tick = gui.getTicks();
        while (lastTickProcessed < tick) {
            processTick();
        }
        gui.update();
    }

    private void processTick() {
        lastTickProcessed++;
    }

    public void updateGUI() {
        if (currentRoom != null) {
            ImageGUI.getInstance().clearImages();
            ImageGUI.getInstance().addImages(currentRoom.getObjects());
            ImageGUI.getInstance().setStatusMessage(statusMessage());
        }
    }


    private String statusMessage() {
        String moves = "Movimentos: " + currentRoom.getMoves();
        return currentRoom.isWon() ? "Nível concluído! " + moves : moves;
    }

    public Baba getPlayer() {
        return currentRoom.getPlayer();
    }
}
