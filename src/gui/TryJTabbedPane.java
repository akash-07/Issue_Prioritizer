package gui;

import javax.swing.*;
import java.util.jar.JarFile;

/**
 * Created by @kash on 2/20/2018.
 */
public class TryJTabbedPane {
    TryJTabbedPane()    {
        JFrame jFrame = new JFrame("Tabbed Frame Example");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(300,300);
        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Cities", new CitiesPanel());
        jTabbedPane.addTab("Flavors", new FlavorsPanel());
        jTabbedPane.addTab("Colors", new ColorsPanel());
        jFrame.add(jTabbedPane);
        jFrame.setVisible(true);
    }

    class CitiesPanel extends JPanel    {
        public CitiesPanel()    {
            JButton b1 = new JButton("New York");
            add(b1);
            JButton b2 = new JButton("London");
            add(b2);
            JButton b3 = new JButton("Hong Kong");
            add(b3);
        }
    }

    class ColorsPanel extends JPanel {
        public ColorsPanel() {
            JCheckBox cb1 = new JCheckBox("Red");
            add(cb1);
            JCheckBox cb2 = new JCheckBox("Green");
            add(cb2);
            JCheckBox cb3 = new JCheckBox("Blue");
            add(cb3);
        }
    }

    class FlavorsPanel extends JPanel {
        public FlavorsPanel() {
            JComboBox jcb = new JComboBox();
            jcb.addItem("Vanilla");
            jcb.addItem("Chocolate");
            jcb.addItem("Strawberry");
            add(jcb);
        }
    }

    public static void main(String[] args)  {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TryJTabbedPane();
            }
        });
    }
}
