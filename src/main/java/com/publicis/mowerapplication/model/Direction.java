package com.publicis.mowerapplication.model;

public enum Direction {
    EAST("E"), WEST("W"), NORTH("N"), SOUTH("S");

    private final String directionCode;

    Direction(String directionCode) {
        this.directionCode = directionCode;
    }

    public String getDirectionCode(){
        return directionCode;
    }


}
