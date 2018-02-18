package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by @kash on 2/18/2018.
 */
public class TryJDBC {
    public static void main(String args[]) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String user_name = "root";
            String password = "SKY15b007";
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tryissue", user_name, password);

            Statement st = conn.createStatement();
            String query = "insert into Issue (id, number, title, body, assignees, created_at, author_assoc)" +
                    " values (1, 100, 'first issue', 'posted by akash', 4, '2015-11-11Z11:00:00T', 'NONE')";
            st.executeUpdate(query);
        }
        catch(Exception e)  {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
