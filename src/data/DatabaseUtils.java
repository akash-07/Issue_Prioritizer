package data;

import features.Comment;
import features.Issue;
import features.Label;

import java.sql.*;
import java.util.List;

/**
 * Created by @kash on 2/19/2018.
 */
public class DatabaseUtils {
    static Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String user_name = "root";
        String password = "SKY15b007";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/issues", user_name, password);
        conn.clearWarnings();
        return conn;
    }

    static void dropIssues(Connection conn, List<Long> issue_ids) throws SQLException {
        String query;
        Statement st = conn.createStatement();
        System.out.println("== Running drop issues ==");
        for(Long id: issue_ids) {
            System.out.println("Dropping issue [" + id + "]");
            query = "delete from comment where issue_id = " + id;
            st.executeUpdate(query);
            query = "delete from labels where issue_id = " + id;
            st.executeUpdate(query);
            query = "delete from issue where id = " + id;
            st.executeUpdate(query);
        }
    }

    static void addIssues(Connection conn, List<Issue> issues) throws SQLException {
        Statement st = conn.createStatement();
        System.out.println("== Running add issues ==");
        for(Issue issue: issues)    {
            System.out.println("Adding issue: [" + issue.getNumber() + "]");
            try{
                String query = "insert into issue (id, number, title, body, assignees, created_at, author_assoc)" +
                    " values(" + issue.getId() + "," + issue.getNumber() + ",'" +
                    issue.getTitle().toString() + "','" + issue.getBody() + "'," + issue.getAssignees().size() +
                    ",'" + issue.getCreated_at().getTimeInMillis() + "','" + issue.getAuthor_assoc().toString() + "')";

                st.executeUpdate(query);

                for(Comment comment: issue.getComments())   {
                    System.out.println("Adding comment: [" + comment.getId() + "]");
                    query = "insert into comment (id, issue_id, author_assoc, created_at, body)" +
                            " values(" + comment.getId() + "," + comment.getIssue_id() + ",'" +
                            comment.getAuthor().toString() + "','" + comment.getCreated_at().getTimeInMillis() +
                            "','" + comment.getBody() + "')";
                    try{
                        st.executeUpdate(query);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("Dropping comment: [" + comment.getId() + "] [" + comment.getIssue_id() + "]");
                    }
                    finally {
                        continue;
                    }
                }

                for(Label lable: issue.getLabels()) {
                    System.out.println("Adding label: [" + issue.getId() + "]");
                    query = "insert into labels (id, issue_id, name) values(" +
                            lable.getId() + "," + lable.getIssue_id() + ",'" +
                            lable.getName() + "')";
                    try{
                        st.executeUpdate(query);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println("Dropping label: [" + lable.getId() + "] [" + lable.getIssue_id() + "]");
                    }
                    finally {
                        continue;
                    }
                }

            }
            catch(Exception e){
                //e.printStackTrace();
                System.out.println("EXCEPTION:" + e.getMessage());
                System.out.println("Dropping issue: [" + issue.getNumber() + "]");
            }
            finally {
                continue;
            }
        }
    }
}
