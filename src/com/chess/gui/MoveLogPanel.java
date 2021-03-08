package com.chess.gui;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.gui.Table.MoveLog;

public class MoveLogPanel extends JPanel {

    private final DataModel model;
    private final JScrollPane scrollPane;
    private static final Dimension MOVE_LOG_SIZE = new Dimension(100, 400);

    MoveLogPanel() {
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        final JTable table = new JTable(model);
        table.setRowHeight(15);
        this.scrollPane = new JScrollPane(table);
        scrollPane.setColumnHeaderView(table.getTableHeader());
        scrollPane.setPreferredSize(MOVE_LOG_SIZE);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    void redo(final Board board, final MoveLog moveLog) {
        int currRow = 0;
        this.model.clear();
        for(final Move move : moveLog.getMoves()){
            final String moveText = move.toString();
            if(move.getMovedPiece().getPieceColour().isWhite()){
                this.model.setValueAt(moveText, currRow, 0);
            }
            else if(move.getMovedPiece().getPieceColour().isBlack()){
                this.model.setValueAt(moveText, currRow, 1);
                currRow++;
            }
        }
        if(moveLog.getMoves().size() > 0){
            final Move lastMove = moveLog.getMoves().get(moveLog.size() - 1);
            final String moveText = lastMove.toString();

            if(lastMove.getMovedPiece().getPieceColour().isWhite()){
                this.model.setValueAt(moveText + calculateCheckAndCheckMateHAsh(board), currRow - 1, 1);
            }
        }

        final JScrollBar vertical = scrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private String calculateCheckAndCheckMateHAsh(final Board board) {
        if(board.currentPlayer().isInCheckMate()){
            return "#";
        }
        else if(board.currentPlayer().isInCheck()){
            return "+";
        }
        return "";
    }

    private static class DataModel extends DefaultTableModel{

        private final List<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        DataModel(){
            this.values = new ArrayList<>();
        }

        public void clear() {
            this.values.clear();
            setRowCount(0);
        }

        @Override
        public int getRowCount() {
            if(this.values == null){
                return 0;
            }
            return this.values.size();
        }

        @Override
        public int getColumnCount() {
            return NAMES.length;
        }

        @Override
        public Object getValueAt(final int row, final int column) {
            final Row currRow = this.values.get(row);
            if(column == 0){
                return currRow.getWhiteMove();
            }
            else if(column == 1){
                return currRow.getBlackMove();
            }
            return null;
        }

        @Override
        public void setValueAt(final Object aValue, final int row, final int column){
            final Row currRow;
            if(this.values.size() <= row){
                currRow = new Row();
                this.values.add(currRow);
            }
            else{
                currRow = this.values.get(row);
            }
            if(column == 0){
                currRow.setWhiteMove((String)aValue);
            }
            else if(column == 1){
                currRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, column);
            }
        }

        @Override
        public Class<?> getColumnClass(final int column){
            return Move.class;
        }

        @Override
        public String getColumnName(final int column){
            return NAMES[column];
        }
    }

    private static class Row{

        private String whiteMove;
        private String blackMove;

        Row(){

        }

        public String getWhiteMove(){
            return this.whiteMove;
        }

        public String getBlackMove(){
            return this.blackMove;
        }

        public void setWhiteMove(final String move){
            this.whiteMove = move;
        }

        public void setBlackMove(final String move){
            this.blackMove = move;
        }
    }
}
