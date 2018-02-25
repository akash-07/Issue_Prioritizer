package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by @kash on 2/25/2018.
 */
public class EvenOddRenderer implements TableCellRenderer {
    public static final DefaultTableCellRenderer DEFAULT_RENDERER =
            new DefaultTableCellRenderer();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color foreground, background;
        if(isSelected)  {
            foreground = Color.yellow;
            background = Color.GREEN;
        }
        else {
            if(row % 2 == 0)    {
                foreground = Color.darkGray;
                background = Color.WHITE;
            }
            else {
                foreground = Color.WHITE;
                background = Color.GRAY;
            }
        }

        renderer.setBackground(background);
        renderer.setForeground(foreground);
        return renderer;
    }
}
