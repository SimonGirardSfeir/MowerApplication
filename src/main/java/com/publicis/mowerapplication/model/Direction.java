package com.publicis.mowerapplication.model;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;

public enum Direction {
    EAST("E") {
        @Override
        public Direction goLeft() {
            return Direction.NORTH;
        }

        @Override
        public Direction goRight() {
            return Direction.SOUTH;
        }

        @Override
        public int[] goForward(int x, int y, Lawn lawn) {
            int[] array = new int[2];

            array[0] = lawn.goEast(x);
            array[1] = y;

            return array;
        }
    }, WEST("W") {
        @Override
        public Direction goLeft() {
            return Direction.SOUTH;
        }

        @Override
        public Direction goRight() {
            return Direction.NORTH;
        }

        @Override
        public int[] goForward(int x, int y, Lawn lawn) {
            int[] array = new int[2];

            array[0] = lawn.goWest(x);
            array[1] = y;

            return array;
        }
    }, NORTH("N") {
        @Override
        public Direction goLeft() {
            return Direction.WEST;
        }

        @Override
        public Direction goRight() {
            return Direction.EAST;
        }

        @Override
        public int[] goForward(int x, int y, Lawn lawn) {
            int[] array = new int[2];

            array[0] = x;
            array[1] = lawn.goNorth(y);

            return array;
        }
    }, SOUTH("S") {
        @Override
        public Direction goLeft() {
            return Direction.EAST;
        }

        @Override
        public Direction goRight() {
            return Direction.WEST;
        }

        @Override
        public int[] goForward(int x, int y, Lawn lawn) {
            int[] array = new int[2];

            array[0] = x;
            array[1] = lawn.goSouth(y);

            return array;
        }
    };

    private final String directionCode;

    Direction(String directionCode) {
        this.directionCode = directionCode;
    }

    public String getDirectionCode(){
        return directionCode;
    }

    public abstract Direction goLeft();

    public abstract Direction goRight();

    public abstract int[] goForward(int x, int y, Lawn lawn);

    public static  Direction getDirectionFromString(String s) throws IncorrectContentException {
        if(s.charAt(0) == 'N') {
            return Direction.NORTH;
        } else if (s.charAt(0) == 'S') {
            return Direction.SOUTH;
        } else if (s.charAt(0) == 'E') {
            return Direction.EAST;
        } else if (s.charAt(0) == 'W') {
            return Direction.WEST;
        } else {
            throw new IncorrectContentException("The data concerning the cardinal point is incorrect.");
        }
    }
}
