package MathPlot;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class MathPlot {
    private class Plotter implements PlotterInterface {
        private interface PlotterItem {
            void plot();
        }

        private abstract class PlotterBase implements PlotterItem {
            final protected GraphicsContext gc;
            final protected Color color;
            final protected double lineWidth;

            public PlotterBase(Color color, double lineWidth) {
                this.gc = Plotter.this.canvas.getGraphicsContext2D();
                this.color = color;
                this.lineWidth = lineWidth;
            }
        }

        final List<PlotterItem> items;
        final private Canvas canvas;
        private Point min, max;
        private Point lastMouse;

        private class Circle extends PlotterBase {
            final private Point c;
            final private double r;

            public Circle(Point c, double r, Color color, double lineWidth) {
                super(color, lineWidth);

                this.c = c;
                this.r = r;
            }

            @Override
            public void plot() {
                this.gc.setStroke(this.color);
                this.gc.setLineWidth(this.lineWidth);
                this.gc.strokeOval(-this.r + this.c.x(), -this.r + this.c.y(), 2 * this.r + this.c.x(),
                        2 * this.r + this.c.y());
            }
        }

        private class Curve extends PlotterBase {
            final private Point.Iterator ptIt;

            public Curve(Point.Iterator ptIt, Color color, double lineWidth) {
                super(color, lineWidth);

                this.ptIt = ptIt;
            }

            @Override
            public void plot() {
                this.ptIt.reset();

                if (!this.ptIt.hasNext()) {
                        return;
                }

                this.gc.setLineWidth(this.lineWidth);
                this.gc.setStroke(this.color);

                this.gc.beginPath();

                Point origin = this.ptIt.nextPoint();
                this.gc.moveTo(origin.x(), origin.y());

                while (this.ptIt.hasNext()) {
                    final Point np = this.ptIt.nextPoint();

                    if (!this.ptIt.hasBreak()) {
                        this.gc.lineTo(np.x(), np.y());
                    }

                    this.gc.moveTo(np.x(), np.y());
                }

                this.gc.stroke();
            }
        }

        private class Line extends PlotterBase {
            final private Point from;
            final private Point to;

            public Line(Point from, Point to, Color color, double lineWidth) {
                super(color, lineWidth);

                this.from = from;
                this.to = to;
            }

            @Override
            public void plot() {
                this.gc.setStroke(this.color);
                this.gc.setLineWidth(this.lineWidth);
                this.gc.strokeLine(this.from.x(), this.from.y(), this.to.x(), this.to.y());
            }
        }

        public Plotter(Canvas canvas, Point min, Point max) {
            this.min = min;
            this.max = max;
            this.items = new ArrayList<>();
            this.canvas = canvas;

            this.canvas.widthProperty().addListener(_ -> render());
            this.canvas.heightProperty().addListener(_ -> render());

            this.canvas.setOnMousePressed(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    this.lastMouse = new Point(e.getX(), e.getY());
                }
            });

            this.canvas.setOnMouseDragged(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    double dx = e.getX() - this.lastMouse.x();
                    double dy = e.getY() - this.lastMouse.y();

                    double width = this.canvas.getWidth();
                    double height = this.canvas.getHeight();

                    double dxUnits = (dx / width) * (this.max.x() - this.min.x());
                    double dyUnits = (dy / height) * (this.max.y() - this.min.y());

                    this.min = new Point(this.min.x() - dxUnits, this.min.y() + dyUnits);
                    this.max = new Point(this.max.x() - dxUnits, this.max.y() + dyUnits);
                    this.lastMouse = new Point(e.getX(), e.getY());

                    render();
                }
            });

            this.canvas.addEventHandler(ScrollEvent.SCROLL, e -> {
                double zoomFactor = (e.getDeltaY() > 0) ? 0.9 : 1.1;

                double mouseX = e.getX();
                double mouseY = e.getY();

                double width = this.canvas.getWidth();
                double height = this.canvas.getHeight();

                double mouseXUnit = this.min.x() + (mouseX / width) * (this.max.x() - this.min.x());
                double mouseYUnit = this.max.y() - (mouseY / height) * (this.max.y() - this.min.y());

                double newWidth = (this.max.x() - this.min.x()) * zoomFactor;
                double newHeight = (this.max.y() - this.min.y()) * zoomFactor;

                double xMin = mouseXUnit - (mouseX - 0) / width * newWidth;
                double xMax = this.min.x() + newWidth;

                double yMax = mouseYUnit + (mouseY - 0) / height * newHeight;
                double yMin = this.max.y() - newHeight;

                this.min = new Point(xMin, yMin);
                this.max = new Point(xMax, yMax);

                render();
            });
        }

        @Override
        public void addCircle(Point c, double r, Color color, double lineWidth) {
            this.items.add(new Circle(c, r, color, lineWidth));
        }

        @Override
        public void addLine(Point from, Point to, Color color, double lineWidth) {
            this.items.add(new Line(from, to, color, lineWidth));
        }

        @Override
        public void addCurve(Point.Iterator ptIt, Color color, double lineWidth) {
            this.items.add(new Curve(ptIt, color, lineWidth));
        }

        @Override
        public Canvas getCanvas() {
            return this.canvas;
        }

        public void render() {
            double width = this.canvas.getWidth();
            double height = this.canvas.getHeight();

            final GraphicsContext gc = this.canvas.getGraphicsContext2D();

            gc.setTransform(new Affine());
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, width, height);

            Affine transform = new Affine();
            transform.appendTranslation(0, height);
            transform.appendScale(1, -1);
            transform.appendScale(width / (this.max.x() - this.min.x()),
                    height / (this.max.y() - this.min.y()));
            transform.appendTranslation(-this.min.x(), -this.min.y());

            gc.setTransform(transform);

            for (final PlotterItem item : this.items) {
                item.plot();
            }
        }
    }

    public enum PlotType {
        Cartesian, Polar
    }

    public enum ExpressionFormat {
        AOS, RPN
    }

    public MathPlot() {
        // YOU CAN CHANGE HERE
    }

    public void setExpression(String expr, ExpressionFormat format) {
        // YOU CAN CHANGE HERE
    }

    public void plot(Canvas canvas, PlotType type) {
        final Plotter pf = new Plotter(canvas, new Point(-10, -10), new Point(10, 10));

        // YOU CAN CHANGE HERE

        pf.render();
    }

    public double area() {
        // YOU CAN CHANGE HERE

        return 0.0;
    }

    public List<String> print(ExpressionFormat format) {
        final List<String> res = new ArrayList<>();

        // YOU CAN CHANGE HERE

        return res;
    }
}
