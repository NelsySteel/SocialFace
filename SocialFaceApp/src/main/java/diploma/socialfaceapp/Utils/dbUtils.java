package diploma.socialfaceapp.Utils;

import net.ucanaccess.jdbc.UcanaccessDriver;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dbUtils {
    private static final String THEMES_BY_TOKEN_QUERY="";
    private static Connection con;
    
    
    public static void connect() throws SQLException, ClassNotFoundException{
        
        Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        String path = "/src/main/resources/diploma/socialfaceapp/Utils/";
        String file = "AnalysDB.accdb";
        String driver = net.ucanaccess.jdbc.UcanaccessDriver.URL_PREFIX;
        String url=driver+s+path+file;
        System.out.println(url);
        try {
            
            dbUtils.con = DriverManager.getConnection(url);

        } catch ( SQLException e ) {
              System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Connection to db was successful!");
    }
    public static void AddTestResults(List<Integer> results) throws SQLException
        {
            String query = "INSERT INTO test_results VALUES";
            for (Integer value: results){
                query += value.toString();
                query += ", ";
            }
            UpdateBD(query);
        }
        
    public static List<Double> getLikesByUser(Integer id){
        List result = new ArrayList();
        String query = "SELECT value FROM likes WHERE user_id = "+id.toString();
        try {
            ResultSet likes = QueryToDB(query);
            while(likes.next()){
                result.add(likes.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(dbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    public static Double getTraitByUser(String trait, Integer id){
        String query = "SELECT " + trait
                + " FROM test_results WHERE user_id = "+id.toString();
        try {
            ResultSet rs = QueryToDB(query);
            rs.next();
            return rs.getDouble(1);
        } catch (SQLException ex) {
            Logger.getLogger(dbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }
    public static void AddLikes(List<Integer> likes, Integer id) throws SQLException
    {
        for (Integer i = 0; i < likes.size(); i++){
            Integer value = likes.get(i);
            String query = "INSERT INTO likes VALUES (";
            query += id.toString();
            query += ", ";
            Integer temp = i+1;
            query += (temp).toString();
            query += ", ";
            query += value.toString();
            query += ")";
            UpdateBD(query);
        }

    }

    public static void CreateUser(String name, Integer id){
        String query = "INSERT INTO users (ID, user_name)"
                + "VALUES ("+id.toString() + ", \""+name
                +"\")";

    try {
        UpdateBD(query);
    } catch (SQLException ex) {
        Logger.getLogger(dbUtils.class.getName()).log(Level.SEVERE, null, ex);
    }
    }

    public static Integer GetUserId(){
        try {
            String query = "SELECT count(*) FROM users AS id";
            ResultSet rs = QueryToDB(query);
            rs.next();
            return rs.getInt(1)+1;
        } catch (SQLException ex) {
            Logger.getLogger(dbUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private static ResultSet QueryToDB(String query) throws SQLException{
        Statement stmt = dbUtils.con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;
    }

    private static void UpdateBD(String statement) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate(statement);
    }
}
