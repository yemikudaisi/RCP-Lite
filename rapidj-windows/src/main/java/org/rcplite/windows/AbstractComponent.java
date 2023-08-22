package org.rapidj.windows;

import net.infonode.docking.View;

import javax.swing.*;

import org.rapidj.api.windows.Component;

public abstract class AbstractComponent extends JComponent implements Component {
    View view;
    String title;
    Icon icon;
    static int index=0;

    public AbstractComponent(){

        title = "Component "+index;
        icon = null;
        AbstractComponent.index++;
    }

    protected void setTitle(String title){
        this.title = title;
        getView().setName(title);
    }

    protected void setIcon(Icon i){
        this.icon = i;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public View getView() {
        if(view == null)
            view = new View(this.title, icon, this);
        return view;
    }


}
