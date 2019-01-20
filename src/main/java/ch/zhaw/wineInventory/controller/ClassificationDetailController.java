package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.controller.helper.ControllerState;
import ch.zhaw.wineInventory.event.ClassificationDetailsEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import ch.zhaw.wineInventory.event.ChangeClassificationEvent;
import ch.zhaw.wineInventory.event.ChangeEntityEventType;
import ch.zhaw.wineInventory.event.ChangeWineEvent;
import ch.zhaw.wineInventory.service.ClassificationService;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View ClassificationDetail.fxml.
 *
 */

@Controller
public class ClassificationDetailController extends MainDetailController {

	/*
	 * Event handler to display an object in the view.
	 */
	@Component
	class ShowClassificationDetailEventHandler implements ApplicationListener<ClassificationDetailsEvent> {

		@Override
		public void onApplicationEvent(ClassificationDetailsEvent event) {
			id.setText(Long.toString(event.getClassification().getId()));
			name.setText(event.getClassification().getName());
			
			changeState(ControllerState.VIEW);
		}

	}
	
	/*
	 * Event handler for a saved or deleted object.
	 */
	@Component
	class ChangeClassificationEventHandler implements ApplicationListener<ChangeClassificationEvent> {

		@Override
		public void onApplicationEvent(ChangeClassificationEvent event) {
			switch (event.getChangeType()) {
			case SAVE:
				ClassificationDetailsEvent wineEvent = new ClassificationDetailsEvent(this, event.getClassification());
				applicationEventPublisher.publishEvent(wineEvent);
				break;
			case DELETE:
				reset();
				break;
			default:
				reset();
				break;
			}
		}
	}
	

	@Autowired
	private ClassificationService classificationService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#deletePersistent(java.lang.Object)
	 */
	@Override
	void deletePersistent(Object object) {
		classificationService.delete((Classification) object);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#getPersistent()
	 */
	@Override
	Object getPersistent() {
		return classificationService.find(Long.parseLong(id.getText()));
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistExisting()
	 */
	@Override
	Object persistExisting() {
		Classification classification = (Classification) getPersistent();
		classification.setName(getName());
		return classificationService.update(classification);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistNew()
	 */
	@Override
	Object persistNew() {
		Classification classification = new Classification();
		classification.setName(getName());
		return classificationService.save(classification);

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertNew(java.lang.Object)
	 */
	@Override
	void raiseAlertNew(Object object) {
		Classification classification = (Classification) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Classification saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been created and \n id is "
				+ classification.getId() + ".");
		alert.showAndWait();

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertUpdate(java.lang.Object)
	 */
	@Override
	void raiseAlertUpdate(Object object) {
		Classification classification = (Classification) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Classification updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been updated.");
		alert.showAndWait();

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventDelete(java.lang.Object)
	 */
	@Override
	void raiseEventDelete(Object object) {
		ChangeClassificationEvent classificationEvent = new ChangeClassificationEvent(this,
																					  null,
																					  ChangeEntityEventType.DELETE);
		applicationEventPublisher.publishEvent(classificationEvent);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventSave(java.lang.Object)
	 */
	@Override
	void raiseEventSave(Object object) {
		ChangeClassificationEvent classificationEvent = new ChangeClassificationEvent(this,
			                                                                          (Classification) object,
			                                                                          ChangeEntityEventType.SAVE);
		applicationEventPublisher.publishEvent(classificationEvent);
	}

}
