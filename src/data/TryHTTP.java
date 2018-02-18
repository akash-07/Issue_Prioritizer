package data;

import org.json.JSONObject;
import java.io.InputStream;
import java.net.*;
import java.util.*;

/**
 * Created by @kash on 2/18/2018.
 */
public class TryHTTP {
    public static void main(String[] args)  {
        try{
            String url1 = "https://api.github.com/repos/tensorflow/tensorflow/issues?page=100";
            URL url = new URL("https://api.github.com/repos/tensorflow/tensorflow/issues");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println("Response code: " + conn.getResponseCode());
            System.out.println("Response message: " + conn.getResponseMessage());

            Map<String, List<String>> hdrMap = conn.getHeaderFields();
            Set<String> hdrFields = hdrMap.keySet();
            System.out.println("Link: " + conn.getHeaderField("Link"));
            for(String k: hdrFields)    {
                System.out.println(k + ": " + hdrMap.get(k));
            }




            InputStream in = conn.getInputStream();
            int c,len = conn.getContentLength();
            if(len == -1)
                System.out.println("Content length unavailable");
            else if(len != 0)   {
                String encoding = conn.getContentEncoding();
                System.out.println("ENCODING: " + encoding);
                encoding = encoding == null ? "UTF-8" : encoding;
                Scanner sc = new Scanner(in, encoding).useDelimiter("\\A");
                String body = sc.hasNext()? sc.next() : "";
                System.out.println(body);
                JSONObject obj = new JSONObject(body);
                long id = obj.getLong("id");
                String name = obj.getString("name");
                JSONObject owner = obj.getJSONObject("owner");
                String login = owner.getString("login");
                System.out.println("===" + login + "===");
                System.out.println("id: " + id);
                System.out.println("name: " + name);
            }
            else
                System.out.println("No content available");

            Map<String, String> map = new HashMap<String, String>();
            map.put("per_page", "100");
            System.out.println("New URL " + ScrapeUtils.addParams(url1, map));

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
