package cloudlab.controllers;

// java
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

// application
import cloudlab.App;
import cloudlab.util.Group;

// javafx
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyGroupsController {
    private Group selectedGroup;

    @FXML
    private TableView<Group> groups;

    @FXML
    private TableColumn<Group, String> groupNameColumn;

    @FXML
    private TableColumn<Group, Integer> groupIDColumn;

    @FXML
    private TableColumn<Group, Integer> memberCountColumn;

    @FXML
    public void initialize() throws SQLException {
        this.groupNameColumn.setCellValueFactory(new PropertyValueFactory<Group, String>("name"));
        this.groupIDColumn.setCellValueFactory(new PropertyValueFactory<Group, Integer>("ID"));
        this.memberCountColumn.setCellValueFactory(new PropertyValueFactory<Group, Integer>("memberCount"));

        ArrayList<Group> groups = new ArrayList<Group>();
        Group.GetAllGroupsByMember(groups, App.cur_UID);

        this.groups.setItems(FXCollections.observableList(groups));
        this.groups.getSelectionModel().selectedItemProperty()
                .addListener((observableList, oldSelection, newSelection) -> {
                    this.selectedGroup = newSelection;
                });
    }

    @FXML
    public void back() throws IOException {
        App.setRoot("home");
    }

    @FXML
    public void leaveGroup() throws SQLException {
        if (this.selectedGroup == null) {
            // Notify that no group is selected
            Alert a = new Alert(AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("No group selected");
            a.setContentText("Please select a group and try again.");
            a.showAndWait();
            return;
        }

        // Send SQL query to remove user from group
        PreparedStatement st = App.conn.prepareStatement("DELETE FROM UserInGroup WHERE user_id=? AND group_id=?");
        st.setInt(1, App.cur_UID);
        st.setInt(2, this.selectedGroup.getID());
        st.executeUpdate();
        st.close();

        // Update table
        this.groups.getItems().remove(this.selectedGroup);
    }
}
