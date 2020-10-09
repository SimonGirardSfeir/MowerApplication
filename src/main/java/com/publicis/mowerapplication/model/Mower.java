package com.publicis.mowerapplication.model;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Mower {

    private int x;
    private int y;
    private Direction direction;

    public void move(char instruction, int xMax, int yMax) throws IncorrectContentException {
        if(instruction == 'G') {
            goLeft();
        } else if(instruction == 'D') {
            goRight();
        } else if(instruction == 'A') {
            goForward(xMax, yMax);
        } else {
            throw new IncorrectContentException("The data concerning the movement of the mower is incorrect.");
        }
    }

    private void goLeft() {
        if(direction.equals(Direction.WEST)) {
            direction = Direction.SOUTH;
        } else if(direction.equals(Direction.SOUTH)) {
            direction = Direction.EAST;
        } else if(direction.equals(Direction.EAST)) {
            direction = Direction.NORTH;
        } else if(direction.equals(Direction.NORTH)) {
            direction = Direction.WEST;
        }
    }

    private void goRight() {
        if(direction.equals(Direction.WEST)) {
            direction = Direction.NORTH;
        } else if(direction.equals(Direction.NORTH)) {
            direction = Direction.EAST;
        } else if(direction.equals(Direction.EAST)) {
            direction = Direction.SOUTH;
        } else if(direction.equals(Direction.SOUTH)) {
            direction = Direction.WEST;
        }
    }

    private void goForward(int xMax, int yMax) {
        if(direction.equals(Direction.WEST)) {
            if(x > 0) {
                x--;
            }
        } else if (direction.equals(Direction.NORTH)) {
            if(y < yMax) {
                y++;
            }
        } else if(direction.equals(Direction.EAST)) {
            if(x < xMax) {
                x++;
            }
        } else if(direction.equals(Direction.SOUTH)) {
            if(y > 0) {
                y--;
            }
        }
    }

    @Override
    public String toString() {
        return x + " " + y + " "+ direction.getDirectionCode();
    }
}
