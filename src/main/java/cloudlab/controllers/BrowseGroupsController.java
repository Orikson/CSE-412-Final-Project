package cloudlab.controllers;

// java
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

// application
import cloudlab.App;
import cloudlab.util.Group;
import cloudlab.util.User;
import javafx.collections.FXCollections;
// javafx
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class BrowseGroupsController {
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
    private TableView<User> users;

    @FXML
    private TableColumn<User, String> fnameColumn;

    @FXML
    private TableColumn<User, String> lnameColumn;

    @FXML
    public void initialize() throws SQLException {
        this.groupNameColumn.setCellValueFactory(new PropertyValueFactory<Group, String>("name"));
        this.groupIDColumn.setCellValueFactory(new PropertyValueFactory<Group, Integer>("ID"));
        this.memberCountColumn.setCellValueFactory(new PropertyValueFactory<Group, Integer>("memberCount"));

        this.fnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        this.lnameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));

        ArrayList<Group> groups = new ArrayList<Group>();
        Group.GetAllGroups(groups);

        this.groups.setItems(FXCollections.observableList(groups));
        this.groups.getSelectionModel().selectedItemProperty()
                .addListener((observableList, oldSelection, newSelection) -> {
                    this.selectedGroup = newSelection;

                    if (this.selectedGroup == null) {
                        return;
                    }

                    try {
                        ArrayList<User> users = this.selectedGroup.getMembers();
                        this.users.setItems(FXCollections.observableList(users));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
    }

    @FXML
    public void back() throws IOException {
        App.setRoot("home");
    }

    @FXML
    public void joinGroup() throws SQLException {
        if (this.selectedGroup == null) {
            return;
        }

        PreparedStatement st = App.conn.prepareStatement("INSERT INTO UserInGroup (user_id, group_id) VALUES (?, ?)");
        st.setInt(1, App.cur_UID);
        st.setInt(2, this.selectedGroup.getID());
        st.executeUpdate();
        st.close();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Successfully joined group");
        alert.setContentText("You have successfully joined the group " + this.selectedGroup.getName());
        alert.showAndWait();

        this.selectedGroup.updateMembers();
        this.selectedGroup.incrementMemberCount();
        ArrayList<User> users = this.selectedGroup.getMembers();
        this.users.setItems(FXCollections.observableList(users));
        this.groups.refresh();
        this.selectedGroup = null;
    }

    @FXML
    public void createGroup() throws IOException {
        App.setRoot("create_group");
    }
}
