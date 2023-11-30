package cloudlab.controllers;

// java
import java.io.IOException;

// application
import cloudlab.App;

// javafx
import javafx.fxml.FXML;

public class MyGroupsController {
    @FXML
    public void back() throws IOException {
        App.setRoot("home");
    }
}
