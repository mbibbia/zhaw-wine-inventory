package ch.zhaw.wineInventory.controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.event.ChangeWineEvent;
import ch.zhaw.wineInventory.event.ImageDetailsEvent;
import ch.zhaw.wineInventory.event.ResetWineEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

@Controller
public class WineImageDetailController extends MainDetailController {

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
	
	@FXML
	private ImageView imageView;
	

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imageView.setImage(null);
		AnchorPane parent = (AnchorPane) imageView.getParent();
		imageView.fitWidthProperty().bind(parent.widthProperty());
		imageView.fitHeightProperty().bind(parent.heightProperty());
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#deletePersistent(java.lang.Object)
	 */
	@Override
	void deletePersistent(Object object) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#getPersistent()
	 */
	@Override
	Object getPersistent() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistExisting()
	 */
	@Override
	Object persistExisting() {
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistNew()
	 */
	@Override
	Object persistNew() {
		return null;
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertNew(java.lang.Object)
	 */
	@Override
	void raiseAlertNew(Object object) {
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertUpdate(java.lang.Object)
	 */
	@Override
	void raiseAlertUpdate(Object object) {
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventDelete(java.lang.Object)
	 */
	@Override
	void raiseEventDelete(Object object) {
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventSave(java.lang.Object)
	 */
	@Override
	void raiseEventSave(Object object) {
	}

}
