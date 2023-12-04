package cloudlab.util;

// java
import java.util.ArrayList;
import java.sql.*;

// application
import cloudlab.App;

public class User {
    private int ID;
    private String f_name;
    private String l_name;
    private String email;

    public static void GetAllUsers(ArrayList<User> users) throws SQLException {
        users.clear();

        // Send SQL query to get all group information
        PreparedStatement st = App.conn
                .prepareStatement(
                        "SELECT user_id, first_name, last_name, email FROM Users");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            int ID = rs.getInt(1);
            String f_name = rs.getString(2);
            String l_name = rs.getString(3);
            String email = rs.getString(4);

            users.add(new User(ID, f_name, l_name, email));
        }

        rs.close();
        st.close();
    }

    public User(int ID, String f_name, String l_name, String email) {
        this.ID = ID;
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
    }

    public int getID() {
        return this.ID;
    }

    public String getFirstName() {
        return this.f_name;
    }

    public String getLastName() {
        return this.l_name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFullName() {
        return this.f_name + " " + this.l_name;
    }
}
