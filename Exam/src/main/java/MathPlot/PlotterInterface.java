package MathPlot;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

// Interface for plotting shapes and curves on a canvas
// Coordinates are in mathematical space (NOT PIXEL SPACE!)
public interface PlotterInterface {
    // Draw a circle with center c and radius r
    void addCircle(Point c, double r, Color color, double lineWidth);

    // Draw a line from point 'from' to point 'to'
    void addLine(Point from, Point to, Color color, double lineWidth);

    // Draw a curve defined by a set of points accessible via Point.Iterator
    void addCurve(Point.Iterator ptIt, Color color, double lineWidth);

    // Return the JavaFX Canvas associated with this Plotter
    Canvas getCanvas();
}
