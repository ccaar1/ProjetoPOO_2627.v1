package objects;

import pt.iscte.poo.utils.Point2D;

//classe abstrata para os elementos do terreno, pensei para Dirt,Grass,Wall,CrackedWall,Hole
public abstract class Terrain extends GameObject {
    protected Terrain(Point2D position, int layer) {
        super(position, layer);
    }
}