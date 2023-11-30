package cloudlab.controllers;

// java
import java.io.IOException;

// application
import cloudlab.App;

// javafx
import javafx.fxml.FXML;

public class HomeController {
    @FXML
    public void myGroups() throws IOException {
        App.setRoot("my_groups");
    }

    @FXML
    public void browseGroups() throws IOException {
        App.setRoot("browse_groups");
    }

    @FXML
    public void myExperiments() throws IOException {
        App.setRoot("my_experiments");
    }

    @FXML
    public void createExperiment() throws IOException {
        App.setRoot("create_experiment");
    }

    @FXML
    public void deleteAccount() throws IOException {
        App.setRoot("login_page");
    }

    @FXML
    public void logout() throws IOException {
        App.setRoot("login_page");
    }
}
