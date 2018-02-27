package analyzer;

import data.DatabaseUtils;
import features.Issue;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by @kash on 2/27/2018.
 */
public class TestPriority {
    public static void main(String[] args)  {
        try{
            Connection conn = DatabaseUtils.getDatabaseConnection();
            List<Issue> issues = DatabaseUtils.getIssues(conn);
            for(Issue issue: issues)
                issue.getWeight();
            List<Issue> sorted_issues = PriorityUtils.getSortedIssues(issues);
            for(Issue issue: sorted_issues)
                System.out.println("[" + issue.getNumber() + "]");
        }
        catch(Exception e)  {
            System.out.println(e.getMessage());
        }
     }
}
