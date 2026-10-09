package com.example.martian;

public final class RobotSimulator {

    public Robot execute(Grid grid, Robot robot, String commands) {
        for (char command : commands.toCharArray()) {

            if (robot.isLost()) {
                break;
            }

            switch (command) {
                case 'L':
                    robot.turnLeft();
                    break;

                case 'R':
                    robot.turnRight();
                    break;

                case 'F':
                    moveForward(grid, robot);
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Unsupported instruction: " + command);
            }
        }

        return robot;
    }

    private void moveForward(Grid grid, Robot robot) {
        Position current = robot.position();
        Position next = robot.forwardPosition();

        if (grid.contains(next)) {
            robot.moveTo(next);
            return;
        }

        if (grid.hasScent(current)) {
            // Ignore this F command.
            return;
        }

        // Robot falls off the world.
        grid.addScent(current);
        robot.markLost();
    }
}
