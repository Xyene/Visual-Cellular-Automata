package tk.ivybits.automata;

import javax.swing.*;

public class Main {
    public static void main(String... argv) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
        }
        new CellFrame().setVisible(true);
    }
}
