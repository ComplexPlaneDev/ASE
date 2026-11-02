package com.github.ase.ui.widgets;

import com.github.ase.BoardModel;
import com.github.ase.ui.boards.BoardView;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {

    private final BoardModel model;

    public MainMenuView(BoardModel model) {
        this.model = model;
    }

    public void show(Stage stage) {
        var playChessButton = new Button("Play Chess");
        var playCheckersButton = new Button("Play Checkers");
        var quitButton = new Button("Quit");

        var playRow = new HBox(20, playChessButton, playCheckersButton);
        playRow.setAlignment(Pos.CENTER);

        var menu = new VBox(20, playRow, quitButton);
        menu.setAlignment(Pos.CENTER);
        VBox.setMargin(menu, new Insets(40));

        var scene = new Scene(menu, 360, 240);
        stage.setTitle("Board Game");
        stage.setScene(scene);
        stage.show();

        playChessButton.setOnAction(e -> startGame(stage, BoardModel.Game.CHESS));
        playCheckersButton.setOnAction(e -> startGame(stage, BoardModel.Game.CHECKERS));
        quitButton.setOnAction(e -> Platform.exit());
    }

    private void startGame(Stage stage, BoardModel.Game game) {
        model.setup(game);

        var board = new BoardView();
        board.setModel(model);

        new GameView(stage, board, this).show();
    }
}
