package data;

import features.Assignee;
import features.Comment;
import features.Issue;
import features.Label;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

/**
 * Created by @kash on 2/18/2018.
 */
public class Run {
    static void intialize() {
        Scanner sc = new Scanner(System.in);
        String url = "";  //sc.nextLine();
        url = "https://api.github.com/repos/tensorflow/tensorflow/issues";
        Map<String, String> params = new HashMap<String, String>();
        params.put("per_page", "100");
        params.put("access_token","26e3f7340239e28acf3a2a1b79736f6f5d81ee9a");
        try {
            String url1;
            //Creating a jdbc connection
            Class.forName("com.mysql.jdbc.Driver");
            String user_name = "root";
            String password = "SKY15b007";
            Connection db_conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/issues", user_name, password);

            Statement st = db_conn.createStatement();

            //List of issues
            List<Issue> issues = new ArrayList<>();
            //looping through pages
            for(int page = 1; page < 3; page++)    {
                System.out.println("Page No. [" + page + "]");
                params.remove("page");
                params.put("page", String.valueOf(page));
                url1 = ScrapeUtils.addParams(url, params);
                HttpURLConnection conn = ScrapeUtils.getConn(url1);
                JSONArray jarr = ScrapeUtils.getJSONArray(conn);
                for(int i = 0; i < jarr.length() - 80; i++)  {
                    JSONObject obj = jarr.getJSONObject(i);

                    //id of issue
                    long id = obj.getLong("id");

                    //number of isser
                    long number = obj.getLong("number");

                    //title
                    String title = obj.getString("title");
                    title.replaceAll("[']", "!");
                    //body
                    String body = obj.getString("body");
                    body = body.replaceAll("[']","!");

                    //labels
                    JSONArray label_arr = obj.getJSONArray("labels");
                    List<Label> labels = ScrapeUtils.getLabelsList(label_arr, id);
                    params.remove("page");

                    //comments
                    String comments_url = obj.getString("comments_url");
                    comments_url = ScrapeUtils.addParams(comments_url, params);
                    HttpURLConnection comm_conn = ScrapeUtils.getConn(comments_url);
                    JSONArray comm_arr = ScrapeUtils.getJSONArray(comm_conn);
                    List<Comment> comments = ScrapeUtils.getCommentList(comm_arr, id);

                    //assignees
                    JSONArray assignees_arr = obj.getJSONArray("assignees");
                    List<Assignee> assignees = ScrapeUtils.getAssigneeList(assignees_arr, id);

                    //author association
                    String author_assoc = obj.getString("author_association");

                    //date created
                    String created_at = obj.getString("created_at");

                    Issue issue = new Issue(id, number, created_at, title, comments, assignees, labels, author_assoc, body);
                    issues.add(issue);
                    System.out.println("Issue created: [" + issue.getNumber() + "]" + " [" + issue.getId() + "]");
                }
            }

            //Adding to database

            for(Issue issue: issues)    {
                System.out.println("Adding issue: [" + issue.getId() + "]");
                String query = "insert into issue (id, number, title, body, assignees, created_at, author_assoc)" +
                        " values(" + issue.getId() + "," + issue.getNumber() + ",'" +
                        issue.getTitle().toString() + "','" + issue.getBody() + "'," + issue.getAssignees().size() +
                        ",'" + issue.getCreated_at().getTimeInMillis() + "','" + issue.getAuthor_assoc().toString() + "')";
                try{
                    st.executeUpdate(query);
                }
                catch(Exception e){
                    System.out.println("EXCEPTION:" + e.getMessage());
                    System.out.println("Dropping issue: [" + issue.getNumber() + "]");
                }
                finally {
                    continue;
                }
            }
            db_conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }
}
