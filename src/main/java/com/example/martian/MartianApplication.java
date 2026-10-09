package com.example.martian;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MartianApplication {

    private MartianApplication() {
    }

    public static void main(String[] args) throws IOException {

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(System.in))) {

            String boundsLine = nextNonBlankLine(reader);

            if (boundsLine == null) {
                return;
            }

            Grid grid = parseGrid(boundsLine);
            RobotSimulator simulator = new RobotSimulator();

            while (true) {
                String positionLine =
                        nextNonBlankLine(reader);

                if (positionLine == null) {
                    break;
                }

                String instructionLine =
                        nextNonBlankLine(reader);

                if (instructionLine == null) {
                    throw new IllegalArgumentException(
                            "Missing instruction line for robot: "
                                    + positionLine);
                }

                RobotPosition position =
                        RobotPosition.parse(positionLine);

                Robot robot = position.toRobot();

                simulator.execute(
                        grid,
                        robot,
                        instructionLine.trim());

                System.out.println(robot);
            }

        } catch (IllegalArgumentException e) {
            System.err.println(
                    "Input error: " + e.getMessage());

            System.exit(1);
        }
    }

    private static Grid parseGrid(String line) {
        String[] parts =
                line.trim().split("\\s+");

        if (parts.length != 2) {
            throw new IllegalArgumentException(
                    "Grid bounds must contain max X and max Y");
        }

        try {
            return new Grid(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid grid bounds: " + line, e);
        }
    }

    private static String nextNonBlankLine(
            BufferedReader reader) throws IOException {

        String line;

        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                return line;
            }
        }

        return null;
    }
}
