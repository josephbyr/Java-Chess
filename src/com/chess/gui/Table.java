package com.chess.gui;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Table {
    
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private final static Dimension OUTER_FRAME_SIZE = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_SIZE = new Dimension(400, 350);
    private final static Dimension TILE_PANEL_SIZE = new Dimension(10, 10);

    private final Color lightTileColour = Color.decode("#F0D9B5");
    private final Color darkTileColour = Color.decode("#946f51");

    public Table(){
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar menuBar = new JMenuBar();
        createMenuBar(menuBar);
        this.gameFrame.setJMenuBar(menuBar);
        this.gameFrame.setSize(OUTER_FRAME_SIZE);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);
    }

    private void createMenuBar(final JMenuBar menuBar){
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

        final JMenuItem exitMenuitem = new JMenuItem("Exit");
        exitMenuitem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuitem);

        return fileMenu;
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtils.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_SIZE);
            validate();
        }
    }

    private class TilePanel extends JPanel{

        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_SIZE);
            setTileColour();
            validate();
        }

        private void setPieceIcon(final Board board) throws IOException{
            this.removeAll();
            if(board.getTile(this.tileId).isTileOccupied()){
                String pieceIconPath = "";
                try{
                final BufferedImage image = 
                    ImageIO.read(new File(pieceIconPath + board.getTile(this.tileId).getPiece().getPieceColour().toString().substring(0, 1) + 
                    board.getTile(this.tileId).getPiece().toString() + ".svg"));
                    add(new JLabel(new ImageIcon(image)));
                }
                catch(IOException e){
                    e.printStackTrace();
                }
            }
        }

        private void setTileColour() {
            boolean isLight = ((tileId + tileId / 8) % 2 == 0);
            setBackground(isLight ? lightTileColour : darkTileColour);
        }
    }
}
