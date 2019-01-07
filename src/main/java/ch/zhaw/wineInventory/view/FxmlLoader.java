package ch.zhaw.wineInventory.view;

import javafx.scene.Parent;

/**
 * Interface used in the classes Component, ViewGroup, Desktop.
 * 
 * @author Christian Jeitziner / Marco Bibbia
 *
 */
public interface FxmlLoader {
	/**
	 * Loads a javafx.scene.Parent object from a FXML file.
	 * @param fxmlFilePath
	 * @return
	 */
	Parent load(String fxmlFilePath);
}
