package org.rcplite.platform.windows;

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
import org.rcplite.platform.services.ComponentService;
import org.rcplite.platform.spi.Shell;
import org.rcplite.platform.ui.ToolBoxComponent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PlatformShell extends AbstractShell implements Shell {

	ToolBoxComponent toolBoxTree;

	TabWindow explorerWindow = new TabWindow();
    TabWindow documentsWindow = new TabWindow();
    TabWindow outputWindow = new TabWindow();
    TabWindow propertiesWindow = new TabWindow();
	RootWindow rootWindow;
    ViewMap rootViewMap;

    ArrayList<View> openDocumentViews;
    ArrayList<View> openPropertyView;
    ArrayList<View> openOutputViews;
    ArrayList<View> openExplorerViews;

	public PlatformShell() {
        this.setTitle("NA Cyber Tools");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

		initUIComponents();
        initMenu();
		setSize(900, 700);
	}

	private void initMenu(){
        MenuFactory.addMenu("File");
        MenuFactory.addMenu("Edit");
        MenuFactory.addMenu("Tools");
        MenuFactory.addMenu("Window");
        MenuFactory.addMenu("Help");

        Iterator<Component> views = ComponentService.getInstance().getComponents();
        while(views.hasNext()){
            Component v = views.next();
            Class type = v.getClass();
            ViewComponent.Menu menu = (ViewComponent.Menu) type.getAnnotation(ViewComponent.Menu.class);
            ViewComponent.Action action = (ViewComponent.Action) type.getAnnotation(ViewComponent.Action.class);
            if(menu != null && action != null) {
                String[] treeArr = menu.path().split("/");
                JMenuItem menuItem = MenuFactory.buildMenu(new ArrayList<String>(Arrays.asList(treeArr)));

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

        setJMenuBar(MenuFactory.getMenuBar());
    }

    private void initUIComponents(){

        openDocumentViews = new ArrayList<>();
        openPropertyView = new ArrayList<>();
        openExplorerViews = new ArrayList<>();
        openOutputViews = new ArrayList<>();

	    toolBoxTree = new ToolBoxComponent();
        rootViewMap = new ViewMap();
        rootWindow = DockingUtil.createRootWindow(rootViewMap, true);

        explorerWindow.addTab(new View("Toolbox", null, toolBoxTree));

        SplitWindow explorerDocumentsSplit = new SplitWindow(true);
        explorerDocumentsSplit.setWindows(explorerWindow, documentsWindow);
        explorerDocumentsSplit.setDividerLocation(0.25f);

        SplitWindow explorerDocumentsPropertiesSplit = new SplitWindow(true);
        explorerDocumentsPropertiesSplit.setWindows(explorerDocumentsSplit, propertiesWindow);
        explorerDocumentsPropertiesSplit.setDividerLocation(0.8f);

        SplitWindow mainSplit = new SplitWindow(false);
        mainSplit.setWindows(explorerDocumentsPropertiesSplit, outputWindow);
        mainSplit.setDividerLocation(0.8f);

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
                    System.out.println(v.getTitle() + " component hash:" + v.hashCode());
                }
            }
        }
    }

    private void addPropertyView(View v){
        for(View p: openPropertyView){
            if(p.getTitle().equalsIgnoreCase(v.getTitle()) ){
                v.restoreFocus();
                return;
            }
        }
        propertiesWindow.addTab(v);
        openPropertyView.add(v);
    }

    private void addDocumentView(View view) {
        for(View doc: openDocumentViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restoreFocus();
                return;
            }
        }
        documentsWindow.addTab(view);
        openDocumentViews.add(view);
    }

    private void addExplorerView(View view) {
        for(View doc: openExplorerViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restoreFocus();
                return;
            }
        }
        explorerWindow.addTab(view);
        openExplorerViews.add(view);
    }

    private void addOutputView(View view) {
        for(View doc: openOutputViews){
            if(doc.getTitle().equalsIgnoreCase(view.getTitle()) ){
                doc.restoreFocus();
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
}
