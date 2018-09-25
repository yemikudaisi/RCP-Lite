package org.rcplite.platform.windows;

import java.util.ArrayList;

public class Perspective {

    String name;
    ArrayList<ViewComponent> views;

    public Perspective( String name){
        this(name, new ArrayList<ViewComponent>());
    }

    public Perspective(String name, ArrayList<ViewComponent> views){
        this.name = name;
        this.views = views;
    }

    public Perspective add(ViewComponent vc){
        views.add(vc);
        return this;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ViewComponent> getViews() {
        return views;
    }
}
