package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

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

    private static Map<String, Integer> initPositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();

        for(int i = 0; i < NUM_TILES; i++){
            positionToCoordinate.put(ALGEBRAIC_NOTATION[i], i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

    private static String[] initAlgebraicNotation() {
        return new String[] {
            "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
            "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
            "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
            "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
            "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
            "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
            "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
            "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"
        };
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

    public static String getPositionAtCoordinate(final int coordinate) {
        return ALGEBRAIC_NOTATION[coordinate];
    }
}
