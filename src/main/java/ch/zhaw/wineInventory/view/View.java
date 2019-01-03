package ch.zhaw.wineInventory.view;

import javafx.scene.layout.Pane;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 *
 */

public class View extends Component {	
	//--------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	//--------------------------------------------------------------------------
	
	/**
	 * A view is identified by name.
	 */
	private String name;
	
	/**
	 * The view is stored in the fxml file.
	 */
	private String fxmlFile;

	//--------------------------------------------------------------------------
	// CONSTRUCTORS
	//--------------------------------------------------------------------------		
	public View(String name, String fxmlFile) {
		this.name = name;
		this.fxmlFile = fxmlFile;
	}

	//--------------------------------------------------------------------------
	// METHODS
	//--------------------------------------------------------------------------
	public void addComponentLeft(String name, Component component, boolean splitCurrentComponent) {
		// Delegate to parent view group
		getParentViewGroup().addComponentLeft(name, component, splitCurrentComponent);
	}

	public void addComponentRight(String name, Component component, boolean splitCurrentComponent) {
		// Delegate to parent view group
		getParentViewGroup().addComponentRight(name, component, splitCurrentComponent);
	}
	
	public void addComponentTop(String name, Component component, boolean splitCurrentComponent) {
		// Delegate to parent view group
		getParentViewGroup().addComponentTop(name, component, splitCurrentComponent);
	}
	
	public void addComponentBottom(String name, Component component, boolean splitCurrentComponent) {
		// Delegate to parent view group
		getParentViewGroup().addComponentBottom(name, component, splitCurrentComponent);
	}
	
	public Pane createPane(FxmlLoader loader) {
		Pane pane = (Pane)loader.load(this.fxmlFile);
		return pane;
	}

	//--------------------------------------------------------------------------
	// UTILITY METHODS
	//--------------------------------------------------------------------------
	public String toString(int indent) {
		String spacer = Desktop.createSpacer('-', indent);
		return String.format("\n%sView: %s %s", spacer, getName(), getFxmlFile());
	}
	
	//--------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	//--------------------------------------------------------------------------	
	String getName() {
		return this.name;
	}

	String getFxmlFile() {
		return this.fxmlFile;
	}
}
