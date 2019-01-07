package ch.zhaw.wineInventory.view;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ch.zhaw.wineInventory.config.SpringFXMLLoader;
import javafx.scene.Parent;

/**
 * Implements the interface FxmlLoader.
 * 
 * @author Christian Jeitziner / Marco Bibbia
 *
 */

public class FxmlLoaderImpl implements FxmlLoader {
	
	/**
	 * Used for logging. 
	 */
	private static final Logger LOG = getLogger(FxmlLoaderImpl.class);
	
	/**
	 * FxmlLoaderImpl delegates the loading of FXML files to an instance of the 
	 * SpringFXMLLoader class.
	 */
	@Autowired
	private final SpringFXMLLoader springFXMLLoader;
	
	/** Constructor which receives a SpringFXMLLoader instance.
	 * @param springFXMLLoader
	 */
	public FxmlLoaderImpl(SpringFXMLLoader springFXMLLoader) {
		this.springFXMLLoader = springFXMLLoader;
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.view.FxmlLoader#load(java.lang.String)
	 */
	@Override
	public Parent load(String fxmlFilePath) {
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			if (rootNode == null) {
			LOG.error("Unable to load FXML view: " + fxmlFilePath);}
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			LOG.error("Exception while trying to load FXML view: " + fxmlFilePath, exception);
		}
		return rootNode;
	}
}
