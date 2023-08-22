package org.rapidj.windows;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlatformMenuFactory {
    static List<JMenu> menus = new ArrayList<>();
    static JMenuBar menuBar = new JMenuBar();

    public static JMenu addMenu(String text){
        for(JMenu menu: menus){
            if(menu.getText().equals(text)){
                return  menu;
            }
        }
        JMenu newMenu = new JMenu(text);
        menus.add(newMenu);
        menuBar.add(newMenu);
        return  newMenu;
    }

    public static JMenuItem buildMenus(ArrayList<String> path, Icon icon){
        if(path.isEmpty())
            throw new IllegalArgumentException("Menu path should be empty");

        JMenu menu = addMenu(path.get(0));
        path.remove(0);
        return (path.isEmpty())? menu:buildMenuTree(menu, path, icon);
    }

    public static JMenuItem buildMenusFromPath(String s, Icon icon){
        String[] treeArr = s.split("/");
        return buildMenus(new ArrayList<String>(Arrays.asList(treeArr)),icon);
    }

    private static JMenuItem buildMenuTree(JMenuItem parent, ArrayList<String> path, Icon icon){
        if(path.size() == 1){
            JMenuItem item = new JMenuItem(path.get(0));
            if(icon != null)
            	item.setIcon(icon);
            parent.add(item);
            return  item;
        }else{
            JMenu otherParent = new JMenu(path.get(0));
            path.remove(0);
            ((JMenu) parent).add(otherParent);
            return buildMenuTree(otherParent, path,icon);
        }
    }

    public static JMenuBar getMenuBar(){
        return menuBar;
    }
}
