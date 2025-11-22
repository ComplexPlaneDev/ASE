package MathPlot;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        final TextArea exprTextArea = new TextArea();
        exprTextArea.setFont(new Font("Courier New", 16));
        exprTextArea.setPrefRowCount(1);
        exprTextArea.setWrapText(false);
        exprTextArea.setMaxWidth(Double.MAX_VALUE);
        final VBox exprBox = new VBox(new Text("Expression:"), exprTextArea);
        exprBox.setAlignment(Pos.CENTER_LEFT);
        VBox.setVgrow(exprTextArea, Priority.ALWAYS);

        final ToggleGroup exprToggleGroup = new ToggleGroup();
        final RadioButton aosButton = new RadioButton("AOS");
        aosButton.setToggleGroup(exprToggleGroup);
        aosButton.setSelected(true);
        final RadioButton rpnButton = new RadioButton("RPN");
        rpnButton.setToggleGroup(exprToggleGroup);
        final HBox exprInputBox = new HBox(aosButton, rpnButton);
        exprInputBox.setAlignment(Pos.CENTER_RIGHT);
        exprInputBox.setSpacing(3);
        final Button plotButton = new Button("Plot");
        plotButton.setMaxWidth(Double.MAX_VALUE);
        final VBox controlBox = new VBox(exprInputBox, plotButton);
        controlBox.setAlignment(Pos.CENTER_RIGHT);
        controlBox.setPadding(new Insets(10));
        controlBox.setSpacing(5);

        final HBox topRowBox = new HBox(exprBox, controlBox);
        topRowBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(exprBox, Priority.ALWAYS);
        HBox.setHgrow(exprTextArea, Priority.ALWAYS);
        HBox.setHgrow(controlBox, Priority.NEVER);
        topRowBox.setPadding(new Insets(10));

        final Canvas cartesianCanvas = new Canvas(600, 600);
        final VBox cartesianBox = new VBox(new Text("Cartesian"), cartesianCanvas);
        cartesianBox.setSpacing(3);
        final Canvas polarCanvas = new Canvas(600, 600);
        final VBox polarBox = new VBox(new Text("Polar"), polarCanvas);
        polarBox.setSpacing(3);
        final HBox plots = new HBox(cartesianBox, polarBox);
        plots.setPadding(new Insets(10));
        plots.setSpacing(10);

        clearCanvas(cartesianCanvas);
        clearCanvas(polarCanvas);

        final MathPlot mathPlot = new MathPlot();

        final Text areaText = new Text();

        final ToggleGroup areaToggleGroup = new ToggleGroup();
        final RadioButton rectButton = new RadioButton("Rectangular");
        rectButton.setToggleGroup(areaToggleGroup);
        rectButton.setSelected(true);
        final RadioButton trapButton = new RadioButton("Trapezoidal");
        trapButton.setToggleGroup(areaToggleGroup);
        final Button areaButton = new Button("Calculate");
        final HBox areaButtonsBox = new HBox(rectButton, trapButton, areaButton);
        areaButtonsBox.setSpacing(3);
        areaButtonsBox.setAlignment(Pos.CENTER_LEFT);
        final VBox areaBox = new VBox(areaText, areaButtonsBox);
        areaBox.setPadding(new Insets(10));
        areaBox.setSpacing(10);

        final TextArea exprPrintArea = new TextArea();
        exprPrintArea.setFont(new Font("Courier New", 16));
        exprPrintArea.setEditable(false);
        exprPrintArea.setPrefRowCount(4);
        exprPrintArea.setWrapText(false);
        exprPrintArea.setMaxWidth(Double.MAX_VALUE);
        final VBox exprPrintBox = new VBox(new Text("Print:"), exprPrintArea);
        exprPrintBox.setPadding(new Insets(10));

        plotButton.setOnAction(e -> {
            if (exprTextArea.getText().isEmpty()) {
                return;
            }

            mathPlot.setExpression(exprTextArea.getText(), aosButton.isSelected()
                    ? MathPlot.ExpressionFormat.AOS
                    : MathPlot.ExpressionFormat.RPN);

            mathPlot.plot(cartesianCanvas, MathPlot.PlotType.Cartesian);
            mathPlot.plot(polarCanvas, MathPlot.PlotType.Polar);

            final List<String> aosPrint = mathPlot.print(MathPlot.ExpressionFormat.AOS);
            for (final String line : aosPrint) {
                exprPrintArea.appendText("AOS: " + line + "\n");
            }

            final List<String> rpnPrint = mathPlot.print(MathPlot.ExpressionFormat.RPN);
            for (final String line : rpnPrint) {
                exprPrintArea.appendText("RPN: " + line + "\n");
            }
        });

        areaButton.setOnAction(e -> {
            final double area = mathPlot.area(rectButton.isSelected() ?
                    MathPlot.AreaType.Rectangular : MathPlot.AreaType.Trapezoidal);
            areaText.setText(String.format("Area: %.3f", area));
        });

        final Scene scene = new Scene(new VBox(topRowBox, plots, areaBox, exprPrintBox));
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void clearCanvas(Canvas canvas) {
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
}