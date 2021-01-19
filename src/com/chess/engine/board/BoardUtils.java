package com.chess.engine.board;

public class BoardUtils {
    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] SECOND_ROW = initRow(8);
    public static final boolean[] SEVENTH_ROW = initRow(48);

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_ROW = 8;

    // sets all squares in given column to true
    private static boolean[] initColumn(int colNum){
        final boolean[] col = new boolean[NUM_TILES];
        while (colNum < NUM_TILES){
            col[colNum] = true;
            colNum += NUM_TILES_PER_ROW;
        }
        return col;
    }

    // sets all squares in given row to true
    private static boolean[] initRow(int rowNum){
        final boolean[] row = new boolean[NUM_TILES];
        while (rowNum % NUM_TILES != 0){
            row[rowNum] = true;
            rowNum ++;
        }
        return row;
    }

    private BoardUtils(){
        throw new RuntimeException("This class cannot be instansiated");
    }

    public static boolean isValidTileCoordinate(final int coordinate) {
        return coordinate >= 0 && coordinate < NUM_TILES;
    }
}
