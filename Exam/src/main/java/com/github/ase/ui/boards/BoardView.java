package com.github.ase.ui.boards;

import com.github.ase.BoardModel;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class BoardView extends Base {

    private static final int MAX_CACHED_PIECE_IMAGES = 64;

    private static final Color CHESS_WHITE = Color.WHITE;
    private static final Color CHESS_BLACK = Color.web("#222222");

    private static final Color CHECKER_LIGHT = Color.web("#f5f5f0");
    private static final Color CHECKER_DARK = Color.web("#2b2b2b");
    private static final Color CHECKER_OUTLINE = Color.web("#111111");

    private final Map<String, Image> pieceImages = new HashMap<>();

    private BoardModel model;
    private Runnable onModelChanged;
    private Consumer<BoardModel> onRedraw;

    public BoardView() {
        setCellClickObserver((row, col) -> {
            if (model != null) {
                model.onCellClick(row, col);
                requestRedraw();
                fireModelChanged();
            }
        });
    }

    public void setModel(BoardModel model) {
        this.model = model;
        requestRedraw();
        fireModelChanged();
    }

    public BoardModel getModel() {
        return model;
    }

    public void setOnModelChanged(Runnable onModelChanged) {
        this.onModelChanged = onModelChanged;
    }

    public void setOnRedraw(Consumer<BoardModel> onRedraw) {
        this.onRedraw = onRedraw;
    }

    public void selectMove(String moveId) {
        if (model != null) {
            model.onSelectMove(moveId);
            requestRedraw();
            fireModelChanged();
        }
    }

    private void fireModelChanged() {
        if (onModelChanged != null) {
            onModelChanged.run();
        }
    }

    @Override
    protected void afterRedraw() {
        if (onRedraw != null && model != null) {
            onRedraw.accept(model);
        }
    }

    @Override
    protected boolean isCellHighlighted(int row, int col) {
        return model != null && model.isCellHighlighted(row, col);
    }

    @Override
    protected void drawPieces(
        GraphicsContext g,
        double boardX,
        double boardY,
        double cell
    ) {
        if (model == null) {
            return;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                String code = model.pieceAt(row, col);

                if (code == null || code.length() < 2) {
                    continue;
                }

                double x = boardX + col * cell;
                double y = boardY + row * cell;

                drawPiece(g, code, x, y, cell);
            }
        }
    }

    private void drawPiece(
        GraphicsContext g,
        String code,
        double cellX,
        double cellY,
        double cell
    ) {
        char color = Character.toLowerCase(code.charAt(0));
        char type = Character.toUpperCase(code.charAt(1));

        boolean chess = color == 'w' || color == 'b';
        boolean checkers = color == 'l' || color == 'd';

        if (chess) {
            PieceType chessType = chessTypeFor(type);
            if (chessType != null) {
                drawChessPiece(g, chessType, color, cellX, cellY, cell);
            }
        } else if (checkers && (type == 'M' || type == 'K')) {
            drawCheckerPiece(g, type == 'K', color, cellX, cellY, cell);
        }
    }

    private static PieceType chessTypeFor(char type) {
        return switch (type) {
            case 'P' -> PieceType.PAWN;
            case 'N' -> PieceType.KNIGHT;
            case 'B' -> PieceType.BISHOP;
            case 'R' -> PieceType.ROOK;
            case 'Q' -> PieceType.QUEEN;
            case 'K' -> PieceType.KING;
            default -> null;
        };
    }

    private void drawChessPiece(
        GraphicsContext g,
        PieceType type,
        char color,
        double cellX,
        double cellY,
        double cell
    ) {
        double pieceSize = cell * 0.8;
        boolean white = color != 'b';

        Image image = chessImage(type, white, pieceSize);

        g.drawImage(
            image,
            cellX + (cell - image.getWidth()) / 2,
            cellY + (cell - image.getHeight()) / 2
        );
    }

    private Image chessImage(PieceType type, boolean white, double size) {
        if (pieceImages.size() >= MAX_CACHED_PIECE_IMAGES) {
            pieceImages.clear();
        }

        int px = Math.max(1, (int) Math.round(size));
        String key = type + "|" + (white ? "w" : "b") + "|" + px;

        return pieceImages.computeIfAbsent(key, k -> {
            WritableImage image = new WritableImage(px, px);

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);

            Color fill = white ? CHESS_WHITE : CHESS_BLACK;
            Color accent = white ? CHESS_BLACK : CHESS_WHITE;

            type.createIcon(px, fill, accent).snapshot(params, image);

            return image;
        });
    }

    private void drawCheckerPiece(
        GraphicsContext g,
        boolean king,
        char color,
        double cellX,
        double cellY,
        double cell
    ) {
        double diameter = cell * 0.75;
        double inset = (cell - diameter) / 2;
        double x = cellX + inset;
        double y = cellY + inset;

        boolean light = color != 'd';
        Color body = light ? CHECKER_LIGHT : CHECKER_DARK;

        g.setFill(body);
        g.fillOval(x, y, diameter, diameter);

        g.setStroke(CHECKER_OUTLINE);
        g.setLineWidth(Math.max(1, diameter * 0.05));
        g.strokeOval(x, y, diameter, diameter);

        if (king) {
            double ringInset = diameter * 0.22;

            g.setStroke(light ? CHECKER_DARK : CHECKER_LIGHT);
            g.setLineWidth(Math.max(1, diameter * 0.06));
            g.strokeOval(
                x + ringInset,
                y + ringInset,
                diameter - 2 * ringInset,
                diameter - 2 * ringInset
            );
        }
    }
}
