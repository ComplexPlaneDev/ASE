/**
 * CLIENT - a chess board holding pieces.
 *
 * The board stores, for each occupied square, only:
 *   - a reference to the SHARED piece-type object (intrinsic state)
 *   - the (row, col) square (extrinsic state)
 *
 * A full starting position has 32 pieces but only 12 distinct
 * (type, colour) objects are ever created.
 */
public class ChessBoard {

    private final ChessPieceFactory factory;
    private final ChessPiece[][] squares = new ChessPiece[8][8];

    public ChessBoard(ChessPieceFactory factory) {
        this.factory = factory;
    }

    /** Places a piece of the given type/colour on square (row, col). */
    public void place(int row, int col, String type, String colour) {
        squares[row][col] = factory.getPiece(type, colour);  // shared
    }

    /** Prints the current board state. */
    public void display() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (squares[row][col] != null) {
                    squares[row][col].place(row, col);       // extrinsic passed in
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== CHESS BOARD (Flyweight) ===");
        ChessPieceFactory factory = new ChessPieceFactory();
        ChessBoard board = new ChessBoard(factory);

        // Standard starting position (rows 0-1 black, rows 6-7 white).
        String[] backRank = {"Rook", "Knight", "Bishop", "Queen",
                             "King", "Bishop", "Knight", "Rook"};
        for (int col = 0; col < 8; col++) {
            board.place(0, col, backRank[col], "Black");
            board.place(1, col, "Pawn",       "Black");
            board.place(6, col, "Pawn",       "White");
            board.place(7, col, backRank[col], "White");
        }

        System.out.println("Starting position (32 pieces):\n");
        board.display();

        int totalPieces = 32;
        System.out.printf("\nTotal pieces on board: %d%n", totalPieces);
        System.out.printf("Distinct piece-type objects (shared): %d%n",
                factory.getCachedCount());
    }
}
