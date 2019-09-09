package org.rcplite.windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.rcplite.api.windows.PreferencePage;

public class PreferenceDialog extends JDialog {

    JPanel treePanel;
    JPanel contentPanel;
    JPanel buttonsPanel;
    JTree tree;
    JButton saveButton;
    JButton cancelButton;
    Set<PreferencePage> preferences;

    public PreferenceDialog(JFrame parent, Set<PreferencePage> preferences){
        super(parent, "Preferences", true);
        setLayout(new BorderLayout());
        treePanel = new JPanel(new BorderLayout());
        contentPanel = new JPanel(new CardLayout());
        buttonsPanel = new JPanel(new FlowLayout());
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        //PreferenceTreeFactory.getTree().setRootVisible(false);
        //PreferenceTreeFactory.getTree().setShowsRootHandles(true);
        Iterator<PreferencePage> preferencesIterator = preferences.iterator();
        while(preferencesIterator.hasNext()){
            PreferencePage p = preferencesIterator.next();
            Class type = p.getClass();
            PlatformPreference.Action action = (PlatformPreference.Action) type.getAnnotation(PlatformPreference.Action.class);
            String[] pathArr = action.path().split("/");
            DefaultMutableTreeNode n = PreferenceTreeFactory.buildNode(new ArrayList<String>(Arrays.asList(pathArr)),p);
            contentPanel.add(p.getViewComponent(), action.path());
        }
// chris ifeanyi anichebe
        tree = PreferenceTreeFactory.getTree();
        treePanel.add(new JScrollPane(tree), BorderLayout.CENTER);
        JScrollPane sp = new JScrollPane(tree);
        sp.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            int selRow = tree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
            if(selRow != -1) {
                if(e.getClickCount() == 1) {
                    DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                    if(dmtn.getUserObject() instanceof PreferencePage){
                        PreferencePage p = (PreferencePage) dmtn.getUserObject();
                        Class type = p.getClass();
                        PlatformPreference.Action action = (PlatformPreference.Action) type.getAnnotation(PlatformPreference.Action.class);
                        CardLayout cl = (CardLayout)(contentPanel.getLayout());
                        cl.show(contentPanel, action.path());
                    }
                }
                else if(e.getClickCount() == 2) {
                }
            }
            }
        };
        tree.addMouseListener(ml);
        tree.setCellRenderer((tree, value, selected, expanded, leaf, row, hasFocus) -> {
            JLabel label = new JLabel();
            Object o = ((DefaultMutableTreeNode) value).getUserObject();
            if (o instanceof PreferencePage) {
                PreferencePage p = (PreferencePage) o;
                label.setText(p.getTitle());
            } else {
                label.setText((String)((DefaultMutableTreeNode) value).getUserObject());
                label.setBorder(BorderFactory.createEmptyBorder ( 4, 4, 4, 4 ));
            }
            return label;
        });

        saveButton.addActionListener(e -> {
        	Iterator<PreferencePage> it = this.preferences.iterator();
            while(it.hasNext()){
                it.next().savePreferences();
            }
        });

        cancelButton.addActionListener(e -> this.dispose());

        add(sp, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
}
