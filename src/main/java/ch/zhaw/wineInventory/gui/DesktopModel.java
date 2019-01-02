package ch.zhaw.wineInventory.gui;

import java.io.InputStream;
import java.util.Map;

import ch.zhaw.wineInventory.view.Desktop;

/**
 * @author Christian Jeitziner / Marco Bibbia
 *
 */
public class DesktopModel {
	// --------------------------------------------------------------------------
	// INSTANCE AND CLASS VARIABLES.
	// --------------------------------------------------------------------------
	private Map<String, Desktop> desktopMap;

	// --------------------------------------------------------------------------
	// CONSTRUCTORS
	// --------------------------------------------------------------------------

	/**
	 * @param is InputStream which identifies a JSON file desktop.json where the
	 *           desktop configuration is stored.
	 */
	public DesktopModel(InputStream is) {
		this.desktopMap = Desktop.getDesktopsFromInputStream(is);
	}

	// --------------------------------------------------------------------------
	// GETTER AND SETTER METHODS
	// --------------------------------------------------------------------------
	public Map<String, Desktop> getDesktopMap() {
		return this.desktopMap;
	}
}
