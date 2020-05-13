
import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.requests.ClearRequest;
import shapes.*;

import java.util.Arrays;
import java.util.List;


public class NetPaintToolBar extends VBox {

    private ToggleButton drawButton;
    private ToggleButton lineButton;
    private ToggleButton rectangleButton;
    private ToggleButton ovalButton;
    private Button clearButton;
    private Slider slider;
    private Label lineWidth;
    private ToggleGroup toggleGroup;
    private Button hostButton;
    private Button joinButton;
    private Button disconnectButton;
    private ColorPicker colorPicker;
    private List<NPShape> shapes;

    public NetPaintToolBar(){
        init();
    }

    private void init(){
        this.drawButton = new ToggleButton("Draw");
        this.lineButton = new ToggleButton("Line");
        this.rectangleButton = new ToggleButton("Rectangle");
        this.ovalButton = new ToggleButton("Oval");
        this.colorPicker = new ColorPicker(Color.BLACK);
        this.clearButton = new Button("Clear");
        this.slider = new Slider(1, 50, 3);
        this.lineWidth = new Label("3.0");
        this.toggleGroup = new ToggleGroup();
        this.hostButton = new Button("Host");
        this.joinButton = new Button("Join");
        this.disconnectButton = new Button("Disconnect");



        Observable.fromArray(drawButton, lineButton, rectangleButton, ovalButton).forEach(tool -> {
            tool.setMinWidth(90);
            tool.setToggleGroup(toggleGroup);
            tool.setCursor(Cursor.HAND);
            tool.setTextFill(Color.WHITE);
            tool.setStyle("-fx-background-color: #0066cc;");
        });
        shapes = Arrays.asList(new NPFreeHand(), new NPLine(), new NPRectangle(), new NPOval());

        colorPicker.setStyle("-fx-background-color: #3399ff;");

        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);

        clearButton.setMinWidth(90);
        clearButton.setCursor(Cursor.HAND);
        clearButton.setTextFill(Color.WHITE);
        clearButton.setStyle("-fx-background-color: #80334d;");

        hostButton.setMinWidth(90);
        hostButton.setCursor(Cursor.HAND);
        hostButton.setTextFill(Color.WHITE);
        hostButton.setStyle("-fx-background-color: #62BF5F;");

        joinButton.setMinWidth(90);
        joinButton.setCursor(Cursor.HAND);
        joinButton.setTextFill(Color.WHITE);
        joinButton.setStyle("-fx-background-color: #62BF5F;");

        disconnectButton.setMinWidth(90);
        disconnectButton.setCursor(Cursor.HAND);
        disconnectButton.setTextFill(Color.WHITE);
        disconnectButton.setStyle("-fx-background-color: #80334d;");
        disconnectButton.setVisible(false);

        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(drawButton, lineButton, rectangleButton, ovalButton, colorPicker, lineWidth, slider, clearButton);
        topVBox.setPadding(new Insets(20));
        topVBox.setSpacing(5);
        topVBox.setAlignment(Pos.TOP_CENTER);
        getChildren().add(topVBox);
        VBox.setVgrow(topVBox, Priority.ALWAYS);
        VBox botVBox = new VBox();

        botVBox.getChildren().addAll(hostButton, joinButton, disconnectButton);
        botVBox.setPadding(new Insets(20));
        botVBox.setSpacing(5);

        getChildren().add(botVBox);

        setStyle("-fx-background-color: #b3e6ff");
        setPrefWidth(100);
    }

    public void toggleNetButtons(){
        disconnectButton.setVisible(!disconnectButton.isVisible());
        joinButton.setVisible(!joinButton.isVisible());
        hostButton.setVisible(!hostButton.isVisible());
    }

    public Observable<ActionEvent> onDisconnectPressed(){
        return JavaFxObservable.actionEventsOf(disconnectButton);
    }

    public Observable<ActionEvent> onHostPressed(){
        return JavaFxObservable.actionEventsOf(hostButton);
    }

    public Observable<ActionEvent> onJoinPressed(){
        return JavaFxObservable.actionEventsOf(joinButton);
    }

    public Observable<ClearRequest> onClearScreen(){
        return JavaFxObservable.actionEventsOf(clearButton).map(event -> new ClearRequest());
    }

    public Observable<Number> onSliderChange(){
        return JavaFxObservable.valuesOf(slider.valueProperty());
    }

    public Observable<Color> onColorPickerChange(){
        return JavaFxObservable.valuesOf(colorPicker.valueProperty());
    }

    public Observable<NPShape> onShapeSelected(){
        return JavaFxObservable.valuesOf(toggleGroup.selectedToggleProperty()).map(toggle -> {
            return shapes.get(toggleGroup.getToggles().indexOf(toggle));
        });
    }


}
