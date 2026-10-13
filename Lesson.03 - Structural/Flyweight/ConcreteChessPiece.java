/**
 * CONCRETE FLYWEIGHT - a shareable chess piece type.
 *
 * Stores ONLY intrinsic state: the piece name and colour. On a standard
 * board there are at most 12 distinct (type, colour) combinations, so at
 * most 12 ConcreteChessPiece objects exist regardless of how many pieces
 * are on the board.
 */
public class ConcreteChessPiece implements ChessPiece {

    // ---- INTRINSIC STATE (shared, immutable) ----
    private final String type;   // King, Queen, Rook, ...
    private final String colour; // White / Black

    public ConcreteChessPiece(String type, String colour) {
        this.type   = type;
        this.colour = colour;
    }

    @Override
    public void place(int row, int col) {
        System.out.printf("  %-6s %s at [%d,%d]%n", colour, type, row, col);
    }

    @Override
    public String describe() {
        return colour + " " + type;
    }
}
