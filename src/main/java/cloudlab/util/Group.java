package cloudlab.util;

// java
import java.sql.*;
import java.util.ArrayList;

// application
import cloudlab.util.User;
import cloudlab.App;

/**
 * Utility class for TableView objects
 * Stores:
 * ID
 * Name
 * Number of members
 */
public class Group {
    private int ID;
    private String name;
    private int memberCount;

    private ArrayList<User> members;
    private boolean genMembers;

    /**
     * Gets a list of all group objects
     * 
     * @throws SQLException
     */
    public static void GetAllGroups(ArrayList<Group> groups) throws SQLException {
        groups.clear();

        // Send SQL query to get all group information
        PreparedStatement st = App.conn
                .prepareStatement(
                        "SELECT Groupss.group_id, Groupss.group_name, member_count FROM Groupss, (SELECT group_id, COUNT(DISTINCT user_id) AS member_count FROM UserInGroup GROUP BY group_id) AS members WHERE Groupss.group_id=Members.group_id");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            int ID = rs.getInt(1);
            String name = rs.getString(2);
            int memberCount = rs.getInt(3);

            groups.add(new Group(ID, name, memberCount));
        }

        rs.close();
        st.close();
    }

    public static void GetAllGroupsByMember(ArrayList<Group> groups, int userID) throws SQLException {
        groups.clear();

        // Send SQL query to get all group information associated with a user
        PreparedStatement st = App.conn
                .prepareStatement(
                        "SELECT Groupss.group_id, Groupss.group_name, member_count FROM Groupss, UserInGroup, (SELECT group_id, COUNT(DISTINCT user_id) AS member_count FROM UserInGroup GROUP BY group_id) AS Members WHERE Groupss.group_id=Members.group_id AND Groupss.group_id=UserInGroup.group_id AND UserInGroup.user_id=?");

        st.setInt(1, userID);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            int ID = rs.getInt(1);
            String name = rs.getString(2);
            int memberCount = rs.getInt(3);

            groups.add(new Group(ID, name, memberCount));
        }

        rs.close();
        st.close();
    }

    public Group(int ID, String name, int memberCount) {
        this.ID = ID;
        this.name = name;
        this.memberCount = memberCount;
        this.members = new ArrayList<User>();
        this.genMembers = false;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public int getMemberCount() {
        return this.memberCount;
    }

    public void incrementMemberCount() {
        this.memberCount++;
    }

    public void updateMembers() {
        this.genMembers = false;
    }

    public ArrayList<User> getMembers() throws SQLException {
        if (!this.genMembers) {
            this.genMembers = true;
            this.members.clear();

            // Send SQL query to get all member information
            PreparedStatement st = App.conn
                    .prepareStatement(
                            "SELECT Users.user_id, Users.first_name, Users.last_name, Users.email FROM Users, UserInGroup WHERE UserInGroup.user_id=Users.user_id AND UserInGroup.group_id=?");
            st.setInt(1, ID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                int ID = rs.getInt(1);
                String f_name = rs.getString(2);
                String l_name = rs.getString(3);
                String email = rs.getString(4);

                this.members.add(new User(ID, f_name, l_name, email));
            }

            rs.close();
            st.close();
        }
        return this.members;
    }
}
