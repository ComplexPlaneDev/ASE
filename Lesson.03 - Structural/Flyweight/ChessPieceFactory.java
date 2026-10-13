import java.util.HashMap;
import java.util.Map;

/**
 * FLYWEIGHT FACTORY for chess pieces.
 *
 * The cache key is "colour:type" (the intrinsic state). One
 * ConcreteChessPiece is created per distinct combination and shared
 * across the whole board.
 */
public class ChessPieceFactory {

    private final Map<String, ChessPiece> cache = new HashMap<>();

    public ChessPiece getPiece(String type, String colour) {
        String key = colour + ":" + type;
        return cache.computeIfAbsent(key, k -> new ConcreteChessPiece(type, colour));
    }

    public int getCachedCount() {
        return cache.size();
    }
}
