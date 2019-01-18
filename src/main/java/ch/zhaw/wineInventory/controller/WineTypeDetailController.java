package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.controller.helper.ControllerState;
import ch.zhaw.wineInventory.event.ChangeWineTypeEvent;
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

	@Component
	class ShowWineTypeDetailEventHandler implements ApplicationListener<WineTypeDetailsEvent> {

		@Override
		public void onApplicationEvent(WineTypeDetailsEvent event) {
			id.setText(Long.toString(event.getWineType().getId()));
			name.setText(event.getWineType().getName());
			
			changeState(ControllerState.VIEW);
		}

	}

	@Autowired
	private WineTypeService wineTypeService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	@Override
	void deletePersistent(Object object) {
		// TODO Auto-generated method stub

		// WineTypeDeleteEvent wineTypeEvent = new
		// WineTypeDeleteEvent(this, (WineType) object);
		// applicationEventPublisher.publishEvent(wineTypeEvent);

	}

	@Override
	Object getPersistent() {
		return wineTypeService.find(Long.parseLong(id.getText()));
	}

	@Override
	Object persistExisting() {
		WineType wineType = (WineType) getPersistent();
		wineType.setName(getName());
		return wineTypeService.save(wineType);
	}

	@Override
	Object persistNew() {
		WineType wineType = new WineType();
		wineType.setName(getName());
		return wineTypeService.save(wineType);
	}

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

	@Override
	void raiseAlertUpdate(Object object) {
		WineType wineType = (WineType) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine type updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine type " + wineType.getName() + " has been updated.");
		alert.showAndWait();

	}

	@Override
	void raiseEventDelete(Object object) {
		// TODO Auto-generated method stub

		// WineTypeDeleteEvent wineTypeEvent = new
		// WineTypeDeleteEvent(this, (WineType) object);
		// applicationEventPublisher.publishEvent(wineTypeEvent);

	}

	@Override
	void raiseEventSave(Object object) {
		ChangeWineTypeEvent wineTypeEvent = new ChangeWineTypeEvent(this, (WineType) object);
		applicationEventPublisher.publishEvent(wineTypeEvent);

	}

}
