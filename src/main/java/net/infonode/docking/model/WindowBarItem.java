/*
 * Copyright (C) 2004 NNL Technology AB
 * Visit www.infonode.net for information about InfoNode(R) 
 * products and how to contact NNL Technology AB.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, 
 * MA 02111-1307, USA.
 */


// $Id: WindowBarItem.java,v 1.12 2007/01/28 21:25:10 jesper Exp $
package net.infonode.docking.model;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.properties.WindowBarProperties;
import net.infonode.properties.propertymap.PropertyMap;

import java.util.ArrayList;

/**
 * @author $Author: jesper $
 * @version $Revision: 1.12 $
 */
public class WindowBarItem extends AbstractTabWindowItem {
  private WindowBarProperties windowBarProperties;

  public WindowBarItem() {
  }

  public WindowBarItem(WindowBarItem windowBarItem) {
    super(windowBarItem);
  }

  protected DockingWindow createWindow(ViewReader viewReader, ArrayList childWindows) {
    return null;
  }

  public WindowItem copy() {
    return new WindowBarItem(this);
  }

  public WindowBarProperties getWindowBarProperties() {
    return windowBarProperties;
  }

  protected PropertyMap getPropertyObject() {
    return windowBarProperties.getMap();
  }

  public void setWindowBarProperties(WindowBarProperties windowBarProperties) {
    this.windowBarProperties = windowBarProperties;
  }

  public boolean isRestoreWindow() {
    return false;
  }

  public String toString() {
    return "WindowBar:\n" + super.toString();
  }

}
