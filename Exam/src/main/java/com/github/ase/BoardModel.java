package com.github.ase;

import java.util.List;

/**
 * The board's memory, as the user interface sees it.
 *
 * <p>This is the single seam between the rendering layer and the game. The UI
 * owns no piece data of its own: it tells the model which game to set up, then
 * to paint the board it asks the model, cell by cell, <em>"what should I draw
 * here?"</em> and renders whatever code it gets back. Students implement this
 * interface to provide the board's state.
 *
 * <h2>Lifecycle</h2>
 * <ol>
 *   <li>The UI constructs a model and calls {@link #setup(Game)} once, telling
 *       it which {@link Game} to arrange in its starting position.</li>
 *   <li>The UI then renders the board. On every repaint it reads
 *       {@link #currentPlayer()} and {@link #getStatus()} (to update the turn
 *       label and status bar), and for every cell it asks
 *       {@link #isCellHighlighted(int, int)} (to pick the background) and
 *       {@link #pieceAt(int, int)} (to pick the piece glyph).</li>
 *   <li>When the user clicks a cell the UI calls
 *       {@link #onCellClick(int, int)}, then repaints the board and refreshes
 *       the move list &mdash; so any highlight, piece move, or recorded move the
 *       model changed in response is reflected immediately.</li>
 *   <li>The move list is filled from {@link #moves()}. Selecting one of its
 *       entries calls {@link #onSelectMove(String)}, after which the UI repaints
 *       &mdash; letting the model restore the board to that point in the game.</li>
 * </ol>
 *
 * <h2>Piece codes</h2>
 * <p>A cell's contents are described by a short <b>piece code</b>: a one-letter
 * colour prefix followed by a one-letter type. The codes are case-insensitive.
 *
 * <table border="1">
 *   <caption>Piece codes</caption>
 *   <tr><th>Game</th><th>Colours</th><th>Types</th><th>Examples</th></tr>
 *   <tr>
 *     <td>Chess</td>
 *     <td>{@code w} = white, {@code b} = black</td>
 *     <td>{@code K Q R B N P} (king, queen, rook, bishop, knight, pawn)</td>
 *     <td>{@code "wK"}, {@code "bQ"}, {@code "wP"}</td>
 *   </tr>
 *   <tr>
 *     <td>Checkers</td>
 *     <td>{@code l} = light, {@code d} = dark</td>
 *     <td>{@code M} = man, {@code K} = king</td>
 *     <td>{@code "lM"}, {@code "dK"}</td>
 *   </tr>
 * </table>
 *
 * <p>The renderer that consumes this interface is
 * {@code com.github.ase.ui.boards.BoardView}.
 */
public interface BoardModel {

    /** Side length of the (square) board, in cells. */
    int SIZE = 8;

    /** The game a board can be set up for. */
    enum Game {
        CHESS,
        CHECKERS
    }

    /**
     * Arranges the board in the starting position for the given game.
     *
     * <p>Called once by the UI before any rendering. Implementations build the
     * initial layout here (which pieces sit on which cells); afterwards
     * {@link #pieceAt(int, int)} reflects that layout.
     *
     * @param game which game to set up
     */
    void setup(Game game);

    /**
     * Describes what to paint in a single cell.
     *
     * @param row the row, {@code 0}..{@code SIZE - 1} (top to bottom)
     * @param col the column, {@code 0}..{@code SIZE - 1} (left to right)
     * @return the piece code for this cell (see {@link BoardModel}), or
     *         {@code null} / an empty string for an empty cell
     */
    String pieceAt(int row, int col);

    /**
     * Reports whether a cell should be drawn with a highlighted background.
     *
     * <p>Called by the UI for every cell on each repaint, so the answer may
     * change over time (for example, after a click selects a piece and the
     * model marks its legal destinations).
     *
     * @param row the row, {@code 0}..{@code SIZE - 1}
     * @param col the column, {@code 0}..{@code SIZE - 1}
     * @return {@code true} to highlight this cell, {@code false} to leave it plain
     */
    boolean isCellHighlighted(int row, int col);

    /**
     * Notifies the model that the user clicked a cell.
     *
     * <p>The UI repaints the board immediately after this call, so the model
     * may change what {@link #pieceAt(int, int)} and
     * {@link #isCellHighlighted(int, int)} return and have it shown at once.
     *
     * @param row the clicked row, {@code 0}..{@code SIZE - 1}
     * @param col the clicked column, {@code 0}..{@code SIZE - 1}
     */
    void onCellClick(int row, int col);

    /**
     * The moves played so far, oldest first, each as a string ID.
     *
     * <p>The UI shows these in the move list; the same ID is passed back to
     * {@link #onSelectMove(String)} when the user picks one. The ID is opaque to
     * the UI &mdash; it can be any token the model recognises.
     *
     * @return the move IDs in play order (empty before the first move)
     */
    List<String> moves();

    /**
     * Notifies the model that the user selected a move from the move list.
     *
     * <p>The UI repaints the board immediately after this call, so the model can
     * restore the position as it was at that move (e.g. for replay or undo).
     *
     * @param moveId one of the IDs returned by {@link #moves()}
     */
    void onSelectMove(String moveId);

    /**
     * The name of the player whose turn it currently is.
     *
     * <p>Read by the UI on every repaint and shown in the turn label as
     * <em>"Player {@code <name>} ..."</em>. The model is free to return anything
     * meaningful (for example {@code "White"}, {@code "Dark"}, or a human name).
     *
     * @return the current player's name
     */
    String currentPlayer();

    /**
     * A free-form status line shown in the status bar.
     *
     * <p>Read by the UI on every repaint. The model decides the text entirely
     * &mdash; e.g. {@code "Check!"}, a move count, or whose turn it is &mdash; and
     * can change it between repaints.
     *
     * @return the status text to display (an empty string clears the bar)
     */
    String getStatus();
}
