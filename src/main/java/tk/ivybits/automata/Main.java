package tk.ivybits.automata;

import javax.swing.*;

public class Main {
    public static void main(String... argv) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CellFrame().setVisible(true);
            }
        });
    }
}
