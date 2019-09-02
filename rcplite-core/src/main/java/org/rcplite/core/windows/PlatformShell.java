package org.rcplite.core.windows;

import static org.rcplite.core.Application.getInjector;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.rcplite.api.windows.Component;
import org.rcplite.api.windows.ShellConfiguration;
import org.rcplite.api.windows.StatusBar;
import org.rcplite.core.windows.controls.ToolBar;
import org.rcplite.core.windows.layout.WrapLayout;

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

	public final static String DOCUMENTS_WINDOW_NAME = "windows.documents";
	public final static String EXPLORERS_WINDOW_NAME = "windows.explorer";
	public final static String PROPERTIES_WINDOW_NAME = "windows.properties";
	public final static String OUTPUT_WINDOW_NAME = "windows.outputs";
	
	private TabWindow explorerWindow = new TabWindow();
    private TabWindow documentsWindow = new TabWindow();
    private TabWindow outputWindow = new TabWindow();
    private TabWindow propertiesWindow = new TabWindow();
	private RootWindow rootWindow;
    private ViewMap rootViewMap;

    private ArrayList<View> openDocumentViews;
    private ArrayList<View> openPropertyView;
    private ArrayList<View> openOutputViews;
    private ArrayList<View> openExplorerViews;
    private Set<Component> components;   
    private Set<ToolBar>toolbars;   
    StatusBar statusBar;

	@Inject public PlatformShell(
			Set<Component> components, 
			StatusBar statusBar, 
			ShellConfiguration config,
			Set<ToolBar> toolbars) {
		this.components = components;
		this.statusBar = statusBar;
		this.toolbars = toolbars;
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
        PlatformMenuFactory.addMenu("Tools");
        PlatformMenuFactory.addMenu("Window");
        PlatformMenuFactory.addMenu("Help");

        JMenuItem prefMenu = PlatformMenuFactory.buildMenusFromPath("Edit/Preferences",null);
        prefMenu.addActionListener((e)->{
            PreferenceDialog d = new PreferenceDialog(this);
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
                        	Object obj = getInjector().getInstance(action.id());
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
    			PROPERTIES_WINDOW_NAME,
    			OUTPUT_WINDOW_NAME,
    			EXPLORERS_WINDOW_NAME);
    	
    	propertiesWindow.addListener(listener);
    	propertiesWindow.setName(PlatformShell.PROPERTIES_WINDOW_NAME);
    	
    	outputWindow.addListener(listener);
    	outputWindow.setName(PlatformShell.OUTPUT_WINDOW_NAME);
    	
    	explorerWindow.addListener(listener);
    	explorerWindow.setName(PlatformShell.EXPLORERS_WINDOW_NAME);
    	
    	documentsWindow.getWindowProperties().setCloseEnabled(false);
    	documentsWindow.setName(PlatformShell.DOCUMENTS_WINDOW_NAME);

        openDocumentViews = new ArrayList<>();
        openPropertyView = new ArrayList<>();
        openExplorerViews = new ArrayList<>();
        openOutputViews = new ArrayList<>();

        rootViewMap = new ViewMap();
        rootWindow = DockingUtil.createRootWindow(rootViewMap, true);

        SplitWindow explorerDocumentsSplit = new SplitWindow(true);
        explorerDocumentsSplit.setWindows(explorerWindow, documentsWindow);
        explorerDocumentsSplit.setDividerLocation(getExplorerDividerLocation());

        SplitWindow explorerDocumentsPropertiesSplit = new SplitWindow(true);
        explorerDocumentsPropertiesSplit.setWindows(explorerDocumentsSplit, propertiesWindow);
        explorerDocumentsPropertiesSplit.setDividerLocation(getExplorerAndDocumentDividerLocation());

        SplitWindow mainSplit = new SplitWindow(false);
        mainSplit.setWindows(explorerDocumentsPropertiesSplit, outputWindow);
        mainSplit.setDividerLocation(getOutputDividerLocation());

        rootWindow.setWindow(mainSplit);

        DockingWindowsTheme theme = new ClassicDockingTheme();
        rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);

        rootWindow.getRootWindowProperties().addSuperObject(
                theme.getRootWindowProperties());

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
    	
    	propertiesWindow.close(); // FIXME properties window not closing
    	
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

    private void addPropertyView(View v){
        for(View p: openPropertyView){
            if(p.getTitle().equalsIgnoreCase(v.getTitle()) ){
                v.restore();
                return;
            }
        }
        propertiesWindow.addTab(v);
        openPropertyView.add(v);
    }

    private void addDocumentView(View view) {
    	documentsWindow.restore();
        for(View doc: openDocumentViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restore();
                return;
            }
        }
        documentsWindow.addTab(view);
        openDocumentViews.add(view);
    }

    private void addExplorerView(View view) {
    	explorerWindow.restore();
        for(View doc: openExplorerViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restore();
                return;
            }
        }
        explorerWindow.addTab(view);
        openExplorerViews.add(view);
    }

    private void addOutputView(View view) {
    	outputWindow.restore();
        for(View doc: openOutputViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restore();
                return;
            }
        }
        outputWindow.addTab(view);
        openOutputViews.add(view);
    }

    @Override
    public void addViewComponent(Component viewComponent) {
        Class type = viewComponent.getClass();
        ViewComponent.Configuration conf = (ViewComponent.Configuration) type.getAnnotation(ViewComponent.Configuration.class);
        View view = viewComponent.getView();
        
        // Assign view properties
        view.getWindowProperties().setCloseEnabled(conf.closable());
        view.getWindowProperties().setMinimizeEnabled(conf.minimizable());
        view.getWindowProperties().setMaximizeEnabled(conf.maximizable());
        view.getWindowProperties().setRestoreEnabled(conf.restorable());
        view.getWindowProperties().setDockEnabled(conf.dockable());
        view.getWindowProperties().setDragEnabled(conf.dragable());
        
        switch (conf.position()){
            case DOCUMENT:
                addDocumentView(view);
                break;
            case OUTPUT:
                addOutputView(view);
                break;
            case EXPLORER:
                addExplorerView(view);
                break;
            case PROPERTY:
                addPropertyView(view);
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
