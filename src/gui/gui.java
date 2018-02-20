package gui;

import data.DatabaseUtils;
import features.Issue;
import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by @kash on 2/21/2018.
 */
public class gui {
    java.util.List<Issue> issues;
    JButton jButton;

    public gui()   {
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Issues", new DisplayIssues());
        jTabbedPane.add("About Us", new AboutUs());
        JFrame jFrame = new JFrame("Issue Prioritizer");
        jFrame.setLayout(new GridLayout(2,1));
        jButton = new JButton("REFRESH");
        jFrame.add(jTabbedPane);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }
}


class DisplayIssues extends Panel{
    Connection db_conn;
    java.util.List<String> issue_titles;
    String data[][];
    String headers[];
    Calendar state;
    JButton jButton;

    DisplayIssues() {
        state = Calendar.getInstance();
        this.setLayout(new GridLayout(2,1));
        jButton = new JButton("Refresh");
        issue_titles = new ArrayList<>();
        try{
            db_conn = DatabaseUtils.getDatabaseConnection();
            java.util.List<Issue> issues = DatabaseUtils.getIssues(db_conn);
            issue_titles = GUIUtils.getIssuesForDisplay(issues);
            data = GUIUtils.getIssuesForJTable(issues);
            headers = GUIUtils.getHeadersForJTable();
        }
        catch(Exception e){
           System.out.println(e.getMessage());
           e.printStackTrace();
           issue_titles.add("ERROR: " + e.getMessage());
           data = new String[1][1];
           data[0][0] = e.getMessage();
           headers = new String[1];
           headers[0] = "ERROR";
        }
        //JList jList = new JList(issue_titles.toArray());
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        DefaultTableCellHeaderRenderer headerRenderer = new DefaultTableCellHeaderRenderer();
        JTable jTable = new JTable(data, headers);
        jTable.setDefaultRenderer(String.class, cellRenderer);
        JScrollPane jScrollPane = new JScrollPane(jTable);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = Calendar.getInstance();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                String since = format.format(state.getTime());
                jButton.setText("CLICK TO REFRESH (last updated: " + since + ")");
            }
        });
        add(jButton);
        add(jScrollPane);
    }

}

class AboutUs extends Panel {
    JLabel jLabell;

    AboutUs()   {
        jLabell = new JLabel("Empty label");
        jLabell.setText("Issue Prioritizer\n Developed by: akash-07");
        add(jLabell);
    }
}


