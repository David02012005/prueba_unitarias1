package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class LocationSpec {

    private final int x = 12;
    private final int y = 32;
    private final Direction direction = Direction.NORTH;
    private Point max;
    private Location location;
    private List<Point> obstacles;

    @BeforeMethod
    public void beforeTest() {
        max = new Point(50, 50);
        location = new Location(new Point(x, y), direction);
        obstacles = new ArrayList<Point>();
    }

    public void whenInstantiatedThenXIsStored() {
        assertEquals(location.getPoint().getX(), x);
    }

    public void whenInstantiatedThenYIsStored() {
        assertEquals(location.getPoint().getY(), y);
    }

    public void whenInstantiatedThenDirectionIsStored() {
        assertEquals(location.getDirection(), direction);
    }

    public void givenDirectionNWhenForwardThenYDecreases() {
        location.setDirection(Direction.NORTH);
        location.forward();
        assertEquals(location.getPoint().getY(), y - 1);
    }

    public void givenDirectionSWhenForwardThenYIncreases() {
        location.setDirection(Direction.SOUTH);
        location.forward();
        assertEquals(location.getPoint().getY(), y + 1);
    }

    public void givenDirectionEWhenForwardThenXIncreases() {
        location.setDirection(Direction.EAST);
        location.forward();
        assertEquals(location.getPoint().getX(), x + 1);
    }

    public void givenDirectionWWhenForwardThenXDecreases() {
        location.setDirection(Direction.WEST);
        location.forward();
        assertEquals(location.getPoint().getX(), x - 1);
    }

    public void givenDirectionNWhenBackwardThenYIncreases() {
        location.setDirection(Direction.NORTH);
        location.backward();
        assertEquals(location.getPoint().getY(), y + 1);
    }

    public void givenDirectionSWhenBackwardThenYDecreases() {
        location.setDirection(Direction.SOUTH);
        location.backward();
        assertEquals(location.getPoint().getY(), y - 1);
    }

    public void givenDirectionEWhenBackwardThenXDecreases() {
        location.setDirection(Direction.EAST);
        location.backward();
        assertEquals(location.getPoint().getX(), x - 1);
    }

    public void givenDirectionWWhenBackwardThenXIncreases() {
        location.setDirection(Direction.WEST);
        location.backward();
        assertEquals(location.getPoint().getX(), x + 1);
    }

    public void whenTurnLeftThenDirectionIsSet() {
        location.turnLeft();
        assertEquals(location.getDirection(), Direction.WEST);
    }

    public void whenTurnRightThenDirectionIsSet() {
        location.turnRight();
        assertEquals(location.getDirection(), Direction.EAST);
    }

    public void givenSameObjectsWhenEqualsThenTrue() {
        assertTrue(location.equals(location));
    }

    public void givenDifferentObjectWhenEqualsThenFalse() {
        assertFalse(location.equals(new Object()));
    }

    public void givenDifferentXWhenEqualsThenFalse() {
        Location newLocation = new Location(new Point(x + 1, y), direction);
        assertFalse(location.equals(newLocation));
    }

    public void givenDifferentYWhenEqualsThenFalse() {
        Location newLocation = new Location(new Point(x, y + 1), direction);
        assertFalse(location.equals(newLocation));
    }

    public void givenDifferentDirectionWhenEqualsThenFalse() {
        Location newLocation = new Location(new Point(x, y), Direction.SOUTH);
        assertFalse(location.equals(newLocation));
    }

    public void givenSameXYDirectionWhenEqualsThenTrue() {
        Location newLocation = new Location(new Point(x, y), direction);
        assertTrue(location.equals(newLocation));
    }

    public void whenCopyThenDifferentObject() {
        Location newLocation = location.copy();
        assertNotSame(location, newLocation);
    }

    public void whenCopyThenEquals() {
        Location newLocation = location.copy();
        assertEquals(location, newLocation);
    }

    public void givenDirectionEAndXEqualsMaxXWhenForwardThen1() {
        location.setDirection(Direction.EAST);
        location.setPoint(new Point(max.getX(), y));
        location.forward();
        assertEquals(location.getPoint().getX(), 1);
    }

    public void givenDirectionWAndXEquals1WhenForwardThenMaxX() {
        location.setDirection(Direction.WEST);
        location.setPoint(new Point(1, y));
        location.forward();
        assertEquals(location.getPoint().getX(), max.getX());
    }

    public void givenDirectionNAndYEquals1WhenForwardThenMaxY() {
        location.setDirection(Direction.NORTH);
        location.setPoint(new Point(x, 1));
        location.forward();
        assertEquals(location.getPoint().getY(), max.getY());
    }

    public void givenDirectionSAndYEqualsMaxYWhenForwardThen1() {
        location.setDirection(Direction.SOUTH);
        location.setPoint(new Point(x, max.getY()));
        location.forward();
        assertEquals(location.getPoint().getY(), 1);
    }

    public void givenObstacleWhenForwardThenReturnFalse() {
        obstacles.add(new Point(x, y - 1));
        location.setPoint(new Point(x, y));
        assertFalse(location.forward(max, obstacles));
    }

    public void givenObstacleWhenBackwardThenReturnFalse() {
        obstacles.add(new Point(x, y + 1));
        location.setPoint(new Point(x, y));
        assertFalse(location.backward(max, obstacles));
    }

}
