package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.controller.helper.ControllerState;
import ch.zhaw.wineInventory.event.ChangeEntityEventType;
import ch.zhaw.wineInventory.event.ChangeWineTypeEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import ch.zhaw.wineInventory.event.WineTypeDetailsEvent;
import ch.zhaw.wineInventory.service.WineTypeService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View WineTypeDetail.fxml.
 *
 */

@Controller
public class WineTypeDetailController extends MainDetailController {

	/*
	 * Event handler to display an object in the view.
	 */
	@Component
	class WineTypeDetailEventHandler implements ApplicationListener<WineTypeDetailsEvent> {

		@Override
		public void onApplicationEvent(WineTypeDetailsEvent event) {
			id.setText(Long.toString(event.getWineType().getId()));
			name.setText(event.getWineType().getName());
			
			changeState(ControllerState.VIEW);
		}

	}
	
	/*
	 * Event handler for a saved or deleted object.
	 */
	@Component
	class ChangeWineTypeEventHandler implements ApplicationListener<ChangeWineTypeEvent> {

		@Override
		public void onApplicationEvent(ChangeWineTypeEvent event) {
			switch (event.getChangeType()) {
			case SAVE:
				WineTypeDetailsEvent wineTypeDetailsEvent = new WineTypeDetailsEvent(this, event.getWineType());
				applicationEventPublisher.publishEvent(wineTypeDetailsEvent);
				break;
			case DELETE:
				reset();
				break;
			
			default:
				break;
			}
		}

	}
	
	@Autowired
	private WineTypeService wineTypeService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#deletePersistent(java.lang.Object)
	 */
	@Override
	void deletePersistent(Object object) {
		wineTypeService.delete((WineType) object);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#getPersistent()
	 */
	@Override
	Object getPersistent() {
		return wineTypeService.find(Long.parseLong(id.getText()));
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistExisting()
	 */
	@Override
	Object persistExisting() {
		WineType wineType = (WineType) getPersistent();
		wineType.setName(getName());
		return wineTypeService.save(wineType);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistNew()
	 */
	@Override
	Object persistNew() {
		WineType wineType = new WineType();
		wineType.setName(getName());
		return wineTypeService.save(wineType);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertNew(java.lang.Object)
	 */
	@Override
	void raiseAlertNew(Object object) {
		WineType wineType = (WineType) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine Type saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The wine type " + wineType.getName() + " has been created and \n id is " + wineType.getId() + ".");
		alert.showAndWait();
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertUpdate(java.lang.Object)
	 */
	@Override
	void raiseAlertUpdate(Object object) {
		WineType wineType = (WineType) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine type updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine type " + wineType.getName() + " has been updated.");
		alert.showAndWait();
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventDelete(java.lang.Object)
	 */
	@Override
	void raiseEventDelete(Object object) {
		ChangeWineTypeEvent wineTypeEvent = new ChangeWineTypeEvent(this,
																	null,
																	ChangeEntityEventType.DELETE);
		applicationEventPublisher.publishEvent(wineTypeEvent);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventSave(java.lang.Object)
	 */
	@Override
	void raiseEventSave(Object object) {
		ChangeWineTypeEvent wineTypeEvent = new ChangeWineTypeEvent(this,
				                                                    (WineType) object,
				                                                    ChangeEntityEventType.SAVE);
		applicationEventPublisher.publishEvent(wineTypeEvent);
	}

}
