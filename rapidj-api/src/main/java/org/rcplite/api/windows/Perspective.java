package org.rapidj.api.windows;

import java.util.ArrayList;

public interface Perspective {
	Perspective add(Component vc);

    String getName();

    public ArrayList<Component> getViews();
}