package gui;

import javax.swing.*;

/**
 * Created by @kash on 2/20/2018.
 */
public class TryJTable {
    TryJTable()   {
        String[] colHeads = {"Name", "Extension", "#IDs"};
        String[][] data =  {
                { "Gail", "4567", "865" },
                { "Ken", "7566", "555" },
                { "Viviane", "5634", "587" },
                { "Melanie", "7345", "922" },
                { "Anne", "1237", "333" },
                { "John", "5656", "314" },
                { "Matt", "5672", "217" },
                { "Claire", "6741", "444" },
                { "Erwin", "9023", "519" },
                { "Ellen", "1134", "532" },
                { "Jennifer", "5689", "112" },
                { "Ed", "9030", "133" },
                { "Helen", "6751", "145" }
        };

        JFrame jFrame = new JFrame("JTable Example");
        JTable jTable = new JTable(data, colHeads);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jFrame.add(jScrollPane);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public static void main(String[] args)  {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TryJTable();
            }
        });
    }
}
