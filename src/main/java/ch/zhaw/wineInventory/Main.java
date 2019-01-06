package ch.zhaw.wineInventory;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ch.zhaw.wineInventory.config.AppProperties;
import ch.zhaw.wineInventory.config.StageManager;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 *
 *         Main class to start application.
 */
@SpringBootApplication
public class Main extends Application {

	protected ConfigurableApplicationContext springContext;
	protected StageManager stageManager;

	public static void main(final String[] args) {
		Application.launch(args);
	}

	/**
	 * Initializes Spring Context
	 * 
	 */
	@Override
	public void init() throws Exception {
		springContext = springBootApplicationContext();
	}

	/**
	 * Method is called from context, loads Stage Manager and displays initial
	 * scene.
	 * 
	 */
	@Override
	public void start(Stage stage) throws Exception {
		// Explicitly initialize singletons to avoid threading issues.
		AppProperties.init(getClass().getResourceAsStream("/application.properties"));

		stageManager = springContext.getBean(StageManager.class, stage);
		stageManager.init(getClass().getResourceAsStream("/config/desktop.json"));

		displayInitialScene();
	}

	/**
	 * Stops application
	 */
	@Override
	public void stop() throws Exception {
		springContext.close();
	}

	/**
	 * Sets the first Scene to be displayed on startup.
	 * 
	 */
	protected void displayInitialScene() {
		stageManager.displayInitialScene();
	}

	/**
	 * 
	 * @return Configurable Application Context
	 */
	private ConfigurableApplicationContext springBootApplicationContext() {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(Main.class);
		String[] args = getParameters().getRaw().stream().toArray(String[]::new);
		return builder.run(args);
	}

}
