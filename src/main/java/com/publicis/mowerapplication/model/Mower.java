package com.publicis.mowerapplication.model;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
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
        direction = direction.goLeft();
    }

    private void goRight() {
        direction = direction.goRight();
    }

    private void goForward(int xMax, int yMax) {
        int [] array = direction.goForward(x, xMax, y, yMax);

        x = array[0];
        y = array[1];
    }

    @Override
    public String toString() {
        return x + " " + y + " "+ direction.getDirectionCode();
    }
}
