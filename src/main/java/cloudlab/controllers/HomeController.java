package cloudlab.controllers;

// java
import java.io.IOException;
import java.sql.*;

// application
import cloudlab.App;

// javafx
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HomeController {
    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() throws SQLException {
        // Get user's name
        String name = "";
        if (App.cur_UID != -1) {
            var st = App.conn.prepareStatement("SELECT first_name, last_name FROM Users WHERE user_id=?");
            st.setInt(1, App.cur_UID);
            var rs = st.executeQuery();
            rs.next();
            name = rs.getString(1) + " " + rs.getString(2);
            rs.close();
            st.close();
        }

        // Set welcome label
        welcomeLabel.setText("Welcome, " + name);
    }

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
    public void deleteAccount() throws IOException, SQLException {
        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setTitle("Delete account");
        a.setHeaderText("Are you sure you want to delete your account?");
        a.setContentText("This action cannot be undone.");
        a.showAndWait();
        if (a.getResult().getText().equals("OK")) {
            var st = App.conn.prepareStatement("DELETE FROM Users WHERE user_id=?");
            st.setInt(1, App.cur_UID);
            st.executeUpdate();
            st.close();
            App.cur_UID = -1;

            App.setRoot("login_page");
        }
    }

    @FXML
    public void logout() throws IOException {
        App.setRoot("login_page");
    }
}
