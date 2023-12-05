package cloudlab.controllers;

// java
import java.io.IOException;
import java.sql.*;

// application
import cloudlab.App;

// javafx
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CreateGroupController {
    @FXML
    private TextField groupName;

    @FXML
    public void create() throws IOException, SQLException {
        String name = this.groupName.getText();
        if (name.length() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid group name");
            alert.setContentText("Group name cannot be empty");
            alert.showAndWait();
            return;
        }

        // Create group
        PreparedStatement st = App.conn
                .prepareStatement("INSERT INTO Groupss (group_name) VALUES (?) RETURNING group_id");
        st.setString(1, name);
        ResultSet rs = st.executeQuery();
        rs.next();
        int groupID = rs.getInt(1);
        rs.close();
        st.close();

        // Add user to group
        st = App.conn.prepareStatement("INSERT INTO UserInGroup (user_id, group_id) VALUES (?, ?)");
        st.setInt(1, App.cur_UID);
        st.setInt(2, groupID);
        st.executeUpdate();
        st.close();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Successfully created group");
        alert.setContentText("You have successfully created and joined the group " + name);
        alert.showAndWait();

        App.setRoot("browse_groups");
    }

    @FXML
    public void back() throws IOException {
        App.setRoot("browse_groups");
    }
}
