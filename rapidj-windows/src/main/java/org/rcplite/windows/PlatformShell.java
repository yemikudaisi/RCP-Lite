package org.rapidj.windows;

import java.awt.BorderLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.rapidj.api.Application;
import org.rapidj.api.windows.Component;
import org.rapidj.api.windows.PreferencePage;
import org.rapidj.api.windows.ShellConfiguration;
import org.rapidj.api.windows.StatusBar;
import org.rapidj.api.windows.ToolBar;
import org.rapidj.windows.layout.WrapLayout;

import com.google.inject.Inject;

import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.theme.ClassicDockingTheme;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;

@SuppressWarnings("serial")
public class PlatformShell extends AbstractShell {

	public final static String CENTER_WINDOW_NAME = "windows.documents";
	public final static String LEFT_WINDOW_NAME = "windows.explorer";
	public final static String RIGHT_WINDOW_NAME = "windows.properties";
	public final static String BOTTOM_WINDOW_NAME = "windows.outputs";
	
	private TabWindow leftWindow = new TabWindow();
    private TabWindow centerWindow = new TabWindow();
    private TabWindow bottomWindow = new TabWindow();
    private TabWindow rightWindow = new TabWindow();
	private RootWindow rootWindow;
    private ViewMap rootViewMap;

    private Set<Component> components;   
    private Set<ToolBar>toolbars;   
    StatusBar statusBar;
    Set<PreferencePage> preferences;

	@Inject public PlatformShell(
			Set<Component> components, 
			StatusBar statusBar, 
			ShellConfiguration config,
			@Nullable Set<ToolBar> toolbars,
			@Nullable Set<PreferencePage> preferences) {
		this.components = components;
		this.statusBar = statusBar;
		this.toolbars = toolbars;
		this.preferences = preferences;
		this.setConfiguration(config);
        this.setTitle(getConfiguration().getTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

		initUIComponents();
        initMenu();
        initToolBar();
        initStatusBar();
		setSize(900, 700);
	}
	
	private void initStatusBar() {
		JComponent panel = statusBar.getComponent();
		add(panel,BorderLayout.SOUTH);
	}
	
	private void initToolBar() {
	    if (this.toolbars == null || this.toolbars.isEmpty())
	        return;
		JPanel panel = new JPanel();
	    panel.setLayout(new WrapLayout(WrapLayout.LEFT));
	    for(ToolBar t:toolbars) {
	    	panel.add(t.getBar());
	    }
	    add(panel, BorderLayout.PAGE_START);
	}

	@SuppressWarnings("unchecked")
	private void initMenu(){
        PlatformMenuFactory.addMenu("File");
        PlatformMenuFactory.addMenu("Edit");
        PlatformMenuFactory.addMenu("Window");
        PlatformMenuFactory.addMenu("Help");

        JMenuItem prefMenu = PlatformMenuFactory.buildMenusFromPath("Edit/Preferences",null);
        prefMenu.addActionListener((e)->{
            PreferenceDialog d = new PreferenceDialog(this, this.preferences);
            d.setLocationRelativeTo(this);
            d.setSize(640,480);
            d.setVisible(true);
        });
        
        // Get view components from components service
        Iterator<Component> views = components.iterator();
        while(views.hasNext()){
            Component v = views.next();
            Class type = v.getClass();
            ViewComponent.Menu menu = (ViewComponent.Menu) type.getAnnotation(ViewComponent.Menu.class); // Get menu for component
            ViewComponent.Action action = (ViewComponent.Action) type.getAnnotation(ViewComponent.Action.class); // Get action for component
            if(menu != null && action != null) {
                String[] treeArr = menu.path().split("/");
                JMenuItem menuItem = PlatformMenuFactory.buildMenus(new ArrayList<String>(Arrays.asList(treeArr)), v.getIcon());

                menuItem.addActionListener(e -> {
                    switch(action.category()){
                        case VIEW:
                        	Object obj = Application.getInjector().getInstance(action.id());
                            if(obj instanceof ViewComponent){
                                this.addViewComponent((ViewComponent)obj);
                            }
                            break;
					case PERSPECTIVE:
						break;
					default:
						break;
                    }
                });
                switch (action.category()){
                    default:
                }
            }
        }

        setJMenuBar(PlatformMenuFactory.getMenuBar());
    }
	
    private void initUIComponents(){
    	TabWindowListener listener = new TabWindowListener(
    			RIGHT_WINDOW_NAME,
    			BOTTOM_WINDOW_NAME,
    			LEFT_WINDOW_NAME);

    	rightWindow.addListener(listener);
    	rightWindow.getWindowProperties().setUndockEnabled(false);
    	rightWindow.getWindowProperties().setMaximizeEnabled(false);
    	rightWindow.setName(PlatformShell.RIGHT_WINDOW_NAME);
    	
    	bottomWindow.addListener(listener);
    	bottomWindow.setName(PlatformShell.BOTTOM_WINDOW_NAME);
    	bottomWindow.getWindowProperties().setUndockEnabled(false);
    	bottomWindow.getWindowProperties().setMaximizeEnabled(false);
    	
    	leftWindow.addListener(listener);
    	leftWindow.setName(PlatformShell.LEFT_WINDOW_NAME);
    	leftWindow.getWindowProperties().setCloseEnabled(false);
    	leftWindow.getWindowProperties().setUndockEnabled(false);
    	leftWindow.getWindowProperties().setMaximizeEnabled(false);
    	
    	centerWindow.getWindowProperties().setCloseEnabled(false);
    	centerWindow.setName(PlatformShell.CENTER_WINDOW_NAME);

        rootViewMap = new ViewMap();
        rootWindow = DockingUtil.createRootWindow(rootViewMap, true);

        SplitWindow explorerDocumentsSplit = new SplitWindow(true);
        explorerDocumentsSplit.setWindows(leftWindow, centerWindow);
        explorerDocumentsSplit.setDividerLocation(getExplorerDividerLocation());

        SplitWindow explorerDocumentsPropertiesSplit = new SplitWindow(true);
        explorerDocumentsPropertiesSplit.setWindows(explorerDocumentsSplit, rightWindow);
        explorerDocumentsPropertiesSplit.setDividerLocation(getExplorerAndDocumentDividerLocation());

        SplitWindow mainSplit = new SplitWindow(false);
        mainSplit.setWindows(explorerDocumentsPropertiesSplit, bottomWindow);
        mainSplit.setDividerLocation(getOutputDividerLocation());

        rootWindow.setWindow(mainSplit);

        DockingWindowsTheme theme = new ClassicDockingTheme();
        rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);

        // Split window
        rootWindow.getRootWindowProperties().getSplitWindowProperties()
        .setContinuousLayoutEnabled(false);
        // Window bar
        rootWindow.getRootWindowProperties().getWindowBarProperties()
        	.setContinuousLayoutEnabled(false);
        
        rootWindow.getRootWindowProperties().addSuperObject(
                theme.getRootWindowProperties());
        
        //rootWindow.getRootWindowProperties().getWindowBarProperties()

        add(rootWindow, BorderLayout.CENTER);

        loadComponents();
    }
    

    private float getExplorerDividerLocation() {
    	float bal = 1.0f - getConfiguration().getPreferredPropertiesWindowWidth();
    	float value = (1*getConfiguration().getPreferredExplorerWindowWidth())/bal;
    	System.out.println("supplied explorer window width: "+getConfiguration().getPreferredExplorerWindowWidth());
    	System.out.println("explorer width: "+value);
    	return value;
    }
    
    private float getExplorerAndDocumentDividerLocation() {
    	float value = 1.0f - getConfiguration().getPreferredPropertiesWindowWidth();
    	System.out.println("supplied properties window width: "+getConfiguration().getPreferredPropertiesWindowWidth());
    	System.out.println("explorer+document width: "+value);
    	return value;
    }
    
    private float getOutputDividerLocation() {
    	return 1.0f - getConfiguration().getPreferredOutputWindowHeight();
    }

    private void loadComponents(){
    	
    	// FIXME Make window close when they don't have content -> working not tested
    	// particularly at startup -> all efforts failed so far 
    	
        Iterator<Component> views = components.iterator();
        while(views.hasNext()){
            Component v = views.next();
            if (v instanceof ViewComponent){
                Class type = v.getClass();
                ViewComponent.Configuration conf = (ViewComponent.Configuration) type.getAnnotation(ViewComponent.Configuration.class);
                ViewComponent viewComponent = (ViewComponent) v;
                
                if(conf != null && conf.openOnStart()) {
                	addViewComponent(viewComponent);
                }
            }
        }
    }

    private void addRightTab(View v){
    	rightWindow.restore();
        rightWindow.addTab(v);
    }

    private void addCenterTab(View view) {
    	centerWindow.restore();
        centerWindow.addTab(view);
    }

    private void addLeftTab(View view) {
    	leftWindow.restore();
    	/*
        for(View doc: openExplorerViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restore();
                return;
            }
        }*/
        //view.getViewProperties().getViewTitleBarProperties().setOrientation(ViewTitleBarProp)
        leftWindow.addTab(view);
    }

    private void addBottomView(View view) {
    	bottomWindow.restore();
        /*for(View doc: openOutputViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restore();
                return;
            }
        }*/
        bottomWindow.addTab(view);
    }

    @Override
    public void addViewComponent(Component viewComponent) {

        // initially minimize all windows till a component is added to it
        leftWindow.minimize();
        rightWindow.minimize();
        bottomWindow.minimize();
        Class type = viewComponent.getClass();
        ViewComponent.Configuration conf = (ViewComponent.Configuration) type.getAnnotation(ViewComponent.Configuration.class);
        View view = viewComponent.getView();
        
        // Assign view properties
        // TODO: Make this configurable by third party user
        view.getWindowProperties().setCloseEnabled(conf.closable());
        view.getWindowProperties().setMinimizeEnabled(conf.minimizable());
        view.getWindowProperties().setMaximizeEnabled(conf.maximizable());
        view.getWindowProperties().setRestoreEnabled(conf.restorable());
        view.getWindowProperties().setDockEnabled(conf.dockable());
        view.getWindowProperties().setDragEnabled(conf.dragable());
        
        switch (conf.position()){
            case CENTER:
                addCenterTab(view);
                break;
            case BOTTOM:
                addBottomView(view);
                break;
            case LEFT:
                addLeftTab(view);
                break;
            case RIGHT:
                addRightTab(view);
                break;
        }
    }


    @Override
    public void launch(){

        if (getConfiguration().isMaximizeOnStartup()){
            setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        }
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    this.setVisible(true);
    }

}
