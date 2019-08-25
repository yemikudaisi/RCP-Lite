package org.rcplite.platform.utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageManager {

    public static ImageIcon getImageIcon(String path){
        URL url = ImageManager.class.getResource(path);
        if (url != null) {
            return new ImageIcon(url);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public static Icon getImageIcon(String path, int width, int height){
        Image image = ((ImageIcon) ImageManager.getImageIcon("/images/clear-log.png")).getImage();
        Image newimg = image.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH);
        return  new ImageIcon(newimg);
    }
}
