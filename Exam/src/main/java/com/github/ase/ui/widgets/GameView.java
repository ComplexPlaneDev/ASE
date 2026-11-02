package com.github.ase.ui.widgets;

import com.github.ase.BoardModel;
import com.github.ase.ui.boards.BoardView;

import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GameView {

    private final Stage stage;
    private final BoardView board;
    private final MainMenuView mainMenu;

    private Label turnLabel;
    private ListView<String> movesList;
    private Label statusBarLabel;

    private boolean refreshingMoves = false;

    private final ObservableList<String> movesItems =
        FXCollections.observableArrayList();

    public GameView(Stage stage, BoardView board, MainMenuView mainMenu) {
        this.stage = stage;
        this.board = board;
        this.mainMenu = mainMenu;
    }

    public void show() {
        build();
    }

    private void build() {
        turnLabel = new Label("Player One to move");
        turnLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

        movesList = new ListView<>();
        movesList.setItems(movesItems);
        VBox.setVgrow(movesList, Priority.ALWAYS);

        movesList.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldId, newId) -> {
                if (!refreshingMoves && newId != null) {
                    board.selectMove(newId);
                }
            }
        );

        board.setOnModelChanged(this::refreshMoves);
        board.setOnRedraw(this::refreshStatus);
        refreshMoves();

        var rightColumn = new VBox(10, new Label("Moves:"), movesList);
        rightColumn.setPrefWidth(220);
        BorderPane.setMargin(rightColumn, new Insets(10));

        var leftColumn = new VBox(10, turnLabel, board);
        leftColumn.setAlignment(Pos.CENTER);
        HBox.setHgrow(leftColumn, Priority.ALWAYS);
        VBox.setVgrow(board, Priority.ALWAYS);
        var center = new HBox(10, leftColumn, rightColumn);
        HBox.setHgrow(center, Priority.ALWAYS);
        HBox.setMargin(center, new Insets(10));

        statusBarLabel = new Label("This is a status bar");
        statusBarLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 16));

        var backButton = new Button("Back");
        backButton.setOnAction(e -> mainMenu.show(stage));

        var statusSpacer = new Region();
        HBox.setHgrow(statusSpacer, Priority.ALWAYS);

        var statusBar = new HBox(20, statusBarLabel, statusSpacer, backButton);
        statusBar.setAlignment(Pos.CENTER_LEFT);
        BorderPane.setMargin(statusBar, new Insets(15, 0, 0, 0));

        var root = new BorderPane();
        root.setPadding(new Insets(15));
        root.setCenter(center);
        root.setBottom(statusBar);

        var scene = new Scene(root, 820, 640);
        stage.setScene(scene);

        Platform.runLater(() -> {
            double decorationWidth = stage.getWidth() - scene.getWidth();
            double decorationHeight = stage.getHeight() - scene.getHeight();

            stage.setMinWidth(root.minWidth(-1) + decorationWidth);
            stage.setMinHeight(root.minHeight(-1) + decorationHeight);
        });
    }

    private void refreshMoves() {
        BoardModel model = board.getModel();
        List<String> moves = (model == null) ? List.of() : model.moves();

        refreshingMoves = true;
        try {
            boolean changed = !movesItems.equals(moves);
            movesItems.setAll(moves);
            if (changed) {
                movesList.getSelectionModel().clearSelection();
            }
        } finally {
            refreshingMoves = false;
        }
    }

    private void refreshStatus(BoardModel model) {
        turnLabel.setText("Player " + model.currentPlayer() + " to move");
        statusBarLabel.setText(model.getStatus());
    }
}