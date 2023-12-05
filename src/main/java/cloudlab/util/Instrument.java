package cloudlab.util;

import java.sql.*;
import java.util.ArrayList;

import cloudlab.App;

public class Instrument {
    private String name;
    private int ID;

    public static void GetAllInstruments(ArrayList<Instrument> instruments) throws SQLException {
        instruments.clear();

        // Send SQL query to get all group information
        PreparedStatement st = App.conn
                .prepareStatement(
                        "SELECT instrument_id, instrument_name FROM Instrument");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            int ID = rs.getInt(1);
            String name = rs.getString(2);

            instruments.add(new Instrument(name, ID));
        }

        rs.close();
        st.close();
    }

    public Instrument(String name, int ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.ID;
    }
}
