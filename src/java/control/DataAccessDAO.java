package control;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataAccessDAO {
    
    public static String DRIVER = "com.mysql.jdbc.Driver";
    public static String USER = "root";
    public static String PASSWORD = "hlawnkyawmoe87";
    public static String CONNECTION = "jdbc:mysql://localhost:3306/jdbcrealm";
    public static String TABLENAME = "users";
    
    protected Connection connect = null;

    public DataAccessDAO() {
    }

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            connect = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return connect;
    }
    
}
