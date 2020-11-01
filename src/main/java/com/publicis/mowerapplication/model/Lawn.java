package com.publicis.mowerapplication.model;

import com.publicis.mowerapplication.exceptions.IncorrectContentException;
import lombok.Getter;

@Getter
public abstract class Lawn {

    protected int[] dimensions;

    protected int xMower;

    protected int yMower;

    public abstract void setInitialPosition(String instruction) throws IncorrectContentException;

    public abstract int goEast(int z);
    public abstract int goWest(int z);
    public abstract int goNorth(int z);
    public abstract int goSouth(int z);

}
