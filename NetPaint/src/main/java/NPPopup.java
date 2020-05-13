import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.stage.Modality;
import javafx.stage.Stage;
import net.NPServer;


public class NPPopup {

    public static void display(){
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setMinWidth(300);
        stage.setMinHeight(150);

        Label ipLabel = new Label();
        ipLabel.setText("IP: ");
        Label portLabel = new Label();
        portLabel.setText("Port: ");
        TextField ipField = new TextField(NPServer.IP_ADDRESS);
        TextField portField = new TextField(NPServer.TCP_PORT + "");


        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            NPServer.IP_ADDRESS = ipField.getText();
            stage.close();
        });


        GridPane layout = new GridPane();

        layout.add(ipLabel, 0, 0);
        layout.add(portLabel, 0, 1);
        layout.add(ipField, 1, 0);
        layout.add(portField, 1, 1);
        layout.add(confirmButton, 0, 2);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
