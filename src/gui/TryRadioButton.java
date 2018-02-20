package gui;

import javafx.scene.control.RadioButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by @kash on 2/20/2018.
 */
public class TryRadioButton implements ActionListener{
    JLabel jLabel;

    @Override
    public void actionPerformed(ActionEvent e) {
        jLabel.setText("You pressed " + e.getActionCommand());
    }

    private void makeGUI()  {
        JFrame jFrame = new JFrame("Trying out JLabel");
        jFrame.setLayout(new FlowLayout());
        jFrame.setSize(220, 90);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JRadioButton radioButton =  new JRadioButton("A");
        JRadioButton radioButton1 = new JRadioButton("B");
        JRadioButton radioButton2 = new JRadioButton("C");

        radioButton.addActionListener(this);
        radioButton1.addActionListener(this);
        radioButton2.addActionListener(this);

        jLabel = new JLabel("No text added");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton);
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);

        jFrame.add(radioButton);
        jFrame.add(radioButton1);
        jFrame.add(radioButton2);
        jFrame.add(jLabel);
        jFrame.setVisible(true);
    }

    TryRadioButton()    {
        makeGUI();
    }

    public static void main(String[] args)  {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TryRadioButton();
            }
        });
    }
}
