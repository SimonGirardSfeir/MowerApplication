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

    public Mower(Lawn lawn, Direction direction) {
        this.x = lawn.getXMower();
        this.y = lawn.getYMower();
        this.direction = direction;
    }

    public void move(char instruction, Lawn lawn) throws IncorrectContentException {
        if(instruction == 'G') {
            goLeft();
        } else if(instruction == 'D') {
            goRight();
        } else if(instruction == 'A') {
            goForward(lawn);
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

    private void goForward(Lawn lawn) {
        int [] array = direction.goForward(x, y, lawn);

        x = array[0];
        y = array[1];
    }

    @Override
    public String toString() {
        return x + " " + y + " "+ direction.getDirectionCode();
    }
}
