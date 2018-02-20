package gui;

import javax.swing.*;

/**
 * Created by @kash on 2/20/2018.
 */
public class TryJLabel {
    JLabel jLabel;

    TryJLabel()    {
        JFrame jFrame = new JFrame("This is JLabel");
        jFrame.setSize(220,150);
        ImageIcon imageIcon = new ImageIcon("./src/gui/github_logo.png");
        jLabel = new JLabel("Github Logo", imageIcon, JLabel.CENTER);
        jFrame.add(jLabel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public static void main(String[] args)  {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TryJLabel();
            }
        });
    }
}
