package org.rcplite.platform.windows;

import net.miginfocom.swing.MigLayout;
import org.rcplite.platform.modules.Tool;
import org.rcplite.platform.services.PreferenceService;
import org.rcplite.platform.services.ShellService;
import org.rcplite.platform.spi.Preference;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PreferenceDialog extends JDialog {

    JPanel treePanel;
    JPanel contentPanenl;
    JTree tree;

    public PreferenceDialog(JFrame parent){
        super(parent, "Preferences", true);
        setLayout(new BorderLayout());
        treePanel = new JPanel(new BorderLayout());
        contentPanenl = new JPanel(new BorderLayout());

        Iterator<Preference> preferences = PreferenceService.getInstance().getPreferences();
        while(preferences.hasNext()){
            Preference p = preferences.next();
            Class type = p.getClass();
            PlatformPreference.Action action = (PlatformPreference.Action) type.getAnnotation(PlatformPreference.Action.class);
            String[] pathArr = action.path().split("/");
            DefaultMutableTreeNode n = PreferenceTreeFactory.buildNode(new ArrayList<String>(Arrays.asList(pathArr)));
        }

        tree = PreferenceTreeFactory.getTree();
        tree.setRootVisible(false);
        treePanel.add(new JScrollPane(tree), BorderLayout.CENTER);

        add(treePanel, BorderLayout.WEST);
        add(contentPanenl, BorderLayout.CENTER);

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {
                        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                        if(dmtn.getUserObject() instanceof Tool){
                            Tool tool = (Tool)dmtn.getUserObject();
                            for(ViewComponent v: tool.getPerspective().getViews()){
                                ShellService.getInstance().getShell().addViewComponent(v);
                            }
                        }
                    }
                    else if(e.getClickCount() == 2) {
                    }
                }
            }
        };
        tree.addMouseListener(ml);
        tree.setPreferredSize(new Dimension(240,480));
    }
}
