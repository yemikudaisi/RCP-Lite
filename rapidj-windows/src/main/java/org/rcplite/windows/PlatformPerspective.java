package org.rapidj.windows;

import java.util.ArrayList;

import org.rapidj.api.windows.Component;
import org.rapidj.api.windows.Perspective;

public class PlatformPerspective implements Perspective {

    String name;
    ArrayList<Component> views;

    public PlatformPerspective( String name){
        this(name, new ArrayList<Component>());
    }

    public PlatformPerspective(String name, ArrayList<Component> views){
        this.name = name;
        this.views = views;
    }

    public Perspective add(Component vc){
        views.add((ViewComponent)vc);
        return this;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Component> getViews() {
        return views;
    }
}
