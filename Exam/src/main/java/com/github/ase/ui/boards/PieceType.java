package com.github.ase.ui.boards;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Scale;

/**
 * Piece silhouettes reuse the outlines from Wikimedia Commons' "SVG chess pieces" set
 * (Cburnett, CC BY-SA 3.0 / GFDL, https://commons.wikimedia.org/wiki/Category:SVG_chess_pieces).
 */
public enum PieceType {
    PAWN(
        new Segment(
            Segment.Kind.BODY,
            "m 22.5,9 c -2.21,0 -4,1.79 -4,4 0,0.89 0.29,1.71 0.78,2.38 "
                + "C 17.33,16.5 16,18.59 16,21 c 0,2.03 0.94,3.84 2.41,5.03 "
                + "C 15.41,27.09 11,31.58 11,39.5 H 34 "
                + "C 34,31.58 29.59,27.09 26.59,26.03 28.06,24.84 29,23.03 29,21 "
                + "29,18.59 27.67,16.5 25.72,15.38 26.21,14.71 26.5,13.89 26.5,13 "
                + "c 0,-2.21 -1.79,-4 -4,-4 z"
        )
    ),
    KNIGHT(
        new Segment(
            Segment.Kind.BODY,
            "M 22,10 C 32.5,11 38.5,18 38,39 L 15,39 C 15,30 25,32.5 23,18"
        ),
        new Segment(
            Segment.Kind.BODY,
            "M 24,18 C 24.38,20.91 18.45,25.37 16,27 C 13,29 13.18,31.34 11,31 "
                + "C 9.958,30.06 12.41,27.96 11,28 C 10,28 11.19,29.23 10,30 "
                + "C 9,30 5.997,31 6,26 C 6,24 12,14 12,14 "
                + "C 12,14 13.89,12.1 14,10.5 C 13.27,9.506 13.5,8.5 13.5,7.5 "
                + "C 14.5,6.5 16.5,10 16.5,10 L 18.5,10 "
                + "C 18.5,10 19.28,8.008 21,7 C 22,7 22,10 22,10"
        ),
        new Segment(Segment.Kind.ACCENT_DOT, "M 9.5 25.5 A 0.5 0.5 0 1 1 8.5,25.5 A 0.5 0.5 0 1 1 9.5 25.5 z"),
        new Segment(
            Segment.Kind.ACCENT_DOT,
            "M 15 15.5 A 0.5 1.5 0 1 1  14,15.5 A 0.5 1.5 0 1 1  15 15.5 z",
            new double[] {0.866, 0.5, -0.5, 0.866, 9.693, -5.173}
        )
    ),
    BISHOP(
        new Segment(
            Segment.Kind.BODY,
            "M 9,36 C 12.39,35.03 19.11,36.43 22.5,34 C 25.89,36.43 32.61,35.03 36,36 "
                + "C 36,36 37.65,36.54 39,38 C 38.32,38.97 37.35,38.99 36,38.5 "
                + "C 32.61,37.53 25.89,38.96 22.5,37.5 C 19.11,38.96 12.39,37.53 9,38.5 "
                + "C 7.65,38.99 6.68,38.97 6,38 C 7.35,36.54 9,36 9,36 z"
        ),
        new Segment(
            Segment.Kind.BODY,
            "M 15,32 C 17.5,34.5 27.5,34.5 30,32 C 30.5,30.5 30,30 30,30 "
                + "C 30,27.5 27.5,26 27.5,26 C 33,24.5 33.5,14.5 22.5,10.5 "
                + "C 11.5,14.5 12,24.5 17.5,26 C 17.5,26 15,27.5 15,30 C 15,30 14.5,30.5 15,32 z"
        ),
        new Segment(Segment.Kind.BODY, "M 25 8 A 2.5 2.5 0 1 1  20,8 A 2.5 2.5 0 1 1  25 8 z"),
        new Segment(
            Segment.Kind.ACCENT_LINE,
            "M 17.5,26 L 27.5,26 M 15,30 L 30,30 M 22.5,15.5 L 22.5,20.5 M 20,18 L 25,18"
        )
    ),
    ROOK(
        new Segment(Segment.Kind.BODY, "M 9,39 L 36,39 L 36,36 L 9,36 L 9,39 z"),
        new Segment(Segment.Kind.BODY, "M 12,36 L 12,32 L 33,32 L 33,36 L 12,36 z"),
        new Segment(
            Segment.Kind.BODY,
            "M 11,14 L 11,9 L 15,9 L 15,11 L 20,11 L 20,9 L 25,9 L 25,11 L 30,11 L 30,9 L 34,9 L 34,14"
        ),
        new Segment(Segment.Kind.BODY, "M 34,14 L 31,17 L 14,17 L 11,14"),
        new Segment(Segment.Kind.BODY, "M 31,17 L 31,29.5 L 14,29.5 L 14,17"),
        new Segment(Segment.Kind.BODY, "M 31,29.5 L 32.5,32 L 12.5,32 L 14,29.5"),
        new Segment(Segment.Kind.ACCENT_LINE, "M 11,14 L 34,14")
    ),
    QUEEN(
        new Segment(
            Segment.Kind.BODY,
            "M 9,26 C 17.5,24.5 30,24.5 36,26 L 38.5,13.5 L 31,25 L 30.7,10.9 "
                + "L 25.5,24.5 L 22.5,10 L 19.5,24.5 L 14.3,10.9 L 14,25 L 6.5,13.5 L 9,26 z"
        ),
        new Segment(
            Segment.Kind.BODY,
            "M 9,26 C 9,28 10.5,28 11.5,30 C 12.5,31.5 12.5,31 12,33.5 "
                + "C 10.5,34.5 11,36 11,36 C 9.5,37.5 11,38.5 11,38.5 "
                + "C 17.5,39.5 27.5,39.5 34,38.5 C 34,38.5 35.5,37.5 34,36 "
                + "C 34,36 34.5,34.5 33,33.5 C 32.5,31 32.5,31.5 33.5,30 "
                + "C 34.5,28 36,28 36,26 C 27.5,24.5 17.5,24.5 9,26 z"
        ),
        new Segment(Segment.Kind.BODY, "M 4,12 A 2,2 0 1 0 8,12 A 2,2 0 1 0 4,12 Z"),
        new Segment(Segment.Kind.BODY, "M 12,9 A 2,2 0 1 0 16,9 A 2,2 0 1 0 12,9 Z"),
        new Segment(Segment.Kind.BODY, "M 20.5,8 A 2,2 0 1 0 24.5,8 A 2,2 0 1 0 20.5,8 Z"),
        new Segment(Segment.Kind.BODY, "M 29,9 A 2,2 0 1 0 33,9 A 2,2 0 1 0 29,9 Z"),
        new Segment(Segment.Kind.BODY, "M 37,12 A 2,2 0 1 0 41,12 A 2,2 0 1 0 37,12 Z"),
        new Segment(Segment.Kind.ACCENT_LINE, "M 11.5,30 C 15,29 30,29 33.5,30"),
        new Segment(Segment.Kind.ACCENT_LINE, "M 12,33.5 C 18,32.5 27,32.5 33,33.5")
    ),
    KING(
        new Segment(Segment.Kind.ACCENT_LINE, "M22.5 11.63V6M20 8h5"),
        new Segment(
            Segment.Kind.BODY,
            "M22.5 25s4.5-7.5 3-10.5c0 0-1-2.5-3-2.5s-3 2.5-3 2.5c-1.5 3 3 10.5 3 10.5"
        ),
        new Segment(
            Segment.Kind.BODY,
            "M12.5 37c5.5 3.5 14.5 3.5 20 0v-7s9-4.5 6-10.5c-4-6.5-13.5-3.5-16 4V27v-3.5"
                + "c-2.5-7.5-12-10.5-16-4-3 6 6 10.5 6 10.5v7"
        ),
        new Segment(
            Segment.Kind.ACCENT_LINE,
            "M12.5 30c5.5-3 14.5-3 20 0m-20 3.5c5.5-3 14.5-3 20 0m-20 3.5c5.5-3 14.5-3 20 0"
        )
    );

    private static final double VIEW_BOX_SIZE = 45;

    private final Segment[] segments;

    PieceType(Segment... segments) {
        this.segments = segments;
    }

    Node createIcon(double size, Color fill, Color accent) {
        Group content = new Group();

        for (Segment segment : segments) {
            content.getChildren().add(segment.toNode(fill, accent));
        }

        Bounds bounds = content.getBoundsInLocal();
        content.setTranslateX(VIEW_BOX_SIZE / 2 - (bounds.getMinX() + bounds.getWidth() / 2));
        content.setTranslateY(VIEW_BOX_SIZE / 2 - (bounds.getMinY() + bounds.getHeight() / 2));

        Rectangle frame = new Rectangle(0, 0, VIEW_BOX_SIZE, VIEW_BOX_SIZE);
        frame.setFill(Color.TRANSPARENT);
        frame.setStroke(Color.TRANSPARENT);

        Group icon = new Group(frame, content);
        icon.getTransforms().add(new Scale(size / VIEW_BOX_SIZE, size / VIEW_BOX_SIZE));

        return icon;
    }

    private record Segment(Kind kind, String path, double[] matrix) {
        enum Kind { BODY, ACCENT_LINE, ACCENT_DOT }

        Segment(Kind kind, String path) {
            this(kind, path, null);
        }

        SVGPath toNode(Color fill, Color accent) {
            SVGPath svgPath = new SVGPath();
            svgPath.setContent(path);

            switch (kind) {
                case BODY -> {
                    svgPath.setFill(fill);
                    svgPath.setStroke(Color.BLACK);
                    svgPath.setStrokeWidth(1.5);
                }
                case ACCENT_LINE -> {
                    svgPath.setFill(Color.TRANSPARENT);
                    svgPath.setStroke(accent);
                    svgPath.setStrokeWidth(1);
                }
                case ACCENT_DOT -> {
                    svgPath.setFill(accent);
                    svgPath.setStroke(accent);
                }
            }

            if (matrix != null) {
                svgPath.getTransforms().add(
                    new Affine(matrix[0], matrix[2], matrix[4], matrix[1], matrix[3], matrix[5])
                );
            }

            return svgPath;
        }
    }
}
