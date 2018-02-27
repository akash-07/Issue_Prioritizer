package gui;

import analyzer.PriorityUtils;
import data.DatabaseUtils;
import data.RunUtils;
import features.Issue;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
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

    public gui(String url)   {
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Issues", new DisplayIssues(url));
        jTabbedPane.add("About Us", new AboutUs());
        JFrame jFrame = new JFrame("Issue Prioritizer");
        //jFrame.setLayout(new GridLayout(2,1));
        jButton = new JButton("REFRESH");
        jFrame.add(jTabbedPane);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setSize(600,400);
    }
}


class DisplayIssues extends Panel{
    Connection db_conn;
    java.util.List<String> issue_titles;
    String data[][];
    String headers[];
    Calendar state;
    JButton refresh_button;
    JButton initialize_button;
    JPanel bottom_panel;
    JLabel jLabel;
    JTable jTable;
    String url;

    DisplayIssues(String url) {
        this.url = url;
        createUI();
    }

    public void createUI()  {
        state = Calendar.getInstance();
        refresh_button = new JButton("Refresh");
        initialize_button = new JButton("Initialize");
        issue_titles = new ArrayList<>();
        bottom_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        jLabel = new JLabel("Last updated status");

        try{
            db_conn = DatabaseUtils.getDatabaseConnection();
            java.util.List<Issue> issues = DatabaseUtils.getIssues(db_conn);
            data = GUIUtils.getIssuesForJTable(PriorityUtils.getSortedIssues(issues));
            headers = GUIUtils.getHeadersForJTable();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            data = new String[1][1];
            data[0][0] = e.getMessage();
            headers = new String[1];
            headers[0] = "ERROR";
        }

        //creating the table model
        TableModel model = new DefaultTableModel(data, headers);

        jTable = new JTable(model);
        TableCellRenderer renderer = new EvenOddRenderer();
        jTable.setDefaultRenderer(Object.class, renderer);
        jTable.setShowVerticalLines(false);
        jTable.setShowHorizontalLines(false);
        jTable.setRowHeight(30);
        JTableHeader tableHeader = jTable.getTableHeader();
        tableHeader.setFont(new Font("century gothic", Font.BOLD, 15));
        jTable.setFont(new Font("courier", Font.BOLD, 15));
        JScrollPane jScrollPane = new JScrollPane(jTable);

        bottom_panel.add(initialize_button);
        bottom_panel.add(refresh_button);
        bottom_panel.add(jLabel);
        refresh_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                state = Calendar.getInstance();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                String since = format.format(state.getTime());
                jLabel.setText("  (last updated: " + since + ")");
                updateData();
            }
        });

        initialize_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RunUtils.intialize(url);
                initialize_button.setEnabled(false);
                updateData();
            }
        });

        setLayout(new BorderLayout());
        add(jScrollPane, BorderLayout.CENTER);
        add(bottom_panel, BorderLayout.SOUTH);
    }

    private void updateData()   {
        RunUtils.refresh(url, "current");
        try{
            db_conn = DatabaseUtils.getDatabaseConnection();
            java.util.List<Issue> issues = DatabaseUtils.getIssues(db_conn);
            issue_titles = GUIUtils.getIssuesForDisplay(issues);
            data = GUIUtils.getIssuesForJTable(PriorityUtils.getSortedIssues(issues));
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

        TableModel model = new DefaultTableModel(data, headers);
        jTable.setModel(model);

    }

}

class AboutUs extends Panel {
    JLabel jLabell;
    JLabel jLabel;
    Panel bottom_panel;

    AboutUs()   {
        ImageIcon icon = new ImageIcon("./src/gui/github_logo.png");
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(250, 190, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImage);
        bottom_panel = new Panel(new FlowLayout(FlowLayout.CENTER));
        jLabell = new JLabel(icon);
        jLabel = new JLabel("Issue Prioritizer, Developed by: akash-07");
        jLabel.setFont(new Font("century gothic", Font.BOLD, 15));
        //setLayout(new BorderLayout());
        add(jLabell);
        bottom_panel.add(jLabel);
        add(bottom_panel);
    }
}


