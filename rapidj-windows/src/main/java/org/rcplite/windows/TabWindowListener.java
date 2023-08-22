package org.rapidj.windows;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.DockingWindowListener;
import net.infonode.docking.OperationAbortedException;
import net.infonode.docking.View;

public class TabWindowListener implements DockingWindowListener {
	String[] inclusive;
	
	public TabWindowListener(String ...inclusions){
		this.inclusive = inclusions;
	}
	
	@Override
	public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
		if(isIncluded(addedToWindow)) {
			System.out.println(addedWindow.getName() +" added to " + addedToWindow.getName());
			System.out.println(addedToWindow.getName()+ " restored.");
			addedToWindow.restore();
		}
	}

	@Override
	public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
		System.out.println(removedWindow.getName() +" removed from " + removedFromWindow.getName()+",count is "+ removedFromWindow.getChildWindowCount() );
		closeWhenEmpty(removedFromWindow);
	}

	@Override
	public void windowShown(DockingWindow window) {
		System.out.println(window.getName()+" shown. Count is "+ window.getChildWindowCount() );
		closeWhenEmpty(window);
		
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
	
	private void closeWhenEmpty(DockingWindow window) {	
		if(isIncluded(window)) {
			if(window.getChildWindowCount()<1) {
				window.close();
				System.out.println(window.getName()+" closed.");
			}
		}else {
			System.out.println(window.getName()+" omitted.");
		}
	}
	
	private boolean isIncluded(DockingWindow window) {
		for(String include: inclusive) {
			if(window.getName() != null && window.getName().equals(include)) {
				return true;
			}			
		}
		return false;
	}
	

}
