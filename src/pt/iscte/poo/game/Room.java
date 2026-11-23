package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import objects.Baba;
import objects.CrackedWall;
import objects.Dirt;
import objects.Wall;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Room {

    private List<ImageTile> objects;
    private String roomName;
    private Baba player;

    public Room() {
        objects = new ArrayList<>();
    }

    private void setName(String name) {
        roomName = name;
    }

    public String getName() {
        return roomName;
    }

    public void addObject(ImageTile obj) {
        objects.add(obj);
    }

    public List<ImageTile> getObjects() {
        return objects;
    }

    public static Room readRoom(File f) { //o starter body cria uma sala mas queremos que leia o ficheiro e crie a sala com base no que está no ficheiro
        Room r = new Room();
        r.setName(f.getName());

        try (Scanner scanner = new Scanner(f)) {

            String dimensions = scanner.nextLine(); //dimensoes do nivel
            String[] values = dimensions.split(","); //separa as dimensoes em colunas e linhas

            int cols = Integer.parseInt(values[0]); //primeiro valor de (a, b) string para int 
            int rows = Integer.parseInt(values[1]); //segundo valor de (a, b) string para int

        
            for (int y = 0; y < rows; y++) { //percorre as n linhas declaradas nas dimensoesdo ficheiro, transformando cada caracter em um objeto correspondente na sala
                String linha = scanner.nextLine();

                for (int x = 0; x < cols; x++) { //percorre as colunas da linha
                    char element = linha.charAt(x);
                    Point2D position = new Point2D(x, y);

                    r.addObject(new Dirt(position));

                    
                    if (element == 'X') { //identifica elementos no map
                        r.addObject(new Wall(position));
                    }
                    else if (element == 'x') {
                        r.addObject(new CrackedWall(position));
                    }
                }
            } 

            //depois de ler o mapa, le o resto do ficheiro onde sao dadas coordernadas de objetos. 
            //aqui tbm lemos os restantes objetos ainda nao implementados. bomb, flag etc
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();

                if (linha.isBlank()) {
                    continue;
                }

                String[] dados = linha.split(",");

                String nome = dados[0];
                int x = Integer.parseInt(dados[1]);
                int y = Integer.parseInt(dados[2]);

                Point2D position = new Point2D(x, y);

                if (nome.equals("baba")) {
                    Baba baba = new Baba(position);
                    r.addObject(baba);
                    r.player = baba;
                }

                //usamos o mesmo padrao para os restantes objetos
            }

        } catch (FileNotFoundException e) {
        System.err.println("Room file not found: " + f.getPath());
        }

        return r;
    }


    public boolean isWall(Point2D position) { //preferencialmente teriamos uma interface bloackable e classes abstratas
        for(ImageTile obj : objects){
            if (obj instanceof Wall && obj.getPosition().equals(position)) {
                return true;
            }
            
        }
        return false;

    }
    public Baba getPlayer() {
        return player;
    }
}