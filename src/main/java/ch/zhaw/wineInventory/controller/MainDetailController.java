package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.controller.validation.ControllerValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

@Controller
abstract class MainDetailController implements Initializable {

	@Lazy
	@Autowired
	StageManager stageManager;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	ControllerValidation validation;

	@FXML
	TextField name;

	@FXML
	Button reset;

	@FXML
	Button edit;

	@FXML
	Button delete;

	@FXML
	Button save;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeDefaultButtonState();
		initializeButtonHandler();
	}

	private void create() {
		Object object = persistNew();
		raiseAlertNew(object);
		raiseEventSave(object);

	}

	private void initializeButtonHandler() {
		name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.trim().isEmpty()) {
				save.setDisable(false);
			} else {
				save.setDisable(true);
			}
		});
	}

	private void initializeDefaultButtonState() {
		if (edit != null) {
			edit.setDisable(true);
		}
		if (save != null) {
			save.setDisable(true);
		}
		if (reset != null) {
			reset.setDisable(false);
		}
		if (delete != null) {
			delete.setDisable(true);
		}
	};

	private void update() {
		Object object = persistExisting();
		raiseAlertUpdate(object);
		raiseEventSave(object);

	};

	abstract void clearFields();

	@FXML
	void delete() {

		Object object = getPersistent();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			deletePersistent(object);
			raiseEventDelete(object);
		}

	}

	abstract void deletePersistent(Object object);

	abstract void disableAllFields();

	abstract void enableAllFields();

	abstract Object getPersistent();

	abstract boolean isNew();

	abstract boolean isValid();

	abstract Object persistExisting();

	abstract Object persistNew();

	abstract void raiseAlertNew(Object object);

	abstract void raiseAlertUpdate(Object object);

	abstract void raiseEventDelete(Object object);

	abstract void raiseEventSave(Object object);

	@FXML
	void edit() {
		enableAllFields();
	}

	@FXML
	void reset() {
		clearFields();
	}

	@FXML
	void save() {
		if (isValid()) {
			if (isNew()) {
				create();
			} else {
				update();
			}
		}
		clearFields();
	}

}
