/**
 * FLYWEIGHT INTERFACE for a chess piece.
 *
 * Intrinsic state : the piece type (King, Queen, Rook, Bishop, Knight,
 *                   Pawn) and its colour (White / Black). A white rook is
 *                   always a white rook - that data never changes.
 *
 * Extrinsic state : the square (row, col) where this particular piece
 *                   currently sits on the board. The same type of piece
 *                   can occupy many squares over the course of a game.
 */
public interface ChessPiece {

    /**
     * Reports / renders this piece at the given board square.
     *
     * @param row board row 0-7 (extrinsic)
     * @param col board column 0-7 (extrinsic)
     */
    void place(int row, int col);

    /** Returns a human-readable description of the piece type. */
    String describe();
}
