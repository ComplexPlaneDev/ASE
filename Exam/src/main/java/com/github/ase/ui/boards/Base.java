package com.github.ase.ui.boards;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public abstract class Base extends Region {
    public interface CellClickObserver {
        void onCellClick(int row, int col);
    }

    public static final int SIZE = 8;

    private static final double MIN_BOARD_SIZE = 320;
    private static final double PREF_BOARD_SIZE = 480;
    private static final double LABEL_SIZE = 24;

    private static final Color LIGHT = Color.web("#f0d9b5");
    private static final Color DARK = Color.web("#b58863");
    private static final Color HIGHLIGHT = Color.web("#6fbf73");
    private static final Color LABEL_COLOR = Color.web("#333333");

    private static final String[] FILES = {
        "A", "B", "C", "D", "E", "F", "G", "H"
    };

    private final Canvas canvas = new Canvas();

    private double lastBoardSize = 0;

    private CellClickObserver observer;

    protected Base() {
        getChildren().add(canvas);

        setMinSize(
            MIN_BOARD_SIZE + 2 * LABEL_SIZE,
            MIN_BOARD_SIZE + 2 * LABEL_SIZE
        );

        setPrefSize(
            PREF_BOARD_SIZE + 2 * LABEL_SIZE,
            PREF_BOARD_SIZE + 2 * LABEL_SIZE
        );

        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        canvas.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            this::handleClick
        );
    }

    public void setCellClickObserver(CellClickObserver observer) {
        this.observer = observer;
    }

    protected void requestRedraw() {
        if (lastBoardSize > 0) {
            redraw(lastBoardSize);
        }
    }

    @Override
    protected void layoutChildren() {
        double left = snappedLeftInset();
        double right = snappedRightInset();
        double top = snappedTopInset();
        double bottom = snappedBottomInset();

        double contentWidth = getWidth() - left - right;
        double contentHeight = getHeight() - top - bottom;

        double boardSize = Math.min(
            contentWidth - 2 * LABEL_SIZE,
            contentHeight - 2 * LABEL_SIZE
        );

        if (boardSize <= 0) {
            return;
        }

        double totalSize = boardSize + 2 * LABEL_SIZE;

        double x = left + (contentWidth - totalSize) / 2;
        double y = top + (contentHeight - totalSize) / 2;

        canvas.setWidth(totalSize);
        canvas.setHeight(totalSize);
        canvas.relocate(x, y);

        lastBoardSize = boardSize;
        redraw(boardSize);
    }

    protected abstract void drawPieces(
        GraphicsContext g,
        double boardX,
        double boardY,
        double cell
    );

    protected boolean isCellHighlighted(int row, int col) {
        return false;
    }

    protected void afterRedraw() {
    }

    private void redraw(double boardSize) {
        double totalSize = boardSize + 2 * LABEL_SIZE;

        GraphicsContext g = canvas.getGraphicsContext2D();

        g.clearRect(0, 0, totalSize, totalSize);

        double boardX = LABEL_SIZE;
        double boardY = LABEL_SIZE;
        double cell = boardSize / SIZE;

        drawBoard(g, boardX, boardY, cell);
        drawLabels(g, boardX, boardY, boardSize, cell);
        drawPieces(g, boardX, boardY, cell);

        afterRedraw();
    }

    private void drawBoard(
        GraphicsContext g,
        double boardX,
        double boardY,
        double cell
    ) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                g.setFill(
                    isCellHighlighted(row, col)
                        ? HIGHLIGHT
                        : (((row + col) % 2 == 0) ? LIGHT : DARK)
                );

                g.fillRect(
                    boardX + col * cell,
                    boardY + row * cell,
                    cell,
                    cell
                );
            }
        }
    }

    private void drawLabels(
        GraphicsContext g,
        double boardX,
        double boardY,
        double boardSize,
        double cell
    ) {
        g.setFill(LABEL_COLOR);
        g.setTextAlign(TextAlignment.CENTER);
        g.setTextBaseline(VPos.CENTER);

        for (int col = 0; col < SIZE; col++) {
            double x = boardX + (col + 0.5) * cell;
            double y = boardY + boardSize + LABEL_SIZE / 2;

            g.fillText(FILES[col], x, y);
        }

        for (int row = 0; row < SIZE; row++) {
            double x = boardX - LABEL_SIZE / 2;
            double y = boardY + (row + 0.5) * cell;

            g.fillText(String.valueOf(8 - row), x, y);
        }

        for (int col = 0; col < SIZE; col++) {
            double x = boardX + (col + 0.5) * cell;
            double y = boardY - LABEL_SIZE / 2;

            g.save();
            g.translate(x, y);
            g.rotate(180);
            g.fillText(FILES[7 - col], 0, 0);
            g.restore();
        }

        for (int row = 0; row < SIZE; row++) {
            double x = boardX + boardSize + LABEL_SIZE / 2;
            double y = boardY + (row + 0.5) * cell;

            g.save();
            g.translate(x, y);
            g.rotate(180);
            g.fillText(String.valueOf(row + 1), 0, 0);
            g.restore();
        }
    }

    private void handleClick(MouseEvent e) {
        if (observer == null) {
            return;
        }

        int[] cell = cellAt(e.getX(), e.getY());

        if (cell == null) {
            return;
        }

        observer.onCellClick(cell[0], cell[1]);
    }

    private int[] cellAt(double x, double y) {
        double boardSize = canvas.getWidth() - 2 * LABEL_SIZE;

        x -= LABEL_SIZE;
        y -= LABEL_SIZE;

        if (x < 0 || y < 0 || x >= boardSize || y >= boardSize) {
            return null;
        }

        double cell = boardSize / SIZE;

        int col = (int) (x / cell);
        int row = (int) (y / cell);

        return new int[] {row, col};
    }
}
