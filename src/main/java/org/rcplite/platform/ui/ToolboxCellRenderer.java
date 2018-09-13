package org.rcplite.platform.ui;

import org.rcplite.platform.modules.Tool;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class ToolboxCellRenderer implements TreeCellRenderer {
    private JLabel label;

    ToolboxCellRenderer() {
        label = new JLabel();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        Object o = ((DefaultMutableTreeNode) value).getUserObject();
        if (o instanceof Tool) {
            Tool tool = (Tool) o;
            Icon icon = tool.getIcon();
            if (icon != null) {
                label.setIcon(icon);
            }
            label.setText(tool.getTitle());
        } else {
            label.setIcon(null);
            label.setText("" + value);
        }
        return label;
    }
}
