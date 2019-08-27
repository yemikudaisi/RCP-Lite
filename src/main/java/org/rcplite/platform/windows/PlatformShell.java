package org.rcplite.platform.windows;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JMenuItem;

import org.rcplite.platform.config.PlatformShellConfiguration;
import org.rcplite.platform.services.ComponentService;
import org.rcplite.platform.spi.Shell;

import net.infonode.docking.RootWindow;
import net.infonode.docking.SplitWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.theme.ClassicDockingTheme;
import net.infonode.docking.theme.DockingWindowsTheme;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.PropertiesUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.util.Direction;

public class PlatformShell extends AbstractShell {

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

	public PlatformShell() {
        this.setTitle(PlatformShellConfiguration.getDefaultConfig().getTitle());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

		initUIComponents();
        initMenu();
		setSize(900, 700);
	}

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
        Iterator<Component> views = ComponentService.getInstance().getComponents();
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
                            try {
                                Object obj = action.id().newInstance();
                                if(obj instanceof ViewComponent){
                                    this.addViewComponent((ViewComponent)obj);
                                }
                            } catch (InstantiationException e1) {
                                e1.printStackTrace();
                            } catch (IllegalAccessException e1) {
                                e1.printStackTrace();
                            }
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
    	
    	propertiesWindow.addListener(new TabWindowListener());
    	outputWindow.addListener(new TabWindowListener());
    	explorerWindow.addListener(new TabWindowListener());

        openDocumentViews = new ArrayList<>();
        openPropertyView = new ArrayList<>();
        openExplorerViews = new ArrayList<>();
        openOutputViews = new ArrayList<>();

        rootViewMap = new ViewMap();
        rootWindow = DockingUtil.createRootWindow(rootViewMap, true);

        SplitWindow explorerDocumentsSplit = new SplitWindow(true);
        explorerDocumentsSplit.setWindows(explorerWindow, documentsWindow);
        explorerDocumentsSplit.setDividerLocation(0.25f);

        SplitWindow explorerDocumentsPropertiesSplit = new SplitWindow(true);
        explorerDocumentsPropertiesSplit.setWindows(explorerDocumentsSplit, propertiesWindow);
        explorerDocumentsPropertiesSplit.setDividerLocation(0.8f);

        SplitWindow mainSplit = new SplitWindow(false);
        mainSplit.setWindows(explorerDocumentsPropertiesSplit, outputWindow);
        mainSplit.setDividerLocation(0.7f);

        rootWindow.setWindow(mainSplit);

        DockingWindowsTheme theme = new ClassicDockingTheme();
        rootWindow.getWindowBar(Direction.DOWN).setEnabled(true);
        RootWindowProperties titleBarStyleProperties = PropertiesUtil.createTitleBarStyleRootWindowProperties();

        rootWindow.getRootWindowProperties().addSuperObject(
                theme.getRootWindowProperties());
        

        //rootWindow.getRootWindowProperties().addSuperObject(titleBarStyleProperties);

        getContentPane().add(rootWindow, BorderLayout.CENTER);
        loadComponents();
    }

    private void loadComponents(){
        Iterator<Component> views = ComponentService.getInstance().getComponents();
        while(views.hasNext()){
            Component v = views.next();
            if (v instanceof ViewComponent){
                Class type = v.getClass();
                ViewComponent.Configuration conf = (ViewComponent.Configuration) type.getAnnotation(ViewComponent.Configuration.class);
                if(conf != null && conf.openOnStart()) {
                    addViewComponent((ViewComponent) v);
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
    public void addViewComponent(ViewComponent viewComponent) {
        Class type = viewComponent.getClass();
        ViewComponent.Configuration conf = (ViewComponent.Configuration) type.getAnnotation(ViewComponent.Configuration.class);
        switch (conf.position()){
            case DOCUMENT:
                addDocumentView(viewComponent.getView());
                break;
            case OUTPUT:
                addOutputView(viewComponent.getView());
                break;
            case EXPLORER:
                addExplorerView(viewComponent.getView());
                break;
            case PROPERTY:
                addPropertyView(viewComponent.getView());
                break;
        }
    }


    @Override
    public void launch(){
	    if(getConfiguration().showToolboxOnStartup()){
            addViewComponent(new ToolBoxComponent());
        }

        if (getConfiguration().isMaximizeOnStartup()){
            setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        }
	    this.setVisible(true);
    }
}
