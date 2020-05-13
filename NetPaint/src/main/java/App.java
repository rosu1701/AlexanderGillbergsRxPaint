import io.reactivex.rxjavafx.schedulers.JavaFxScheduler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;
import net.NPClient;
import net.NPServer;
import net.requests.ShutDownRequest;

import java.time.Duration;

public class App extends Application {

    private BorderPane pane;
    private NetPaintToolBar toolBar;
    private NetPaintCanvas canvas;
    private Scene scene;
    private Label statusLabel;

    private final NPClient client = new NPClient();
    private final NPServer server = new NPServer();

    @Override
    public void start(Stage primaryStage) {
        pane = new BorderPane();
        toolBar = new NetPaintToolBar();
        canvas = new NetPaintCanvas(toolBar, 1100, 800);
        canvas.setDisable(true);
        statusLabel = new Label("You need to be connected to draw.");

        pane.setLeft(toolBar);
        pane.setCenter(canvas);

        canvas.getChildren().add(statusLabel);
        scene = new Scene(pane, 1200, 800);

        primaryStage.setTitle("NetPaint");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);

        toolBar.onHostPressed().subscribe(i -> {
            canvas.clearCanvas();
            if(setupServer())
                if(setupClient())
                    statusLabel.setText("Online");

        }, Throwable::printStackTrace);

        toolBar.onJoinPressed().subscribe(i -> {
            canvas.clearCanvas();
            NPPopup.display();
            if(setupClient())
                statusLabel.setText("Online");
        }, Throwable::printStackTrace);

        toolBar.onDisconnectPressed().subscribe(i -> {
            setDisconnectState();
            if(server.isRunning()){
                server.closeServer();
            }
        }, Throwable::printStackTrace);
    }

    @Override
    public void stop() throws Exception {
        client.disconnect();
        if(server.isRunning()){
            server.closeServer();
        }

    }

    private boolean setupClient(){
        client.listen().observeOn(JavaFxScheduler.platform()).subscribe(netRequest -> {
            if(netRequest instanceof ShutDownRequest){
                setDisconnectState();
            }
            else{
                canvas.handleNetRequest(netRequest);
            }
        }, Throwable::printStackTrace);

        if(client.startClient()){
            toolBar.toggleNetButtons();
            canvas.onShapeDraw().subscribe(client::send, Throwable::printStackTrace);
            toolBar.onClearScreen().subscribe(client::send, Throwable::printStackTrace);
            canvas.setDisable(false);
            return true;
        }
        return false;
    }

    private boolean setupServer(){
        if(server.startServer()){
            server.listen();
            return true;
        }
        return false;
    }

    private void setDisconnectState(){

        canvas.clearCanvas();
        client.disconnect();
        toolBar.toggleNetButtons();
        statusLabel.setText("You need to be connected to draw.");
        canvas.setDisable(true);
    }


    public static void main(String[] args) {


        launch();
    }
}
