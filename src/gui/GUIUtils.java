package gui;

import features.Issue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by @kash on 2/21/2018.
 */
public class GUIUtils {
    static List<String> getIssuesForDisplay(List<Issue> issues)   {
        List<String> issue_titles = new ArrayList<>();
        int N = 0;
        for(Issue issue: issues)    {
            N += 1;
            String title = issue.getTitle().toString();
            int num_comments = issue.getComments().size();
            title = N + ". " + title + "...\t\t" + num_comments;
            issue_titles.add(title);
        }
        return issue_titles;
    }

    static String[][] getIssuesForJTable(List<Issue> issues) {
        int N = issues.size();
        String[][] data = new String[N][3];
        for(int i = 0; i < N; i++)  {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = issues.get(i).getTitle().toString();
            data[i][2] = String.valueOf(issues.get(i).getComments().size());
        }
        return data;
    }

    static String[] getHeadersForJTable() {
        String headers[] = {"No." ,"Issue Title", "# Comments"};
        return headers;
    }
}
