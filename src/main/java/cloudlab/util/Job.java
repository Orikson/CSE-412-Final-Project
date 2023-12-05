package cloudlab.util;

import cloudlab.util.Instrument;
import cloudlab.App;

import java.sql.*;

public class Job {
    private Instrument instrument;
    private int jobID;

    public Job(Instrument instrument) {
        this.instrument = instrument;
        this.jobID = -1;
    }

    public int create() throws SQLException {
        PreparedStatement st = App.conn
                .prepareStatement("INSERT INTO Job (is_completed, private) VALUES (FALSE, FALSE) RETURNING job_id");

        ResultSet rs = st.executeQuery();
        rs.next();

        this.jobID = rs.getInt(1);

        // Create job-instrument relationship
        st = App.conn.prepareStatement("INSERT INTO InstrumentInJob (instrument_id, job_id) VALUES (?, ?)");
        st.setInt(1, this.instrument.getID());
        st.setInt(2, this.jobID);
        st.executeUpdate();

        rs.close();
        st.close();

        return this.jobID;
    }

    public void queue(int experimentID) throws SQLException {
        PreparedStatement st = App.conn
                .prepareStatement("INSERT INTO JobInExperiment (job_id, experiment_id) VALUES (?, ?)");
        st.setInt(1, this.jobID);
        st.setInt(2, experimentID);

        st.executeUpdate();
        st.close();
    }

    public String getInstrumentName() {
        return this.instrument.getName();
    }

    public int getInstrumentID() {
        return this.instrument.getID();
    }

    public int getID() {
        return this.jobID;
    }
}
