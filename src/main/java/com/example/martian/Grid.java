package com.example.martian;

import java.util.HashSet;
import java.util.Set;

public final class Grid {
    private final int maxX;
    private final int maxY;
    private final Set<Position> scents = new HashSet<>();

    public Grid(int maxX, int maxY) {
        if (maxX < 0 || maxY < 0) {
            throw new IllegalArgumentException(
                    "Grid upper-right coordinates must be non-negative");
        }

        if (maxX > 50 || maxY > 50) {
            throw new IllegalArgumentException(
                    "Grid coordinates must not exceed 50");
        }

        this.maxX = maxX;
        this.maxY = maxY;
    }

    public boolean contains(Position position) {
        return position.x() >= 0
                && position.x() <= maxX
                && position.y() >= 0
                && position.y() <= maxY;
    }

    public boolean hasScent(Position position) {
        return scents.contains(position);
    }

    public void addScent(Position position) {
        if (!contains(position)) {
            throw new IllegalArgumentException(
                    "A scent must be placed inside the grid: " + position);
        }

        scents.add(position);
    }
}