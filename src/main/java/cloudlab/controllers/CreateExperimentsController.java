package cloudlab.controllers;

// java
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

// application
import cloudlab.App;
import cloudlab.util.Instrument;
import cloudlab.util.Job;

// javafx
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CreateExperimentsController {
    private Instrument selectedInstrument;
    private Job selectedJob;

    @FXML
    private TextField experimentName;

    @FXML
    private TableView<Instrument> instrumentTable;

    @FXML
    private TableColumn<Instrument, String> instrumentNameColumn;

    @FXML
    private TableColumn<Instrument, Integer> instrumentIDColumn;

    @FXML
    private TableView<Job> jobTable;

    @FXML
    private TableColumn<Job, String> jobInstrumentColumn;

    @FXML
    public void initialize() throws SQLException {
        this.instrumentNameColumn.setCellValueFactory(new PropertyValueFactory<Instrument, String>("name"));
        this.instrumentIDColumn.setCellValueFactory(new PropertyValueFactory<Instrument, Integer>("ID"));

        this.jobInstrumentColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("instrumentName"));

        ArrayList<Instrument> instruments = new ArrayList<Instrument>();
        Instrument.GetAllInstruments(instruments);

        this.instrumentTable.setItems(FXCollections.observableList(instruments));
        this.instrumentTable.getSelectionModel().selectedItemProperty()
                .addListener((observableList, oldSelection, newSelection) -> {
                    this.selectedInstrument = newSelection;
                });

        this.jobTable.setItems(FXCollections.observableList(new ArrayList<Job>()));
        this.jobTable.getSelectionModel().selectedItemProperty()
                .addListener((observableList, oldSelection, newSelection) -> {
                    this.selectedJob = newSelection;
                });
    }

    @FXML
    public void createJob() {
        this.jobTable.getItems().add(new Job(this.selectedInstrument));
    }

    @FXML
    public void deleteJob() {
        this.jobTable.getItems().remove(this.selectedJob);
    }

    @FXML
    public void queueExperiment() throws IOException, SQLException {
        if (this.experimentName.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Experiment name cannot be empty");
            alert.showAndWait();
            return;
        }

        if (this.jobTable.getItems().size() == 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Experiment must have at least one job");
            alert.showAndWait();
            return;
        }

        // Create each job
        ArrayList<Integer> jobIDs = new ArrayList<Integer>();
        for (Job job : this.jobTable.getItems()) {
            jobIDs.add(job.create());
        }

        // Create experiment
        PreparedStatement st = App.conn
                .prepareStatement("INSERT INTO Experiment (experiment_name) VALUES (?) RETURNING experiment_id");
        st.setString(1, this.experimentName.getText());
        ResultSet rs = st.executeQuery();

        rs.next();
        int experimentID = rs.getInt(1);

        // Queue each job
        for (Job job : this.jobTable.getItems()) {
            job.queue(experimentID);
        }

        // Queue experiment
        st = App.conn.prepareStatement("INSERT INTO UserQueuesExperiment (user_id, experiment_id) VALUES (?, ?)");
        st.setInt(1, App.cur_UID);
        st.setInt(2, experimentID);
        st.executeUpdate();
        rs.close();
        st.close();

        // Alert user that experiment was created
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Experiment created");
        alert.showAndWait();

        // Create dummy experiment log data
        for (Job job : this.jobTable.getItems()) {
            st = App.conn.prepareStatement(
                    "INSERT INTO ExperimentLog (timestamp, instrument_id, job_id, experiment_data, symbolic_link) VALUES (?, ?, ?, ?, ?)");
            st.setLong(1, System.currentTimeMillis() / 1000L);
            st.setInt(2, job.getInstrumentID());
            st.setInt(3, job.getID());
            st.setString(4, "Demo experiment log for job " + job.getID());
            st.setString(5, "https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            st.executeUpdate();
            st.close();
        }

        App.setRoot("home");
    }

    @FXML
    public void back() throws IOException {
        App.setRoot("home");
    }
}
