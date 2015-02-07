package tk.ivybits.automata;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CellFrame extends JFrame {
    private JPanel scene;
    private AutomataGrid grid;

    public CellFrame() {
        initComponents();
        setLocationRelativeTo(null);
        ControlFrame controls = new ControlFrame(this, false, grid);
        controls.setLocationRelativeTo(this);
        controls.setVisible(true);
        controls.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        grid.setRunning(true);
    }

    private void initComponents() {
        scene = new JPanel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cellular Automata");
        setResizable(false);

        scene.setToolTipText("Cellular Automata");
        scene.setLayout(new BorderLayout());
        getContentPane().add(scene, BorderLayout.CENTER);
        grid = new AutomataGrid(90, 60);
        grid.setBackground(Color.WHITE);
        scene.add(BorderLayout.CENTER, grid);

        pack();
    }
}
