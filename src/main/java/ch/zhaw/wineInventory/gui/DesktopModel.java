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
	/**
	 * Used to store a map of Desktop objects identified by a Desktop name.
	 * The map is initialized from a JSON file.
	 */
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
	/**
	 * Returns the desktop map as a reference, i.e. it would be possible to
	 * change the map.
	 * @return
	 */
	public Map<String, Desktop> getDesktopMap() {
		return this.desktopMap;
	}
}
