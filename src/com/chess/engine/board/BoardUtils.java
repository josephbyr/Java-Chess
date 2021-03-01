package com.chess.engine.board;

import java.util.Map;

public class BoardUtils {
    public static final boolean[] FIRST_FILE = initColumn(0);
    public static final boolean[] SECOND_FILE = initColumn(1);
    public static final boolean[] SEVENTH_FILE = initColumn(6);
    public static final boolean[] EIGHTH_FILE = initColumn(7);

    public static final boolean[] FIRST_RANK = initRow(0);
    public static final boolean[] SECOND_RANK = initRow(8);
    public static final boolean[] THIRD_RANK = initRow(16);
    public static final boolean[] FOURTH_RANK = initRow(24);
    public static final boolean[] FIFTH_RANK = initRow(32);
    public static final boolean[] SIXTH_RANK = initRow(48);
    public static final boolean[] SEVENTH_RANK = initRow(48);
    public static final boolean[] EIGHTH_RANK = initRow(56);

    public static final String[] ALGEBRAIC_NOTATION = initAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initPositionToCoordinateMap();

    public static final int NUM_TILES = 64;
    public static final int NUM_TILES_PER_RANK = 8;

    // sets all squares in given column to true
    private static boolean[] initColumn(int colNum){
        final boolean[] col = new boolean[NUM_TILES];
        while (colNum < NUM_TILES){
            col[colNum] = true;
            colNum += NUM_TILES_PER_RANK;
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

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public static int getPositionAtCoordinate(int coordinate) {
        return ALGEBRAIC_NOTATION[coordinate];
    }
}
