package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.event.ClassificationDetailsEvent;
import ch.zhaw.wineInventory.event.ClassificationSaveEvent;
import ch.zhaw.wineInventory.service.ClassificationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	@Component
	class ShowClassificationDetailEventHandler implements ApplicationListener<ClassificationDetailsEvent> {

		@Override
		public void onApplicationEvent(ClassificationDetailsEvent event) {
			classificationId.setText(Long.toString(event.getClassification().getId()));
			name.setText(event.getClassification().getName());
		}

	}

	@FXML
	private Label classificationId;

	@FXML
	private TextField name;

	@FXML
	private Button reset;

	@FXML
	private Button saveClassification;

	@Autowired
	private ClassificationService classificationService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private String getName() {
		return name.getText();
	}

	@Override
	void clearFields() {

		classificationId.setText(null);
		name.clear();

	}

	@Override
	boolean isNew() {
		return (classificationId.getText() == null || classificationId.getText() == "");
	}

	@Override
	boolean isValid() {
		return validation.emptyValidation("Name", getName().isEmpty());
	}

	@Override
	Object persistExisting() {
		Classification classification = classificationService.find(Long.parseLong(classificationId.getText()));
		classification.setName(getName());
		return classificationService.update(classification);
	}

	@Override
	Object persistNew() {
		Classification classification = new Classification();
		classification.setName(getName());
		return classificationService.save(classification);

	}

	@Override
	void raiseCreateAlert(Object object) {
		Classification classification = (Classification) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Classification saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been created and \n id is "
				+ classification.getId() + ".");
		alert.showAndWait();

	}

	@Override
	void raiseSaveEvent(Object object) {
		Classification classification = (Classification) object;
		ClassificationSaveEvent classificationEvent = new ClassificationSaveEvent(this, classification);
		applicationEventPublisher.publishEvent(classificationEvent);

	}

	@Override
	void raiseUpdateAlert(Object object) {
		Classification classification = (Classification) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Classification updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been updated.");
		alert.showAndWait();

	}

	@Override
	void reset() {
		clearFields();
	}

}
