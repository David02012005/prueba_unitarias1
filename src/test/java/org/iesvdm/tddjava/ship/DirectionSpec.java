package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class DirectionSpec {

    public void whenGetFromShortNameNThenReturnDirectionN() {
        Direction direction = Direction.getFromShortName('N');
        assertEquals(Direction.NORTH, direction, "When 'N' is passed, the direction should be NORTH");
    }

    public void whenGetFromShortNameWThenReturnDirectionW() {
        Direction direction = Direction.getFromShortName('W');
        assertEquals(Direction.WEST, direction, "When 'W' is passed, the direction should be WEST");
    }

    public void whenGetFromShortNameBThenReturnNone() {
        Direction direction = Direction.getFromShortName('B');
        assertEquals(Direction.NONE, direction, "When 'B' is passed, the direction should be NONE");
    }

    public void givenSWhenLeftThenE() {
        Direction direction = Direction.SOUTH;
        Direction newDirection = direction.turnLeft();
        assertEquals(Direction.EAST, newDirection, "When SOUTH is passed, the new direction should be EAST");
    }

    public void givenNWhenLeftThenW() {
        Direction direction = Direction.NORTH;
        Direction newDirection = direction.turnLeft();
        assertEquals(Direction.WEST, newDirection, "When NORTH is passed, the new direction should be WEST");
    }

    public void givenSWhenRightThenW() {
        Direction direction = Direction.SOUTH;
        Direction newDirection = direction.turnRight();
        assertEquals(Direction.WEST, newDirection, "When SOUTH is passed, the new direction should be WEST");
    }

    public void givenWWhenRightThenN() {
        Direction direction = Direction.WEST;
        Direction newDirection = direction.turnRight();
        assertEquals(Direction.NORTH, newDirection, "When WEST is passed, the new direction should be NORTH");
    }

}
