package com.publicis.mowerapplication.model;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MowerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void moveLeft() throws IncorrectContentException {

        Mower mower1 = new Mower(0, 0, Direction.SOUTH);
        mower1.move('G', 1, 1);

        assertEquals(Direction.EAST, mower1.getDirection());

        Mower mower2 = new Mower(0, 0, Direction.EAST);
        mower2.move('G', 1, 1);

        assertEquals(Direction.NORTH, mower2.getDirection());

        Mower mower3 = new Mower(0, 0, Direction.NORTH);
        mower3.move('G', 1, 1);

        assertEquals(Direction.WEST, mower3.getDirection());

        Mower mower4 = new Mower(0, 0, Direction.WEST);
        mower4.move('G', 1, 1);

        assertEquals(Direction.SOUTH, mower4.getDirection());
    }

    @Test
    void moveRight() throws IncorrectContentException {

        Mower mower1 = new Mower(0, 0, Direction.SOUTH);
        mower1.move('D', 1, 1);

        assertEquals(Direction.WEST, mower1.getDirection());

        Mower mower2 = new Mower(0, 0, Direction.WEST);
        mower2.move('D', 1, 1);

        assertEquals(Direction.NORTH, mower2.getDirection());

        Mower mower3 = new Mower(0, 0, Direction.NORTH);
        mower3.move('D', 1, 1);

        assertEquals(Direction.EAST, mower3.getDirection());

        Mower mower4 = new Mower(0, 0, Direction.EAST);
        mower4.move('D', 1, 1);

        assertEquals(Direction.SOUTH, mower4.getDirection());
    }

    @Test
    void moveForward() throws IncorrectContentException {
        //Standard case for direction South
        Mower mower1 = new Mower(1 , 1, Direction.SOUTH);
        mower1.move('A', 2, 2);

        assertEquals(1, mower1.getX());
        assertEquals(0, mower1.getY());
        assertEquals(Direction.SOUTH, mower1.getDirection());

        //Limit case for direction South
        Mower mower2 = new Mower(1 , 0, Direction.SOUTH);
        mower2.move('A', 2, 2);

        assertEquals(1, mower2.getX());
        assertEquals(0, mower2.getY());
        assertEquals(Direction.SOUTH, mower2.getDirection());

        //Standard case for direction East
        Mower mower3 = new Mower(1, 1, Direction.EAST);
        mower3.move('A', 2, 2);

        assertEquals(2, mower3.getX());
        assertEquals(1, mower3.getY());
        assertEquals(Direction.EAST, mower3.getDirection());

        //Limit case for direction East
        Mower mower4 = new Mower(2, 1, Direction.EAST);
        mower4.move('A', 2, 2);

        assertEquals(2, mower4.getX());
        assertEquals(1, mower4.getY());
        assertEquals(Direction.EAST, mower4.getDirection());

        //Standard case for direction North
        Mower mower5 = new Mower(1, 1, Direction.NORTH);
        mower5.move('A', 2, 2);

        assertEquals(1, mower5.getX());
        assertEquals(2, mower5.getY());
        assertEquals(Direction.NORTH, mower5.getDirection());

        //Limit case for direction North
        Mower mower6 = new Mower(1, 2, Direction.NORTH);
        mower6.move('A', 2, 2);

        assertEquals(1, mower6.getX());
        assertEquals(2, mower6.getY());
        assertEquals(Direction.NORTH, mower6.getDirection());

        //Standard case for direction West
        Mower mower7 = new Mower(1, 1, Direction.WEST);
        mower7.move('A', 2, 2);

        assertEquals(0, mower7.getX());
        assertEquals(1, mower7.getY());
        assertEquals(Direction.WEST, mower7.getDirection());

        //Limit case for direction West
        Mower mower8 = new Mower(0, 1, Direction.WEST);
        mower8.move('A', 2, 2);

        assertEquals(0, mower8.getX());
        assertEquals(1, mower8.getY());
        assertEquals(Direction.WEST, mower8.getDirection());
    }

    @Test
    void moveWrongDirection() {
        Mower mower = new Mower(1 , 1, Direction.SOUTH);
        assertThrows(IncorrectContentException.class, () -> mower.move('Z', 2, 2));
    }
}