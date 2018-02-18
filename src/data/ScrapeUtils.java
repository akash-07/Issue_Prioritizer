package data;

import features.Assignee;
import features.Comment;
import features.Label;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
/**
 * Created by @kash on 2/18/2018.
 */
public class ScrapeUtils {
    //returns an HTTP URL connection for the specified url
    static HttpURLConnection getConn(String url) throws IOException{
        URL url1 = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) url1.openConnection() ;
        return conn;
    }

    static String addParams(String url, Map<String, String> params)   {
        Set<String> keys = params.keySet();
        String extensions = "?";
        for(String key: keys)   {
            extensions += key + "=" + params.get(key) + "&";
        }
        return  url + extensions.substring(0, extensions.length() - 1);
    }

    static Map<String, List<String>> getHeaders(HttpURLConnection conn)    {
        return conn.getHeaderFields();
    }

    static JSONObject getJSONObj(HttpURLConnection conn) throws IOException, JSONException{
        InputStream in = conn.getInputStream();
        String encoding = conn.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        Scanner sc = new Scanner(in, encoding).useDelimiter("\\A");
        String body = sc.hasNext()? sc.next() : "";
        JSONObject obj = new JSONObject(body);
        return obj;
    }


    static JSONArray getJSONArray(HttpURLConnection conn) throws IOException, JSONException{
        InputStream in = conn.getInputStream();
        String encoding = conn.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        Scanner sc = new Scanner(in, encoding).useDelimiter("\\A");
        String body = sc.hasNext()? sc.next() : "";
        JSONArray arr = new JSONArray(body);
        return arr;
    }

    static List<Label> getLabelsList(JSONArray label_arr, long issue_id)  throws JSONException  {
        List<Label> labels = new ArrayList<>();
        for(int i = 0; i < label_arr.length(); i++) {
            JSONObject obj = label_arr.getJSONObject(i);
            long id = obj.getLong("id");
            String name = obj.getString("name");
            name = name.replaceAll("[']","!");
            labels.add(new Label(name, id, issue_id));
        }

        return labels;
    }

    static List<Comment> getCommentList(JSONArray comment_arr, long issue_id)  throws JSONException {
        List<Comment> comments = new ArrayList<>();
        for(int i = 0; i < comment_arr.length(); i++) {
            JSONObject obj = comment_arr.getJSONObject(i);
            long id = obj.getLong("id");
            String author_assoc = obj.getString("author_association");
            String body = obj.getString("body");
            body = body.replaceAll("[']", "!");
            String created_at = obj.getString("created_at");
            comments.add(new Comment(created_at, author_assoc,issue_id, id, body));
        }

        return comments;
    }

    static List<Assignee> getAssigneeList(JSONArray assignee_arr, long issue_id)  throws JSONException {
        List<Assignee> assignees = new ArrayList<>();
        for(int i = 0; i < assignee_arr.length(); i++) {
            assignees.add(new Assignee());
        }

        return assignees;
    }
}
