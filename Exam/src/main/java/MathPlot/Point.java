package MathPlot;

import java.util.NoSuchElementException;

public class Point {
    public interface Iterator {
        // Returns true if there is another point available
        boolean hasNext();

        // Returns true if there is a break in the points (e.g., discontinuity)
        boolean hasBreak();

        // Resets the iterator to the beginning
        void reset();

        // Returns the next point
        Point nextPoint() throws NoSuchElementException;
    }

    final private double x;
    final private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }
}
