package tk.ivybits.automata;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import static javax.swing.GroupLayout.*;

public class ControlFrame extends javax.swing.JDialog {
    private AutomataGrid grid;
    private JPanel automataList;
    private JScrollPane automataScroller;
    private JButton clearButton;
    private JLabel delayLabel;
    private JSpinner delaySpinner;
    private JButton pauseButton;
    private JSeparator separator;
    private JButton stepButton;

    public ControlFrame(java.awt.Frame parent, boolean modal, final AutomataGrid grid) {
        super(parent, modal);
        this.grid = grid;
        initComponents();
    }

    private void initComponents() {
        automataScroller = new JScrollPane();
        automataList = new JPanel();
        delayLabel = new JLabel();
        delaySpinner = new JSpinner();
        stepButton = new JButton();
        clearButton = new JButton();
        pauseButton = new JButton();
        separator = new JSeparator();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Controls");
        setMaximumSize(new Dimension(155, Integer.MAX_VALUE));
        setResizable(false);

        automataList.setLayout(new GridLayout(0, 1));
        automataScroller.setViewportView(automataList);
        ButtonGroup exclusive = new ButtonGroup();
        for (final Automata a : Automata.values()) {
            JRadioButton select = new JRadioButton(a.display());
            select.setToolTipText(a.algorithm());
            select.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    grid.setAutomata(a);
                }
            });
            exclusive.add(select);
            select.setOpaque(true);
            select.setFocusPainted(false);
            select.setBackground(a.colour());
            select.setMargin(new Insets(0, 0, 0, 0));
            automataList.add(select);
        }
        exclusive.getElements().nextElement().setSelected(true);
        automataScroller.getVerticalScrollBar().setUnitIncrement(16);

        delayLabel.setText("generations/s");

        delaySpinner.setValue(4);

        stepButton.setText("Step");
        stepButton.setEnabled(false);
        stepButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                grid.tick();
            }
        });

        clearButton.setText("Clear");
        clearButton.setMaximumSize(new Dimension(61, 23));
        clearButton.setMinimumSize(new Dimension(61, 23));
        clearButton.setPreferredSize(new Dimension(61, 23));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                grid.reset();
            }
        });

        pauseButton.setText("Pause");
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (grid.isRunning()) {
                    pauseButton.setText("Play");
                } else {
                    pauseButton.setText("Pause");
                }
                grid.setRunning(!grid.isRunning());
                stepButton.setEnabled(!stepButton.isEnabled());
                delaySpinner.setEnabled(!delaySpinner.isEnabled());
                delayLabel.setEnabled(!delayLabel.isEnabled());
            }
        });

        javax.swing.GroupLayout layout;
        layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
                                        .addComponent(separator)
                                        .addGroup(Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(clearButton, PREFERRED_SIZE, 65, PREFERRED_SIZE)
                                                .addGap(6, 6, 6)
                                                .addComponent(pauseButton))
                                        .addComponent(stepButton, Alignment.LEADING, PREFERRED_SIZE, 61, PREFERRED_SIZE)
                                        .addGroup(Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(delaySpinner, PREFERRED_SIZE, 61, PREFERRED_SIZE)
                                                .addGap(10, 10, 10)
                                                .addComponent(delayLabel))
                                        .addComponent(automataScroller))
                                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(clearButton, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addComponent(pauseButton))
                                .addGap(6, 6, 6)
                                .addComponent(stepButton)
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(delaySpinner, PREFERRED_SIZE, DEFAULT_SIZE, PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addComponent(delayLabel)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(separator, PREFERRED_SIZE, 3, PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(automataScroller, PREFERRED_SIZE, 244, PREFERRED_SIZE))
        );

        delaySpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                grid.setGenerationsPerSecond((int) delaySpinner.getValue());
            }
        });

        pack();
    }
}
