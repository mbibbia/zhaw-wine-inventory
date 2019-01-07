package ch.zhaw.wineInventory.gui;

import java.util.HashMap;
import java.util.Map;

import ch.zhaw.wineInventory.view.Desktop;
import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

/**
 * Used to create the main view for the application, i.e a menu bar and a number 
 * of sub views.
 * @author Christian Jeitziner / Marco Bibbia
 *
 */
public class DesktopView {
	//--------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	//--------------------------------------------------------------------------
	/**
	 * Back reference to the controller object. 
	 */
	private DesktopController controller;
	
	/**
	 * A map of desktopMenuItems, used to set or clear the selection state in the
	 * desktop menu item.
	 */
	private Map<String,RadioMenuItem> desktopMenuMap;

	//--------------------------------------------------------------------------
	// CONSTRUCTORS
	//--------------------------------------------------------------------------
	/**
	 * DesktopView Constructor, creates a empty desktopMenuMap. 
	 */
	public DesktopView() {
		this.desktopMenuMap = new HashMap<String,RadioMenuItem>();
	}
	
	//--------------------------------------------------------------------------
	// METHODS
	//--------------------------------------------------------------------------
	/**
	 * Set a desktop menu item to the selected state.
	 * @param desktopName
	 */
	public void activateDesktopMenuItem(String desktopName) {
		RadioMenuItem desktopMenuItem = this.desktopMenuMap.getOrDefault(desktopName, null);
		if (desktopMenuItem != null) {
			desktopMenuItem.setSelected(true);
		}
	}

	/**
	 * Initializes the Desktop menu item.
	 * @param desktopMap map of Desktop objects
	 * @return Menu
	 */
	private Menu desktopMenu(Map<String, Desktop> desktopMap) {
		Menu menu = new Menu("Desktop");
		
		ToggleGroup group = new ToggleGroup();
		for (String name: desktopMap.keySet()) {
			RadioMenuItem desktopMenuItem = new RadioMenuItem(name);
		    menu.getItems().add(desktopMenuItem);
		    group.getToggles().add(desktopMenuItem);
		    this.desktopMenuMap.put(name, desktopMenuItem);
		    setDesktopEventHandler(desktopMenuItem, name);
		}
	    return menu;
	}

	/**
	 * Initializes the file menu.
	 * @return Menu
	 */
	private Menu fileMenu() {
		Menu desktopMenu = new Menu("File");
	    MenuItem exitMenuItem = new MenuItem("Exit");
	    desktopMenu.getItems().addAll(exitMenuItem);
	    setFileExitEventHandler(exitMenuItem);
	    return desktopMenu;
	}

	/**
	 * Initializes the menu bar
	 * @param desktopMap: used to create the Desktop menu items.
	 * @return MenuBar
	 */
	public MenuBar menuBar(Map<String, Desktop> desktopMap) {
		MenuBar menuBar = new MenuBar();
	    menuBar.getMenus().addAll(fileMenu(), desktopMenu(desktopMap));
	    return menuBar;
	}

	/**
	 * Set radioMenuItem onAction event handler for switching desktops.
	 * @param desktopMenuItem: the radioMenuItem
	 * @param desktopName: the name of the desktop which is activated
	 *        when the menu item is selected.
	 */
	public void setDesktopEventHandler(RadioMenuItem desktopMenuItem,
									   String desktopName) {
		desktopMenuItem.setOnAction(e->controller.changeScene(desktopName));
	}

	/**
	 * Event handler to exist the application.
	 * @param menuItem: the Exit menu item
	 */
	public void setFileExitEventHandler(MenuItem menuItem) {
		menuItem.setOnAction(e->Platform.exit());
	}

	
	//--------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	//--------------------------------------------------------------------------		
	/** Method to connect the view to the controller.
	 * @param controller: DesktopController instance
	 */
	public void setController(DesktopController controller) {
		this.controller = controller;
	}

}
