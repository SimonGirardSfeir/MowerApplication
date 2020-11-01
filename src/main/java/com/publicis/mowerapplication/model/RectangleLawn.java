package com.publicis.mowerapplication.model;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;

public class RectangleLawn extends Lawn {

    public RectangleLawn(String[] dimensions) {
        int xMax = Integer.parseInt(dimensions[0]);
        int yMax = Integer.parseInt(dimensions[1]);

        this.dimensions = new int[] {xMax, yMax};
    }

    @Override
    public void setInitialPosition(String instruction) throws IncorrectContentException {
        String[] arrayStartingPosition = instruction.split(" ");

        if(arrayStartingPosition.length != 3)   {
            throw new IncorrectContentException("The line concerning the coordinates of one of the starting points is incorrect.");
        }

        xMower = Integer.parseInt(arrayStartingPosition[0]);
        yMower = Integer.parseInt(arrayStartingPosition[1]);

        if(xMower > dimensions[0] || yMower > dimensions[1]) {
            throw new IncorrectContentException("The position of the starting point is not on the lawn.");
        }
    }

    @Override
    public int goEast(int z) {
        if(z < dimensions[0]) {
            return ++z;
        }
        return z;
    }

    @Override
    public int goWest(int z) {
        if(z > 0) {
            return --z;
        }
        return z;
    }

    @Override
    public int goNorth(int z) {
        if(z < dimensions[1]) {
            return ++z;
        }
        return z;
    }

    @Override
    public int goSouth(int z) {
        if(z > 0) {
            return --z;
        }
        return z;
    }
}
