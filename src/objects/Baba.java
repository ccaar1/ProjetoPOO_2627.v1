package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Baba extends GameObject {

    private String direction;

    public Baba(Point2D position) {
        this(position, "r");
    }

    public Baba(Point2D position, String direction) {
        super(position, 25);
        this.direction = direction;
    }

    @Override
    public String getName() {
        return "baba-" + direction;
    }

    public void move(Direction d) { //inicialmente estava como random mas nao queremos isso, queremos que seja o jogador a controlar a personagem
        super.setPosition(super.getPosition().plus(d.asVector()));
        direction = d.toString().toLowerCase().substring(0, 1); //pega na primeira letra da direção e converte para minuscula

    }
}
