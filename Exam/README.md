# Advanced Software Engineering — Exam Project

**A JavaFX board game (chess + checkers on one 8×8 board).** The user interface is
already written. The game logic is not — that's your job.

> **Submission deadline: 2 December 2026.**

---

## The task in one sentence

Implement the **`com.github.ase.BoardModel`** interface so the provided UI becomes a
playable game, and wire your implementation into the app at the single designated line.

That's it. The whole exam lives behind that one interface.

---

## What you get, and the one seam you work through

The entire application is split by a single interface:

- The **UI** (`com.github.ase.ui.*`) is complete. It owns **no game state**. To draw the
  board it asks the model, cell by cell, *"what piece is here?"* (`pieceAt`) and *"is this
  cell highlighted?"* (`isCellHighlighted`), and reads `currentPlayer()` / `getStatus()` on
  every repaint. User input flows back through `onCellClick` and `onSelectMove`.
- **`com.github.ase.BoardModel`** is your contract. Read its Javadoc carefully — it defines
  the lifecycle, the piece codes, and what each method must do.

Pieces cross the seam as short **string codes**, never objects:

| Game     | Colours                        | Types                                      | Examples            |
|----------|--------------------------------|--------------------------------------------|---------------------|
| Chess    | `w` = white, `b` = black       | `K Q R B N P` (king, queen, rook, bishop, knight, pawn) | `"wK"`, `"bQ"`, `"wP"` |
| Checkers | `l` = light, `d` = dark        | `M` = man, `K` = king                      | `"lM"`, `"dK"`      |

`null` or `""` means an empty cell.

---

## The two edits you are allowed to make

1. **Create your implementation.** Put it in your own package. It must `implement com.github.ase.BoardModel`.
2. **Plug it in.** In `App.java` there is exactly one line marked for you:

   ```java
   // STUDENTS: edit ONLY the line below.
   // Create an instance of YOUR BoardModel implementation here.
   BoardModel model = null; // TODO: replace null with your BoardModel implementation
   ```

   Replace `null` with `new YourBoardModel()` (your class). **This is the only edit to
   existing code you may make.**

> **The app will not run until you do this.** As delivered, `model` is `null`, so the project
> compiles but the app fails as soon as you start a game. That is intentional: wiring in your
> implementation on this one line is what brings it to life.

### Hard rules

- **Do not touch any UI code or any other existing code** — nothing under
  `com.github.ase.ui.*`, and no changes to `App.java` beyond the one marked line.
- **Do not modify the `BoardModel` interface.** Implement it as given.
- Your code depends **inward** on `BoardModel` only. It must not import anything from
  `com.github.ase.ui.*`.
- **No external dependencies.** You may use anything from the Java standard library (the JDK)
  freely, but you must **not add any dependency to `pom.xml`** — no third-party libraries. If
  it isn't bundled with Java, you can't use it. (The libraries already declared in `pom.xml`,
  such as the test framework, stay as they are; just don't add more.)

---

## What you must build

### Minimum to pass

**One complete, playable game** — either chess **or** checkers (your choice) — driven by
two human players through the UI:

- Correct starting position for the chosen game.
- Legal move enforcement for every piece type in that game.
- Turns alternate correctly; captures work.
- The game reaches a proper end (see **How a game ends** below) and the result is reported.

The UI exposes a cell-highlight hook (`isCellHighlighted`), but **what to highlight, and
when, is entirely your call** — there is no required highlighting behaviour.

The rules are the **simplified** variant. There is **no checkmate**: the game ends by
*capture*, with a well-defined final move.

**How a game ends**

- **Chess** — there is no checkmate and no check-induced restriction on moves. The game
  ends when a king is **captured**: a player is allowed to move a piece onto the opponent's
  king, and that capture is the final move. Once a king is taken, the game is over and its
  owner has lost.
- **Checkers** — the game ends when a player has **no pieces left** on the board (all of
  them have been captured). The move that removes the opponent's last piece is the final
  move, and that player has lost.

After the final move, report the outcome (e.g. via `getStatus()`) and stop accepting
further moves.

**"No special moves" — what is excluded**

- **Chess** — plain moves and captures only. **No** castling, **no** en passant, **no**
  pawn promotion (a pawn reaching the last rank simply stays a pawn), **no** check or
  checkmate rules (a king may legally move into or stay in an attacked square; you win by
  actually capturing it, not by delivering mate). Pawns, knights, bishops, rooks, queens,
  and kings move exactly as their basic geometry allows, nothing more.
- **Checkers** — basic men and kings only, with the simplified rules spelled out in
  **Checkers rules to implement** below. **No** mandatory/forced captures, **no** multi-jump
  chains, and no other variant rules.

**Checkers rules to implement (simplified Italian draughts)**

If you choose checkers, implement exactly these rules — nothing more:

1. **Men move one step forward**, diagonally only (toward the opponent's side).
2. **Captures are single and optional.** A piece captures by jumping one adjacent opponent
   piece into the empty square immediately beyond it. **Each piece captures at most once per
   turn** — there are no multi-jump chains — and **capturing is never mandatory**: a player
   may always choose an ordinary move instead.
3. **Kings move one step diagonally, forward or backward.** A king captures under the same
   rule as (2): a single jump over one adjacent opponent piece, optional, at most once per
   turn.

A man that reaches the far row is promoted to a king (from then on it may also move and
capture backward, per rule 3).

### Extensibility (mandatory — part of the pass bar)

You only have to deliver **one** working game, but your design must be **open to the other
one**. Adding the second game (chess if you built checkers, or vice versa) must be possible
by **adding new code only** — new classes for the other game's pieces, rules, and setup —
**without modifying any of your existing classes**. The game-selection seam in
`BoardModel.setup(Game)` is there to support exactly this.

Concretely, this rules out designs such as hard-coding one game's rules directly in your
`BoardModel` implementation, or branching on the game type with `if`/`switch` scattered
through your move logic — because dropping in the second game would then force you to edit
that existing code. A grader will check whether the second game *could* be introduced this
way, so make the extension point explicit and be ready to justify your design.

### Bonus

- **Chess — check detection.** Detect when a king is in check and surface it (e.g. via
  `getStatus()`). (This is a bonus, *not* checkmate — just recognising check.)
- **Move-replay handling.** The interface carries a move-list hook: `moves()` reports the
  moves played so far and `onSelectMove` is called when the user picks one, so you can let
  the model keep a history and navigate or replay the game to an earlier point. This is
  **optional**: `moves()` may always return an empty list, in which case there is simply no
  history and no undo — that is perfectly acceptable for a pass.

---

## Testing requirements

Unit testing is **mandatory**. An implementation is only graded if it is **properly
covered by tests**, and the minimum requirement is **80% of your total code covered**.

This threshold is **enforced by the build**: JaCoCo is configured to fail `mvn test` if line
coverage of your code drops below 80%. The UI and `App` are excluded from the
measurement, so the figure reflects *your* code only. The report is written to
`target/site/jacoco/index.html` — open it to see exactly what is and isn't covered.

- **Test everything you write.** Every piece of game logic you implement needs tests.
- **Tests must stay inside the `BoardModel` scope.** A test may touch your implementation
  and the `BoardModel` interface and standard Java — **nothing else**. In particular:
  **no UI classes** (`com.github.ase.ui.*`) may appear in your tests, directly or
  indirectly, and tests must run **without JavaFX on the thread**. Your game logic is
  unit-testable precisely because it never depends on the UI — your tests prove it.
- JUnit 5 and Mockito are already on the classpath.

---

## Build & run

Requires **JDK 25** and Maven. (JavaFX is pulled in by Maven; `mvn javafx:run` opens a
window, so you need a display.)

```bash
mvn compile                 # compile
mvn test                    # run all tests (JUnit 5)
mvn javafx:run              # launch the app
mvn -q test -Dtest=YourTestClass             # run one test class
mvn -q test -Dtest=YourTestClass#yourMethod  # run one test method
```

A JaCoCo coverage report is produced under `target/site/jacoco/` after `mvn test`.

---

## Checklist before you submit

- [ ] My implementation class implements `com.github.ase.BoardModel`.
- [ ] `App.java`'s one marked line constructs my class; nothing else in existing code changed.
- [ ] No imports from `com.github.ase.ui.*` anywhere in my code or tests.
- [ ] `mvn compile` succeeds and `mvn javafx:run` plays a full game.
- [ ] `mvn test` passes; tests use only `BoardModel`-scope classes and no JavaFX.
- [ ] The second game could be added by writing new classes only — none of my existing classes would need changing.
- [ ] I added no dependencies to `pom.xml` — my code uses only the Java standard library.
