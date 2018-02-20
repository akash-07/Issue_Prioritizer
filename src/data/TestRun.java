package data;

import features.Issue;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/**
 * Created by @kash on 2/18/2018.
 */
public class TestRun {
    public static void main(String[] args) {
        /*
        String url = "https://api.github.com/repos/akash-07/Issue_Prioritizer/issues";
        url = "https://api.github.com/repos/tensorflow/tensorflow/issues";
        RunUtils.intialize(url);
        Scanner sc = new Scanner(System.in);
        System.out.println("Waiting for user input...");
        sc.nextLine();
        RunUtils.refresh(url, "past10min");
        */
        try {
            Connection db_conn = DatabaseUtils.getDatabaseConnection();
            List<Issue> issueList = DatabaseUtils.getIssues(db_conn);
            for(Issue issue: issueList)
                System.out.println("[" + issue.getNumber() + "]");
        }
        catch(Exception e)  {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.github.com/repos/tensorflow/tensorflow/issues";
                new gui.gui();
            }
        });
    }
}
