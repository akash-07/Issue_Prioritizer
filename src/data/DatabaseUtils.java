package data;

import features.Assignee;
import features.Comment;
import features.Issue;
import features.Label;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by @kash on 2/19/2018.
 */
public class DatabaseUtils {
    public static Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String user_name = "root";
        String password = "SKY15b007";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/issues", user_name, password);
        conn.clearWarnings();
        return conn;
    }

    public static void dropIssues(Connection conn, List<Long> issue_ids) throws SQLException {
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

    public static void addIssues(Connection conn, List<Issue> issues) throws SQLException {
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

    public static List<Issue> getIssues(Connection conn) throws SQLException{
        List<Issue> issues = new ArrayList<>();
        Statement st = conn.createStatement();
        String query = "select * from issue";
        ResultSet res = st.executeQuery(query);
        while(res.next())   {
            String title = res.getString("title");
            String body = res.getString("body");
            Long number = res.getLong("number");
            Long id = res.getLong("id");

            String created_at = res.getString("created_at");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            created_at = format.format(new Date(Long.valueOf(created_at)));

            String author_assoc = res.getString("author_assoc");
            int num_assignees = res.getInt("assignees");
            List<Assignee> assignees = new ArrayList<>();
            for(int i = 0; i < num_assignees; i++)
                assignees.add(new Assignee());

            Statement st1 = conn.createStatement();
            query = "select * from comment where issue_id = " + id;
            ResultSet res1 = st1.executeQuery(query);
            List<Comment> comments = new ArrayList<>();
            while(res1.next())   {
                Long comment_id = res1.getLong("id");
                String author_assoc_comment = res1.getString("author_assoc");
                String created_at_comment = res1.getString("created_at");
                format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                created_at_comment = format.format(new Date(Long.valueOf(created_at_comment)));
                String body_comment = res1.getString("body");
                Comment comment = new Comment(created_at_comment, author_assoc_comment, id, comment_id, body_comment);
                comments.add(comment);
            }

            query = "select * from labels where issue_id = " + id;
            ResultSet res2 = st1.executeQuery(query);
            List<Label> labels = new ArrayList<>();
            while(res2.next())  {
                String name = res2.getString("name");
                Long id_label = res2.getLong("id");
                Label label = new Label(name, id_label, id);
                labels.add(label);
            }
            Issue issue = new Issue(id, number, created_at, title, comments, assignees,labels,author_assoc,body);
            issues.add(issue);
        }
        return issues;
    }
}
