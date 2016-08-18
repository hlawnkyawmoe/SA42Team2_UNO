package control;
import model.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Registration_DAO extends DataAccessDAO{
    
    private Connection conn;
    private Statement statement;
    private ResultSet rs;
    
    private PreparedStatement preparedStatement = null;
    public static String INSERT_USER = "INSERT INTO " + DataAccessDAO.TABLENAME + "(USERNAME, PASSWORD) VALUES (?,?)";
    public static String INSERT_USERGROUP = "INSERT INTO user_groups (group_id) VALUES (?)";
    
    public int createUser(User user) throws SQLException {
        try {
            Connection con = getConnection();
            preparedStatement = con.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
      
        return 0;
    }
    public int createUserGroup() throws SQLException {
        try {
            Connection con;
            con = getConnection();
            preparedStatement = con.prepareStatement(INSERT_USERGROUP);            
            preparedStatement.setInt(1, 1);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
      
        return 0;
    }

}
