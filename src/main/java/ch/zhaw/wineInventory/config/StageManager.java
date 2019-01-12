package ch.zhaw.wineInventory.config;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.InputStream;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ch.zhaw.wineInventory.gui.DesktopController;
import ch.zhaw.wineInventory.gui.DesktopModel;
import ch.zhaw.wineInventory.gui.DesktopView;
import ch.zhaw.wineInventory.view.FxmlLoaderImpl;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 *
 */
public class StageManager {

	/**
	 * Used for logging. 
	 */	
	private static final Logger LOG = getLogger(StageManager.class);
	
	/**
	 * All scenes (instances of Scene) are shown on this stage.
	 */
	private final Stage primaryStage;

	/**
	 * Used to load a view from a FXML file.
	 */
	@Autowired
	private final SpringFXMLLoader springFXMLLoader;

	/**
	 * A desktop controller is responsible for creating the main view which consists
	 * of a menu bar and a view. The view is initialized from a JSON file
	 * (desktop.json).
	 */
	private DesktopController desktopController;

	/**
	 * Constructor which needs a FXML loader and a stage as parameters.
	 * @param springFXMLLoader
	 * @param stage
	 */
	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
	}

	/**
	 * @param is : The input stream is used to initialize the desktop model from a
	 *           json file.
	 */
	public void init(InputStream is) {
		LOG.info("Intialize StageManager");
		this.desktopController = new DesktopController(new DesktopModel(is), new DesktopView(), this,
				new FxmlLoaderImpl(springFXMLLoader));
	}

	/**
	 * Used in Main.java to display the first scene.
	 * Implemented in the DesktopController class.
	 */
	public void displayInitialScene() {
		this.desktopController.displayInitialScene();
	}

	/**
	 * Used by the desktop controller to switch to another scene,
	 * receives a Pane object and the title for the scene.
	 * @param pane
	 * @param sceneTitle
	 */
	public void switchScene(Pane pane, String sceneTitle) {
		show(pane, sceneTitle);
	}

	/**
	 * Private method to assign a scene to the primary stage 
	 * and to show the stage.
	 * @param rootnode
	 * @param title
	 */
	private void show(final Parent rootnode, String title) {
		Scene scene = prepareScene(rootnode);

		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();

		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + title, exception);
		}
	}

	/**
	 * Private method which receives a Parent object and connects
	 * the Parent to a scene.
	 * @param rootnode: instance of Parent
	 * @return a scene object
	 */
	private Scene prepareScene(Parent rootnode) {
		Scene scene = primaryStage.getScene();

		if (scene == null) {
			scene = new Scene(rootnode);
		}
		scene.setRoot(rootnode);
		return scene;
	}

	/**
	 * Called when the primary stage cannot be shown and the application
	 * then exists.
	 * @param errorMsg
	 * @param exception
	 */
	private void logAndExit(String errorMsg, Exception exception) {
		LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}

}