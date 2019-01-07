package ch.zhaw.wineInventory.view;

import javafx.scene.layout.Pane;

public abstract class Component {
	// --------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	// --------------------------------------------------------------------------

	/**
	 * parentViewGroup points back to the parent of this component.
	 */
	private ViewGroup parentViewGroup;

	// --------------------------------------------------------------------------
	// CONSTRUCTORS
	// --------------------------------------------------------------------------
	
	/**
	 * Protected constructor, called from derived classes, member parentViewGroup 
	 * is initialized later on.
	 */
	protected Component() {
	}

	// --------------------------------------------------------------------------
	// METHODS
	// --------------------------------------------------------------------------
	/** Used to add a component (identified by name) to the left of the current 
	 * component (this object) . If splitCurrentComponent is true, a ViewGroup
	 * is created and the new component and the current component are
	 * added to the ViewGroup.
	 * If splitCurrentComponent is false, again a ViewGroup is created,
	 * but now the new component and all existing components are added to the
	 * viewGroup.
	 * 
	 * @param name - name of the new component
	 * @param component - new component
	 * @param splitCurrentComponent - boolean, see description.
	 */
	public abstract void addComponentLeft(String name,
			                              Component component,
			                              boolean splitCurrentComponent);

	/** Used to add a component (identified by name) to the left of the current 
	 * component (this object) . If splitCurrentComponent is true, a ViewGroup
	 * is created and the new component and the current component are
	 * added to the ViewGroup.
	 * If splitCurrentComponent is false, again a ViewGroup is created,
	 * but now the new component and all existing components are added to the
	 * viewGroup.
	 * 
	 * @param name - name of the new component
	 * @param component - new component
	 * @param splitCurrentComponent - boolean, see description.
	 */
	public abstract void addComponentRight(String name,
			                               Component component,
			                               boolean splitCurrentComponent);

	/** Used to add a component (identified by name) to the left of the current 
	 * component (this object) . If splitCurrentComponent is true, a ViewGroup
	 * is created and the new component and the current component are
	 * added to the ViewGroup.
	 * If splitCurrentComponent is false, again a ViewGroup is created,
	 * but now the new component and all existing components are added to the
	 * viewGroup.
	 * 
	 * @param name - name of the new component
	 * @param component - new component
	 * @param splitCurrentComponent - boolean, see description.
	 */
	public abstract void addComponentTop(String name,
			                             Component component,
			                             boolean splitCurrentComponent);

	/** Used to add a component (identified by name) to the left of the current 
	 * component (this object) . If splitCurrentComponent is true, a ViewGroup
	 * is created and the new component and the current component are
	 * added to the ViewGroup.
	 * If splitCurrentComponent is false, again a ViewGroup is created,
	 * but now the new component and all existing components are added to the
	 * viewGroup.
	 * 
	 * @param name - name of the new component
	 * @param component - new component
	 * @param splitCurrentComponent - boolean, see description.
	 */
	public abstract void addComponentBottom(String name,
			                                Component component, 
			                                boolean splitCurrentComponent);

	/**
	 * Abstract method which returns a Pane object created by means of a FXML
	 * loader.
	 * @param {FxmlLoader} loader
	 * @return {Pane}
	 */
	public abstract Pane createPane(FxmlLoader loader);

	// --------------------------------------------------------------------------
	// UTILITY METHODS
	// --------------------------------------------------------------------------
	public String toString() {
		return toString(0);
	}

	/**
	 * Special toString method which takes a integer as a parameter to left-adjust
	 * the returned string with a special character (e.g. dash or blank) for 
	 * better readability when an entire desktop object is printed.
	 * @param {integer} indent
	 * @return
	 */
	abstract public String toString(int indent);

	// --------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	// --------------------------------------------------------------------------
	/**
	 * Returns the parent of this component, can be null or a ViewGroup.
	 * @return {ViewGroup}
	 */
	ViewGroup getParentViewGroup() {
		return this.parentViewGroup;
	}

	/**
	 * Sets the parent view group.
	 * @param parentViewGroup
	 */
	void setParentViewGroup(ViewGroup parentViewGroup) {
		this.parentViewGroup = parentViewGroup;
	}
}
