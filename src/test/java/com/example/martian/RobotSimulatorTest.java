package com.example.martian;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RobotSimulatorTest {

    private final RobotSimulator simulator = new RobotSimulator();

    @Test
    void sampleCaseProducesExpectedResults() {
        Grid grid = new Grid(5, 3);

        Robot first = simulator.execute(
                grid,
                new Robot(1, 1, Orientation.E),
                "RFRFRFRF");

        Robot second = simulator.execute(
                grid,
                new Robot(3, 2, Orientation.N),
                "FRRFLLFFRRFLL");

        Robot third = simulator.execute(
                grid,
                new Robot(0, 3, Orientation.W),
                "LLFFFLFLFL");

        assertEquals("1 1 E", first.toString());
        assertEquals("3 3 N LOST", second.toString());
        assertEquals("2 3 S", third.toString());
    }

    @Test
    void robotIsLostWhenMovingOffNorthEdge() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 3, Orientation.N),
                "F");

        assertTrue(robot.isLost());
        assertEquals("2 3 N LOST", robot.toString());
    }

    @Test
    void robotIsLostWhenMovingOffSouthEdge() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 0, Orientation.S),
                "F");

        assertTrue(robot.isLost());
        assertEquals("2 0 S LOST", robot.toString());
    }

    @Test
    void robotIsLostWhenMovingOffEastEdge() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(5, 2, Orientation.E),
                "F");

        assertTrue(robot.isLost());
        assertEquals("5 2 E LOST", robot.toString());
    }

    @Test
    void robotIsLostWhenMovingOffWestEdge() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(0, 2, Orientation.W),
                "F");

        assertTrue(robot.isLost());
        assertEquals("0 2 W LOST", robot.toString());
    }

    @Test
    void scentPreventsLaterRobotFromBeingLostAtSamePoint() {
        Grid grid = new Grid(5, 3);

        // First robot falls from (5, 3), leaving a scent there.
        Robot first = simulator.execute(
                grid,
                new Robot(5, 3, Orientation.E),
                "F");

        // Second robot tries the same dangerous move.
        Robot second = simulator.execute(
                grid,
                new Robot(5, 3, Orientation.E),
                "F");

        assertTrue(first.isLost());
        assertFalse(second.isLost());

        // The second robot remains at the scented position.
        assertEquals("5 3 E", second.toString());
    }

    @Test
    void scentedDangerousMoveIsIgnoredAndSubsequentCommandsContinue() {
        Grid grid = new Grid(5, 3);

        // Create scent at (5, 3).
        simulator.execute(
                grid,
                new Robot(5, 3, Orientation.E),
                "F");

        // F is ignored because of the scent.
        // R changes E -> S.
        // F moves from (5,3) to (5,2).
        Robot robot = simulator.execute(
                grid,
                new Robot(5, 3, Orientation.E),
                "FRF");

        assertFalse(robot.isLost());
        assertEquals("5 2 S", robot.toString());
    }

    @Test
    void scentIsPlacedAtLastSafePosition() {
        Grid grid = new Grid(5, 3);

        // Robot moves from (4,0) to (5,0), then attempts
        // to move outside the grid from (5,0).
        simulator.execute(
                grid,
                new Robot(4, 0, Orientation.E),
                "FF");

        // A second robot at the scented position should
        // be protected from falling.
        Robot second = simulator.execute(
                grid,
                new Robot(5, 0, Orientation.E),
                "F");

        assertFalse(second.isLost());
        assertEquals("5 0 E", second.toString());
    }

    @Test
    void leftTurnChangesOrientationButNotPosition() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.N),
                "L");

        assertEquals(2, robot.x());
        assertEquals(1, robot.y());
        assertEquals(Orientation.W, robot.orientation());
    }

    @Test
    void rightTurnChangesOrientationButNotPosition() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.N),
                "R");

        assertEquals(2, robot.x());
        assertEquals(1, robot.y());
        assertEquals(Orientation.E, robot.orientation());
    }

    @Test
    void fourRightTurnsReturnToOriginalOrientation() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.N),
                "RRRR");

        assertEquals(2, robot.x());
        assertEquals(1, robot.y());
        assertEquals(Orientation.N, robot.orientation());
    }

    @Test
    void forwardMovesNorth() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.N),
                "F");

        assertEquals("2 2 N", robot.toString());
    }

    @Test
    void forwardMovesSouth() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.S),
                "F");

        assertEquals("2 0 S", robot.toString());
    }

    @Test
    void forwardMovesEast() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.E),
                "F");

        assertEquals("3 1 E", robot.toString());
    }

    @Test
    void forwardMovesWest() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.W),
                "F");

        assertEquals("1 1 W", robot.toString());
    }

    @Test
    void commandsAfterRobotIsLostAreNotExecuted() {
        Grid grid = new Grid(5, 3);

        // F causes the robot to fall.
        // The remaining R commands must not execute.
        Robot robot = simulator.execute(
                grid,
                new Robot(5, 3, Orientation.E),
                "FRRR");

        assertTrue(robot.isLost());
        assertEquals("5 3 E LOST", robot.toString());
    }

    @Test
    void unsupportedCommandFailsClearly() {
        Grid grid = new Grid(5, 3);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> simulator.execute(
                        grid,
                        new Robot(1, 1, Orientation.N),
                        "FX"));

        assertEquals(
                "Unsupported instruction: X",
                exception.getMessage());
    }

    @Test
    void invalidRobotPositionIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> RobotPosition.parse("1 N"));

        assertThrows(
                IllegalArgumentException.class,
                () -> RobotPosition.parse("1 2 X"));
    }

    @Test
    void invalidGridIsRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Grid(-1, 3));

        assertThrows(
                IllegalArgumentException.class,
                () -> new Grid(51, 3));
    }

    @Test
    void blankCommandStringLeavesRobotUnchanged() {
        Grid grid = new Grid(5, 3);

        Robot robot = simulator.execute(
                grid,
                new Robot(2, 1, Orientation.N),
                "");

        assertEquals("2 1 N", robot.toString());
    }
}
