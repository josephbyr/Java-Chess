package com.chess.engine;

public enum Colour {
    WHITE {
        @Override
        public int getDirection(){
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }
    },
    BLACK {
        @Override
        public int getDirection(){
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }
    };

    public abstract int getDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
}
