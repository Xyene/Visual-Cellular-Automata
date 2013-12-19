package tk.ivybits.automata;

import java.awt.*;

import static tk.ivybits.automata.AutomataFactory.create;

/**
 * @version 1.0
 * @since 1.0
 */
public enum Automata implements IAutomata {
    GAME_OF_LIFE("Game Of Life", "B3/S23", 0x0CAC00),
    HIGH_LIFE("High Life", "B36/S23", 0xFF0000),
    ASSIMILATION("Assimilation", "B345/S4567", 0x0000FF),
    TWO_BY_TWO("2x2", "B36/S125", 0xFFFF00),
    DAY_AND_NIGHT("Day and Night", "B3678/S34678", 0x00FFFF),
    
    AMOEBA("Amoeba", "B357/S1358", 0xFF00FF),
    PSEUDOLIFE("Pseudo Life", "B357/S238", 0xFFFFFF),
    DIAMOEBA("Diamoeba", "B35678/S5678", 0xE05010),

    LONGLIFE("Long Life", "B345/S5", 0x505050),
    STAINS("Stains", "B3678/S235678", 0x5000FF),
    SEEDS("Seeds", "B2/S", 0xFBEC7D),
    MAZE("Maze", "B3/S12345", 0xA8E4A0),
    COAGULATIONS("Coagulations", "B378/S235678", 0x9ACD32),
    WALLED_CITIES("Walled cities", "B45678/S2345", 0x0047AB),
    GNARL("Gnarl", "B1/S1", 0xE5B73B),
    REPLICATOR("Replicator", "B1357/S1357", 0x259588),
    MYSTERY("Mystery", "B3458/S05678", 0x0C3C00),
    LIVING_ON_EDGE("Living on the Edge", "B37/S3458/4", 0xFF0000),
    LIKE_FROGS("Like Frogs", "B3/S124/3", 0x00FF00),
    LIKE_STAR_WARS("Like Star Wars", "B278/S3456/6", 0x0000FF),
    FROGS("Frogs", "B34/S12/3", 0x00AA00),
    BRIAN_6("Brian 6", "B246/S6/3", 0xCCCC00);

    private final String id;
    private final String algorithm;
    private final IAutomata automata;
    private final Color colour;

    Automata(String id, String algorithm, int colour) {
        this.id = id;
        this.algorithm = algorithm;
        this.automata = create(algorithm);
        this.colour = new Color(colour);
    }

    @Override
    public boolean willLive(boolean alive, int neighbours, int tick) {
        return automata.willLive(alive, neighbours, tick);
    }

    public String display() {
        return id;
    }

    public String algorithm() {
        return algorithm;
    }

    public Color colour() {
        return colour;
    }
}
