package com.example.martian;

public final class Robot {
    private int x;
    private int y;
    private Orientation orientation;
    private boolean lost;

    public Robot(int x, int y, Orientation orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Position position() {
        return new Position(x, y);
    }

    public Orientation orientation() {
        return orientation;
    }

    public boolean isLost() {
        return lost;
    }

    public void turnLeft() {
        ensureActive();
        orientation = orientation.turnLeft();
    }

    public void turnRight() {
        ensureActive();
        orientation = orientation.turnRight();
    }

    public Position forwardPosition() {
        ensureActive();

        return new Position(
                x + orientation.deltaX(),
                y + orientation.deltaY()
        );
    }

    public void moveTo(Position position) {
        ensureActive();

        x = position.x();
        y = position.y();
    }

    public void markLost() {
        ensureActive();
        lost = true;
    }

    private void ensureActive() {
        if (lost) {
            throw new IllegalStateException(
                    "A lost robot cannot execute further commands");
        }
    }

    @Override
    public String toString() {
        return x + " " + y + " " + orientation
                + (lost ? " LOST" : "");
    }
}