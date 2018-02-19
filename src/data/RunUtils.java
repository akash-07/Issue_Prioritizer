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
public class RunUtils {
    static void intialize(String url) {
        System.out.println("---------------Initializing----------------");
        //url = "https://api.github.com/repos/tensorflow/tensorflow/issues";
        //url = "https://api.github.com/repos/akash-07/Issue_Prioritizer/issues";
        Map<String, String> params = new HashMap<String, String>();
        params.put("per_page", "100");
        params.put("access_token","26e3f7340239e28acf3a2a1b79736f6f5d81ee9a");
        try {
            String url1;
            //Creating a jdbc connection
            Connection db_conn = DatabaseUtils.getDatabaseConnection();
            Statement st = db_conn.createStatement();

            //List of issues
            List<Issue> issues = new ArrayList<>();

            //get max pages via a function
            int max_pages = 3;

            //looping through pages
            for(int page = 1; page < max_pages; page++)    {
                System.out.println("Page No. [" + page + "]");
                params.remove("page");
                params.put("page", String.valueOf(page));
                url1 = ScrapeUtils.addParams(url, params);
                HttpURLConnection conn = ScrapeUtils.getConn(url1);
                System.out.println(url1);
                JSONArray jarr = ScrapeUtils.getJSONArray(conn);
                for(int i = 0; i < jarr.length(); i++)  {
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

            DatabaseUtils.addIssues(db_conn, issues);
            db_conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }

    static void refresh(String url, String since)   {
        System.out.println("-------------Refresing-------------");
        List<Issue> updated_open_issues = ScrapeUtils.regetOpenIssues(url, since);
        List<Issue> updated_closed_issues = ScrapeUtils.regetClosedIssues(url, since);
        try{
            Connection db_conn = DatabaseUtils.getDatabaseConnection();
            List<Long> issue_ids = new ArrayList<>();
            for(Issue issue: updated_closed_issues) {
                issue_ids.add(issue.getId());
            }
            DatabaseUtils.dropIssues(db_conn, issue_ids);
            issue_ids = new ArrayList<>();
            for(Issue issue: updated_open_issues) {
                issue_ids.add(issue.getId());
            }
            DatabaseUtils.dropIssues(db_conn, issue_ids);
            DatabaseUtils.addIssues(db_conn, updated_open_issues);
        }
        catch (Exception e) {
            System.out.print("METHOD refresh(): ");
            System.out.println(e.getMessage());
        }
    }
}
