package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by @kash on 2/20/2018.
 */
public class TrySwing {
    JLabel jLabel;

    TrySwing()  {
        JFrame jFrame = new JFrame("An Event Example");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(220, 90);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton jButton = new JButton("Alpha");
        JButton jButton1 = new JButton("Beta");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel.setText("Alpha pressed");
            }
        });

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel.setText("Beta pressed");
            }
        });

        jFrame.add(jButton);
        jFrame.add(jButton1);
        jLabel = new JLabel("Press the button");
        jFrame.add(jLabel);
        jFrame.setVisible(true);
    }

    public static void main(String[] args)  {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrySwing();
            }
        });
    }
}
