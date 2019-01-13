package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.controller.helper.ControllerState;
import ch.zhaw.wineInventory.controller.helper.ControllerValidation;
import ch.zhaw.wineInventory.service.ProducerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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

	@Autowired
	ProducerService producerService;

	ControllerState controllerState;

	@FXML
	Label id;

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
		// Text field and combo boxes should be readable
		// also in VIEW state.
		name.setStyle("-fx-opacity: 1;");
		changeState(ControllerState.RESET);
	}

	private void create() {
		Object object = persistNew();
		raiseEventSave(object);
		raiseAlertNew(object);

	}

	private void update() {
		Object object = persistExisting();
		raiseEventSave(object);
		raiseAlertUpdate(object);

	};

	void changeState(ControllerState newState) {
		if (controllerState == newState) {
			return;
		}
		System.out.println(String.format("%s => %s", controllerState, newState));

		if (controllerState != null) {
			switch (controllerState) {
			case EDIT:
			case CREATE:
				name.textProperty().addListener((observable, oldValue, newValue) -> {
				});
				break;

			default:
				break;
			}
		}

		controllerState = newState;

		switch (controllerState) {
		case RESET:
			// Buttons
			setResetStateButtons();

			// Text field and combo boxes view
			setResetStateProperties();

			// Text field and combo boxes activation state
			setResetStateActivation();

			break;

		case VIEW:
			// Buttons
			setViewStateButtons();

			setViewStateActivation();
			break;

		case EDIT:
			setEditStateButtons();

			// Text field and combo boxes activation state
			setEditStateActivation();

			setEventListener();
			break;

		case CREATE:
			// Buttons
			setCreateStateButtons();

			// Text field and combo boxes activation state
			setCreateStateActivation();

			// Event listener
			setEventListener();
			break;

		default:
			break;
		}
	}

	void setEventListener() {
		name.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.trim().isEmpty()) {
				save.setDisable(false);
			} else {
				save.setDisable(true);
			}
		});
	};

	void clearFields() {
		setResetStateProperties();
	}

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
			changeState(ControllerState.RESET);
		}

	}

	abstract void deletePersistent(Object object);

	void disableAllFields() {
		setResetStateActivation();

	}

	@FXML
	void edit() {
		if (controllerState == ControllerState.RESET) {
			changeState(ControllerState.CREATE);
		} else if (controllerState == ControllerState.VIEW) {
			changeState(ControllerState.EDIT);
		}
	}

	void enableAllFields() {
		setEditStateActivation();

	}

	String getName() {
		return name.getText();
	}

	abstract Object getPersistent();

	boolean isNew() {
		return (id.getText() == null || id.getText() == "");
	}

	boolean isValid() {
		return validation.emptyValidation("Name", getName().isEmpty());
	}

	abstract Object persistExisting();

	abstract Object persistNew();

	abstract void raiseAlertNew(Object object);

	abstract void raiseAlertUpdate(Object object);

	abstract void raiseEventDelete(Object object);

	abstract void raiseEventSave(Object object);

	@FXML
	void reset() {
		changeState(ControllerState.RESET);
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
		changeState(ControllerState.VIEW);
	};

	void setCreateStateActivation() {
		setEditStateActivation();
	};

	void setCreateStateButtons() {
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
	}

	void setEditStateActivation() {
		name.setDisable(false);
	}

	void setEditStateButtons() {
		if (edit != null) {
			edit.setDisable(true);
		}
		if (save != null) {
			save.setDisable(false);
		}
		if (reset != null) {
			reset.setDisable(false);
		}
		if (delete != null) {
			delete.setDisable(false);
		}
	}

	void setResetStateActivation() {
		name.setDisable(true);
	}

	void setResetStateButtons() {
		if (edit != null) {
			edit.setDisable(false);
		}
		if (save != null) {
			save.setDisable(true);
		}
		if (reset != null) {
			reset.setDisable(true);
		}
		if (delete != null) {
			delete.setDisable(true);
		}
	}

	void setResetStateProperties() {
		id.setText(null);
		name.clear();
	}

	void setViewStateActivation() {
		setResetStateActivation();
	}

	void setViewStateButtons() {
		if (edit != null) {
			edit.setDisable(false);
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
	}

}
