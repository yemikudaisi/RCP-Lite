package org.rcplite.platform.windows;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowListener;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.View;

public class TabWindowListener implements DockingWindowListener {

	@Override
	public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
		addedToWindow.restore();
		
	}

	@Override
	public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
		if(removedFromWindow.getChildWindowCount()<1) {
			removedFromWindow.close();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowShown(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowHidden(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewFocusChanged(View previouslyFocusedView, View focusedView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(DockingWindow window) throws OperationAbortedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowUndocking(DockingWindow window) throws OperationAbortedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowUndocked(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDocking(DockingWindow window) throws OperationAbortedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDocked(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowMinimizing(DockingWindow window) throws OperationAbortedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowMinimized(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowMaximizing(DockingWindow window) throws OperationAbortedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowMaximized(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowRestoring(DockingWindow window) throws OperationAbortedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowRestored(DockingWindow window) {
		// TODO Auto-generated method stub
		
	}

}
