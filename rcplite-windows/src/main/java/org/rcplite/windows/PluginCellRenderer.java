package org.rcplite.windows;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.rcplite.api.plugins.Tool;

import java.awt.Component;

public class PluginCellRenderer implements TreeCellRenderer {
    private JLabel label;

    PluginCellRenderer() {
        label = new JLabel();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        Object o = ((DefaultMutableTreeNode) value).getUserObject();
        if (o instanceof Tool) {
            Tool tool = (Tool) o;
            if (tool.getIcon() == null) {
                label.setIcon(null);
            }else{
                label.setIcon(tool.getIcon());
            }
            label.setText(tool.getTitle());
        } else {
            label.setIcon(null);
            label.setText("" + value);
            label.setBorder(BorderFactory.createEmptyBorder ( 4, 4, 4, 4 ));
        }
        return label;
    }
}
