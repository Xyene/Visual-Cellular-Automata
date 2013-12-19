package tk.ivybits.automata;

public interface IAutomata {
    boolean willLive(boolean alive, int neighbours, int tick);
}
