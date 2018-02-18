package data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by @kash on 2/18/2018.
 */
public class Scrape {
    public static void main(String[] args)  {
        Scanner sc = new Scanner(System.in);
        String url = "";  //sc.nextLine();
        url = "https://api.github.com/repos/tensorflow/tensorflow/issues";
        Map<String, String> params = new HashMap<String, String>();
        params.put("per_page", "100");
        params.put("access_token","26e3f7340239e28acf3a2a1b79736f6f5d81ee9a");
        try {
            String url1;
            for(int page = 1; page < 11; page++)    {
                System.out.println("[" + page + "]");
                params.remove("page");
                params.put("page", String.valueOf(page));
                url1 = ScrapeUtils.addParams(url, params);
                HttpURLConnection conn = ScrapeUtils.getConn(url1);
                JSONArray jarr = ScrapeUtils.getJSONArray(conn);
                for(int i = 0; i < jarr.length(); i++)  {
                    JSONObject obj = jarr.getJSONObject(i);
                    long id = obj.getLong("number");
                    System.out.println(id);
                }
            }
        }
        catch (Exception e) {
            System.out.println("EXCEPTION: " + e.getMessage());
        }
    }
}
