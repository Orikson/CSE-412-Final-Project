package cloudlab.controllers;

// java
import java.io.IOException;
import java.sql.*;
import org.postgresql.util.PSQLException;

// application
import cloudlab.App;

// javafx
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class LoginController {
    // Sign in controls
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    // Sign up controls
    @FXML
    private TextField su_emailField;

    @FXML
    private PasswordField su_passwordField;

    @FXML
    private TextField su_fnameField;

    @FXML
    private TextField su_lnameField;

    @FXML
    public void signIn() throws IOException, SQLException {
        String email = emailField.getText();
        String password = passwordField.getText();

        // Send SQL query to validate credentials
        PreparedStatement st = App.conn
                .prepareStatement("SELECT user_id FROM Users WHERE email=? AND password=?");
        st.setString(1, email);
        st.setString(2, password);
        ResultSet rs = st.executeQuery();
        if (!rs.next()) {
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Invalid credentials");
            a.setContentText("Please check your email and password and try again.");
            a.showAndWait();
            rs.close();
            st.close();
            return;
        }
        App.cur_UID = rs.getInt(1);
        rs.close();
        st.close();

        App.setRoot("home");
    }

    @FXML
    public void signUp() throws IOException, SQLException {
        String email = su_emailField.getText();
        String password = su_passwordField.getText();
        String fname = su_fnameField.getText();
        String lname = su_lnameField.getText();

        // Send SQL query to insert new user
        if (email.isEmpty() || password.isEmpty() || fname.isEmpty() || lname.isEmpty()) {
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Invalid input");
            a.setContentText("Please verify all entries are non-empty");
            a.showAndWait();
            return;
        }

        try {
            PreparedStatement st = App.conn.prepareStatement(
                    "INSERT INTO Users (email, password, first_name, last_name) VALUES (?, ?, ?, ?) RETURNING user_id");
            st.setString(1, email);
            st.setString(2, password);
            st.setString(3, fname);
            st.setString(4, lname);
            ResultSet rs = st.executeQuery();
            rs.next();
            App.cur_UID = rs.getInt(1);
            rs.close();
            st.close();
        } catch (PSQLException e) {
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Invalid input");
            a.setContentText("Email already in use");
            a.showAndWait();
            return;
        }

        App.setRoot("home");
    }
}
