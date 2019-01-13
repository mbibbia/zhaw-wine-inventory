package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.controller.validation.ControllerValidation;
import ch.zhaw.wineInventory.event.ClassificationDetailsEvent;
import ch.zhaw.wineInventory.event.ClassificationSaveEvent;
import ch.zhaw.wineInventory.service.ClassificationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class ClassificationDetailController implements Initializable {

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

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private ControllerValidation validation;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private void clearFields() {

		classificationId.setText(null);
		name.clear();

	}

	private String getName() {
		return name.getText();
	}

	private void raiseEventSaveClassification(final Classification classification) {
		ClassificationSaveEvent classificationEvent = new ClassificationSaveEvent(this, classification);
		applicationEventPublisher.publishEvent(classificationEvent);
	}

	private void saveAlert(Classification classification) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been created and \n id is "
				+ classification.getId() + ".");
		alert.showAndWait();
	}

	@FXML
	private void saveClassification(ActionEvent event) {

		if (validation.emptyValidation("Name", getName().isEmpty())) {

			if (classificationId.getText() == null || classificationId.getText() == "") {

				Classification classification = new Classification();
				classification.setName(getName());

				Classification newClassification = classificationService.save(classification);

				saveAlert(newClassification);

				raiseEventSaveClassification(newClassification);

			} else {
				Classification classification = classificationService.find(Long.parseLong(classificationId.getText()));
				classification.setName(getName());
				Classification updatedClassification = classificationService.update(classification);
				updateAlert(updatedClassification);

				raiseEventSaveClassification(updatedClassification);
			}

			clearFields();

		}

	}

	private void updateAlert(Classification classification) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The classification " + classification.getName() + " has been updated.");
		alert.showAndWait();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

}
