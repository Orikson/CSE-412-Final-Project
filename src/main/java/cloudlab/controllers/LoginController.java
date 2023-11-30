package cloudlab.controllers;

// java
import java.io.IOException;

// application
import cloudlab.App;

// javafx
import javafx.fxml.FXML;

public class LoginController {
    @FXML
    public void signIn() throws IOException {
        App.setRoot("home");
    }

    @FXML
    public void signUp() throws IOException {
        App.setRoot("home");
    }
}
