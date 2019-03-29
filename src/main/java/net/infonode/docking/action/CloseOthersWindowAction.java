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


// $Id: CloseOthersWindowAction.java,v 1.2 2005/05/20 14:48:12 johan Exp $
package net.infonode.docking.action;

import net.infonode.docking.AbstractTabWindow;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.internalutil.InternalDockingUtil;
import net.infonode.gui.icon.button.CloseIcon;

import javax.swing.*;
import java.io.ObjectStreamException;

/**
 * Closes all tabs (with abort possibility) except the one belonging to the window the action is performed upon in
 * the AbstractTabWindow parent of the window.
 *
 * @author $Author: johan $
 * @version $Revision: 1.2 $
 * @since IDW 1.4.0
 */
public class CloseOthersWindowAction extends DockingWindowAction {
  private static final long serialVersionUID = 1;

  /**
   * The only instance of this class
   */
  public static final CloseOthersWindowAction INSTANCE = new CloseOthersWindowAction();

  private static final Icon icon = new CloseIcon(InternalDockingUtil.DEFAULT_BUTTON_ICON_SIZE);

  private CloseOthersWindowAction() {
  }

  public Icon getIcon() {
    return icon;
  }

  public String getName() {
    return "Close Others";
  }

  public boolean isPerformable(DockingWindow window) {
    return window.getWindowParent() instanceof AbstractTabWindow;
  }

  public void perform(DockingWindow window) {
    if (isPerformable(window)) {
      AbstractTabWindow tw = (AbstractTabWindow) window.getWindowParent();

      for (int i = 0; i < tw.getChildWindowCount();) {
        if (tw.getChildWindow(i) != window && tw.getChildWindow(i).isClosable()) {
          try {
            tw.getChildWindow(i).closeWithAbort();
          }
          catch (OperationAbortedException e) {
            i++;
          }
        }
        else
          i++;
      }
    }
  }

  protected Object readResolve() throws ObjectStreamException {
    return INSTANCE;
  }

}
