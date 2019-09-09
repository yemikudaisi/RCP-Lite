package org.rcplite.windows;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import org.rcplite.api.windows.PreferencePage;

import java.util.ArrayList;
import java.util.List;

public class PreferenceTreeFactory {
    static List<DefaultMutableTreeNode> nodes = new ArrayList<>();
    static DefaultMutableTreeNode root = new DefaultMutableTreeNode("Preferences");
    static JTree tree = new JTree(root);
    static PreferencePage nodeObject;

    public static DefaultMutableTreeNode addTreeNode(String text){
        for(DefaultMutableTreeNode node: nodes){
            if((String)node.getUserObject() == text){
                return  node;
            }
        }
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(text);
        nodes.add(newNode);
        root.add(newNode);
        return  newNode;
    }

    public static DefaultMutableTreeNode buildNode(ArrayList<String> path, PreferencePage p){
        if(path.isEmpty())
            throw new IllegalArgumentException("Menu path should be empty");

        nodeObject = p;
        DefaultMutableTreeNode node = addTreeNode(path.get(0));
        path.remove(0);
        return (path.isEmpty())? node :buildNodeTree(node, path);
    }

    static DefaultMutableTreeNode buildNodeTree(DefaultMutableTreeNode parent, ArrayList<String> path){
        if(path.size() == 0){
            parent.setUserObject(nodeObject);
            return parent;
        }

        DefaultMutableTreeNode child = new DefaultMutableTreeNode(path.get(0));
        path.remove(0);
        parent.add(child);
        return buildNodeTree(child, path);
    }

    public static JTree getTree(){
        return tree;
    }
}
