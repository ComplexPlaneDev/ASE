package com.github.ase;

import com.github.ase.ui.widgets.MainMenuView;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        // STUDENTS: edit ONLY the line below.
        // Create an instance of YOUR BoardModel implementation here, e.g.:
        //     BoardModel model = new MyBoardModel();
        // Until you do, the app compiles but fails as soon as you start a game.
        BoardModel model = null; // TODO: replace null with your BoardModel implementation

        MainMenuView mainMenu = new MainMenuView(model);
        mainMenu.show(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
