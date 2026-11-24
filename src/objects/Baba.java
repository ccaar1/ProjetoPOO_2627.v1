package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Baba extends GameObject {

    private String direction = "r";

    public Baba(Point2D position) {
        super(position, 25);
    }

    @Override
    public String getName() {
        return "baba-" + direction;
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public void move(Direction d) { //inicialmente estava como random mas nao queremos isso, queremos que seja o jogador a controlar a personagem
        super.setPosition(super.getPosition().plus(d.asVector()));
        direction = d.toString().toLowerCase().substring(0, 1); //pega na primeira letra da direção e converte para minuscula

    }
}

//GameObject
//Word
//WordIs