package com.example.martian;

public record RobotPosition(
        int x,
        int y,
        Orientation orientation) {

    public static RobotPosition parse(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException(
                    "Robot position line cannot be blank");
        }

        String[] parts = line.trim().split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Robot position must contain x, y and orientation: "
                            + line);
        }

        try {
            return new RobotPosition(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Orientation.parse(parts[2])
            );
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "Invalid robot coordinates: " + line, e);
        }
    }

    public Robot toRobot() {
        return new Robot(x, y, orientation);
    }
}
