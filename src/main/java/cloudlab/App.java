package cloudlab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class App extends Application {
    private static Scene scene;

    public static Connection conn;
    public static int cur_UID;

    @Override
    public void start(Stage stage) throws IOException {
        App.cur_UID = -1;
        scene = new Scene(loadFXML("login_page"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws java.sql.SQLException {
        /*
         * Connects to the "cloudlab" database on localhost:5432 (default Postgres port)
         * Loads password from environment variable CLOUDLAB_PASS
         */
        String url = "jdbc:postgresql://localhost/cloudlab";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", System.getenv("CLOUDLAB_PASS"));
        props.setProperty("ssl", "false");
        App.conn = DriverManager.getConnection(url, props);

        // Launch app
        launch();
    }
}
