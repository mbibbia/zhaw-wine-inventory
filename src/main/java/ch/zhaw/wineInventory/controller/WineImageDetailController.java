package ch.zhaw.wineInventory.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.event.ChangeWineEvent;
import ch.zhaw.wineInventory.event.ImageDetailsEvent;
import ch.zhaw.wineInventory.event.ResetWineEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

@Controller
public class WineImageDetailController implements Initializable {

	@FXML
	private ImageView imageView;

	@Lazy
	@Autowired
	private StageManager stageManager;

	/*
	 * Event handler to display an object in the view.
	 */
	@Component
	class WineDetailEventHandler implements ApplicationListener<WineDetailsEvent> {
		@Override
		public void onApplicationEvent(WineDetailsEvent event) {
			imageView.setImage(null);
			if (event.getWine().getImage() != null) {
				imageView.setImage(new Image(new ByteArrayInputStream(event.getWine().getImage().getData())));
			}

		}
	}

	/*
	 * Event handler to display an object in the view.
	 */
	@Component
	class WineImageDetailsEventHandler implements ApplicationListener<ImageDetailsEvent> {
		@Override
		public void onApplicationEvent(ImageDetailsEvent event) {
			imageView.setImage(null);
			if (event.getImage() != null) {
				imageView.setImage(new Image(new ByteArrayInputStream(event.getImage().getData())));
			}

		}
	}

	/*
	 * Event handler for a saved or deleted object.
	 */
	@Component
	class ChangeWineEventHandler implements ApplicationListener<ChangeWineEvent> {

		@Override
		public void onApplicationEvent(ChangeWineEvent event) {
			
			imageView.setImage(null);
			Wine wine = event.getWine();
			if (wine != null && wine.getImage() != null) {
				imageView.setImage(new Image(new ByteArrayInputStream(event.getWine().getImage().getData())));
			} else {
				imageView.setImage(null);
			}
		}

	}

	/*
	 * Event handler when the dialog is reset.
	 */
	@Component
	class ResetWineEventHandler implements ApplicationListener<ResetWineEvent> {

		@Override
		public void onApplicationEvent(ResetWineEvent event) {
			
			imageView.setImage(null);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.setImage(null);
		AnchorPane parent = (AnchorPane) imageView.getParent();
		imageView.fitWidthProperty().bind(parent.widthProperty());
		imageView.fitHeightProperty().bind(parent.heightProperty());
	}

}
