package com.chess.gui;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.google.common.primitives.Ints;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.chess.gui.Table.*;

public class TakenPiecesPanel extends JPanel{
    
    private final JPanel topPanel;
    private final JPanel bottomPanel;

    private static final Color PANEL_COLOUR = Color.decode("#FFFFFF");
    private static final Dimension TAKEN_PIECES_SIZE = new Dimension(40, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel(){
        super(new BorderLayout());
        this.setBackground(PANEL_COLOUR);
        this.setBorder(PANEL_BORDER);
        this.topPanel = new JPanel(new GridLayout(8, 2));
        this.bottomPanel = new JPanel(new GridLayout(8, 2));
        this.topPanel.setBackground(PANEL_COLOUR);
        this.bottomPanel.setBackground(PANEL_COLOUR);
        this.add(this.topPanel, BorderLayout.NORTH);
        this.add(this.bottomPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_SIZE);
    }

    public void redo(final MoveLog moveLog){

        this.topPanel.removeAll();
        this.bottomPanel.removeAll();

        final List<Piece> takenWhitePieces = new ArrayList<>();
        final List<Piece> takenBlackPieces = new ArrayList<>();

        for(final Move move : moveLog.getMoves()){
            if(move.isAttack()){
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceColour().isWhite()){
                    takenWhitePieces.add(takenPiece);
                }
                else if(takenPiece.getPieceColour().isBlack()){
                    takenBlackPieces.add(takenPiece);
                }
                else{
                    throw new RuntimeException("Should reach here");
                }
            }
        }

        Collections.sort(takenWhitePieces, new Comparator<Piece>(){
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        Collections.sort(takenBlackPieces, new Comparator<Piece>(){
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        for(final Piece takenPiece : takenWhitePieces){
            try{
                final BufferedImage image = ImageIO.read(new File("art/" + 
                    takenPiece.getPieceColour().toString().substring(0, 1).toLowerCase() + takenPiece.toString()  + ".png"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.bottomPanel.add(imageLabel);
            }
            catch(final IOException e){
                e.printStackTrace();
            }
        }

        for(final Piece takenPiece : takenBlackPieces){
            try{
                final BufferedImage image = ImageIO.read(new File("art/" + 
                    takenPiece.getPieceColour().toString().substring(0, 1).toLowerCase() + takenPiece.toString()  + ".png"));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.bottomPanel.add(imageLabel);
            }
            catch(final IOException e){
                e.printStackTrace();
            }
        }
        validate();
    }
}
