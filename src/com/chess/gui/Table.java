package com.chess.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Table {
    
    private final JFrame gameFrame;
    private static Dimension OUTER_FRAME_SIZE = new Dimension(600, 600);

    public Table(){
        this.gameFrame = new JFrame("Chess");
        final JMenuBar menuBar = new JMenuBar();
        populateMenuBar(menuBar);
        this.gameFrame.setJMenuBar(menuBar);
        this.gameFrame.setSize(OUTER_FRAME_SIZE);
        this.gameFrame.setVisible(true);
    }

    private void populateMenuBar(final JMenuBar menuBar){
        menuBar.add(createFileMenu());
    }

    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Open PGN file");
            }
        });
        fileMenu.add(openPGN);
        return fileMenu;
    }
}
