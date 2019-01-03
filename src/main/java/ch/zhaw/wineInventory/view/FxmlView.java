package ch.zhaw.wineInventory.view;

import java.util.ResourceBundle;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 *
 */

public enum FxmlView {

	WINE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("wine.title");

		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Wine.fxml";
		}

	},

	WINETABLE {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("wineTable.title");

		}

		@Override
		public String getFxmlFile() {
			return "/fxml/WineTable.fxml";
		}

	},

	WINEDETAIL {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("wineDetail.title");

		}

		@Override
		public String getFxmlFile() {
			return "/fxml/WineDetail.fxml";
		}

	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}

}
