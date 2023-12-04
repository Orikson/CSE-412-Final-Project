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

    }

    public User(int ID, String f_name, String l_name, String email) {
        this.ID = ID;
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
    }
}
