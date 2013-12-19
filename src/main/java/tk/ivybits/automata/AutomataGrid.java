package tk.ivybits.automata;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AutomataGrid extends JPanel {
    private static final int CELL_SIZE = 10;
    private Cell[][] cells;
    private final int width, height;
    private Automata automata = Automata.values()[0];
    private Timer ticker;

    static class Cell {
        protected Automata automata;
        protected int generation;

        public Cell(Automata automata) {
            this.automata = automata;
        }
    }

    public AutomataGrid(int w, int h) {
        width = w;
        height = h;
        reset();
        Dimension size = new Dimension(w * CELL_SIZE, h * CELL_SIZE);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                doCell(e);
            }

            private void doCell(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                if (inBounds(x, y)) {
                    cells[x][y] = SwingUtilities.isLeftMouseButton(e) ? new Cell(automata) : null;
                    repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                doCell(e);
            }
        };
        addMouseListener(m);
        addMouseMotionListener(m);
        ticker = new Timer(1000 / 4, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
    }

    public void setAutomata(Automata automata) {
        this.automata = automata;
    }

    public Automata getAutomata() {
        return automata;
    }

    public void reset() {
        cells = new Cell[width][height];
        repaint();
    }

    public void setGenerationsPerSecond(int ticks) {
        ticker.setDelay(1000 / ticks);
    }

    public int getGenerationsPerSecond() {
        return 1000 / ticker.getDelay();
    }

    public boolean isRunning() {
        return ticker.isRunning();
    }

    public void setRunning(boolean flag) {
        if (flag) {
            if (!ticker.isRunning()) {
                ticker.restart();
            }
        } else {
            ticker.stop();
        }
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private int alive(int x, int y) {
        return cellAt(x, y) != null ? 1 : 0;
    }

    private Cell cellAt(int x, int y) {
        return inBounds(x, y) ? cells[x][y] : null;
    }

    private Automata leadingSpecies(int x, int y) {
        byte[] leading = new byte[Automata.values().length];
        if (alive(x + 1, y) == 1) {
            leading[cellAt(x + 1, y).automata.ordinal()]++;
        }
        if (alive(x - 1, y) == 1) {
            leading[cellAt(x - 1, y).automata.ordinal()]++;
        }
        if (alive(x, y + 1) == 1) {
            leading[cellAt(x, y + 1).automata.ordinal()]++;
        }
        if (alive(x, y - 1) == 1) {
            leading[cellAt(x, y - 1).automata.ordinal()]++;
        }
        if (alive(x + 1, y + 1) == 1) {
            leading[cellAt(x + 1, y + 1).automata.ordinal()]++;
        }
        if (alive(x + 1, y - 1) == 1) {
            leading[cellAt(x + 1, y - 1).automata.ordinal()]++;
        }
        if (alive(x - 1, y + 1) == 1) {
            leading[cellAt(x - 1, y + 1).automata.ordinal()]++;
        }
        if (alive(x - 1, y - 1) == 1) {
            leading[cellAt(x - 1, y - 1).automata.ordinal()]++;
        }
        int max = -1;
        for (int idx = 0; idx != leading.length; idx++) {
            if (leading[idx] > max) {
                max = idx;
            }
        }
        return Automata.values()[max];
    }

    public void tick() {
        Cell[][] cells = dup(this.cells);
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                Cell cell = this.cells[x][y];
                Automata leading = leadingSpecies(x, y);
                boolean alive = leading.willLive(cell != null, alive(x + 1, y)
                        + alive(x - 1, y)
                        + alive(x, y + 1)
                        + alive(x, y - 1)
                        + alive(x + 1, y + 1)
                        + alive(x + 1, y - 1)
                        + alive(x - 1, y + 1)
                        + alive(x - 1, y - 1));
                if (alive) {
                    if (cell == null) {
                        cells[x][y] = new Cell(leading);
                    } else {
                        cell.generation++;
                    }
                } else {
                    cells[x][y] = null;
                }
            }
        }
        this.cells = cells;
        paintImmediately(getBounds());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                Cell cell = cells[x][y];
                if (cell != null) {
                    g.setColor(cell.automata.colour());
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        g.setColor(Color.LIGHT_GRAY);
        for (int y = 0; y != height; y++) {
            g.drawLine(0, y * CELL_SIZE, width * CELL_SIZE, y * CELL_SIZE);
        }
        for (int x = 0; x != width; x++) {
            g.drawLine(x * CELL_SIZE, 0, x * CELL_SIZE, height * CELL_SIZE);
        }
    }

    private static Cell[][] dup(Cell[][] from) {
        Cell[][] cells = new Cell[from.length][];
        for (int i = 0; i < from.length; i++) {
            Cell[] matrix = from[i];
            int len = matrix.length;
            cells[i] = new Cell[len];
            System.arraycopy(matrix, 0, cells[i], 0, len);
        }
        return cells;
    }
}
