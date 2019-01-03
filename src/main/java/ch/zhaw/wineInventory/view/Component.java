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
	public Component() {
	}

	// --------------------------------------------------------------------------
	// METHODS
	// --------------------------------------------------------------------------
	public abstract void addComponentLeft(String name, Component component, boolean splitCurrentComponent);

	public abstract void addComponentRight(String name, Component component, boolean splitCurrentComponent);

	public abstract void addComponentTop(String name, Component component, boolean splitCurrentComponent);

	public abstract void addComponentBottom(String name, Component component, boolean splitCurrentComponent);

	public abstract Pane createPane(FxmlLoader loader);

	// --------------------------------------------------------------------------
	// UTILITY METHODS
	// --------------------------------------------------------------------------
	public String toString() {
		return toString(0);
	}

	abstract public String toString(int indent);

	// --------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	// --------------------------------------------------------------------------
	ViewGroup getParentViewGroup() {
		return this.parentViewGroup;
	}

	void setParentViewGroup(ViewGroup parentViewGroup) {
		this.parentViewGroup = parentViewGroup;
	}
}
