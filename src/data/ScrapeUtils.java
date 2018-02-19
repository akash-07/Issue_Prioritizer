package data;

import features.Assignee;
import features.Comment;
import features.Issue;
import features.Label;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 * Created by @kash on 2/18/2018.
 */
public class ScrapeUtils {
    //returns an HTTP URL connection for the specified url
    static HttpURLConnection getConn(String url) throws IOException {
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
        return conn;
    }

    static String addParams(String url, Map<String, String> params) {
        Set<String> keys = params.keySet();
        String extensions = "?";
        for (String key : keys) {
            extensions += key + "=" + params.get(key) + "&";
        }
        return url + extensions.substring(0, extensions.length() - 1);
    }

    static Map<String, List<String>> getHeaders(HttpURLConnection conn) {
        return conn.getHeaderFields();
    }

    static JSONObject getJSONObj(HttpURLConnection conn) throws IOException, JSONException {
        InputStream in = conn.getInputStream();
        String encoding = conn.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        Scanner sc = new Scanner(in, encoding).useDelimiter("\\A");
        String body = sc.hasNext() ? sc.next() : "";
        JSONObject obj = new JSONObject(body);
        return obj;
    }


    static JSONArray getJSONArray(HttpURLConnection conn) throws IOException, JSONException {
        InputStream in = conn.getInputStream();
        String encoding = conn.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        Scanner sc = new Scanner(in, encoding).useDelimiter("\\A");
        String body = sc.hasNext() ? sc.next() : "";
        JSONArray arr = new JSONArray(body);
        return arr;
    }

    static List<Label> getLabelsList(JSONArray label_arr, long issue_id) throws JSONException {
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < label_arr.length(); i++) {
            JSONObject obj = label_arr.getJSONObject(i);
            long id = obj.getLong("id");
            String name = obj.getString("name");
            name = name.replaceAll("[']", "!");
            labels.add(new Label(name, id, issue_id));
        }

        return labels;
    }

    static List<Comment> getCommentList(JSONArray comment_arr, long issue_id) throws JSONException {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < comment_arr.length(); i++) {
            JSONObject obj = comment_arr.getJSONObject(i);
            long id = obj.getLong("id");
            String author_assoc = obj.getString("author_association");
            String body = obj.getString("body");
            body = body.replaceAll("[']", "!");
            String created_at = obj.getString("created_at");
            comments.add(new Comment(created_at, author_assoc, issue_id, id, body));
        }

        return comments;
    }

    static List<Assignee> getAssigneeList(JSONArray assignee_arr, long issue_id) throws JSONException {
        List<Assignee> assignees = new ArrayList<>();
        for (int i = 0; i < assignee_arr.length(); i++) {
            assignees.add(new Assignee());
        }

        return assignees;
    }

    private static List<Issue> regetIssues(String url, String open_status, String since) {
        //List of issues
        List<Issue> issues = new ArrayList<>();
        //url = "https://api.github.com/repos/tensorflow/tensorflow/issues";
        Map<String, String> params = new HashMap<String, String>();
        params.put("per_page", "100");
        params.put("access_token", "26e3f7340239e28acf3a2a1b79736f6f5d81ee9a");
        if(open_status.equals("closed"))
            params.put("state", "closed");
        else
            params.put("state", "open");
        try {
            String url1;
            //Creating a jdbc connection
            Connection db_conn = DatabaseUtils.getDatabaseConnection();
            Statement st = db_conn.createStatement();

            //Get max pages by a function call
            int max_pages = 2;

            //looping through pages
            for (int page = 1; page < max_pages; page++) {
                Calendar date = Calendar.getInstance();
                long numMillisInADay = TimeUtils.getMillis(since);
                //Gettting date and time of previous day
                date.setTimeInMillis(date.getTimeInMillis() - numMillisInADay);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                since = format.format(date.getTime());
                System.out.println("Since: " + since);
                System.out.println("Page No. [" + page + "]");
                params.remove("page");
                params.put("page", String.valueOf(page));
                params.put("since", since);

                url1 = ScrapeUtils.addParams(url, params);
                HttpURLConnection conn = ScrapeUtils.getConn(url1);
                JSONArray jarr = ScrapeUtils.getJSONArray(conn);

                for (int i = 0; i < jarr.length(); i++) {
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
                    body = body.replaceAll("[']", "!");

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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return issues;
    }

    static List<Issue> regetOpenIssues(String url, String since)  {
        return regetIssues(url, "open", since);
    }

    static List<Issue> regetClosedIssues(String url, String since) {
        return regetIssues(url, "closed", since);
    }
}
