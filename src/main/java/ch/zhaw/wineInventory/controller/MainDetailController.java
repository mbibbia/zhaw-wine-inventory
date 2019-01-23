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

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Abstract base class for all DetailControllers.
 *
 */
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

	/**
	 * Tracks the state of the view, used to enable/disable the edit, save
	 * reset and save buttons. Also used to enable/disable all other controls
	 * on the associated view.
	 * RESET:  The view is in its initial state, all controls empty and disabled.
	 * VIEW:   A database entity is displayed in the view, controls are populated
	 *         but disabled.
	 * EDIT:   Same as VIEW, but the controls are enabled.
	 * CREATE: No database object is loaded, the controls can be edited, a new
	 *         database entity can be created.
	 */
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
	
	private boolean nameValid = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeEventListenersForSaveButton();
		initializeInputControlsStyle();
		changeState(ControllerState.RESET);
	}
	
	/**
	 * Called when a new entity is created. in the database.
	 */
	private void create() {
		Object object = persistNew();
		raiseEventSave(object);
		raiseAlertNew(object);
	}
	
	/**
	 * Called when an existing database entity is updated.
	 */
	private void update() {
		Object object = persistExisting();
		raiseEventSave(object);
		raiseAlertUpdate(object);
	}
	
	/**
	 * Called when this controller enters a new state. 
	 * @param newState
	 */
	void changeState(ControllerState newState) {
		if (controllerState == newState) {
			return;
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

			// Text field and combo boxes view
			setViewStateProperties();

			// Text field and combo boxes activation state
			setViewStateActivation();

			break;

		case EDIT:
			setEditStateButtons();

			// Text field and combo boxes view
			setEditStateProperties();

			// Text field and combo boxes activation state
			setEditStateActivation();

			break;

		case CREATE:
			// Buttons
			setCreateStateButtons();

			// Text field and combo boxes view
			setCreateStateProperties();

			// Text field and combo boxes activation state
			setCreateStateActivation();

			break;

		default:
			break;
		}
	}
	
	/**
	 * To delete a database entity.
	 */
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

	
	/**
	 * Used to delete an entity from the database, needs to be implemented
	 * in the derived classes.
	 * @param object : The object that should be deleted.
	 */
	abstract void deletePersistent(Object object);

	/**
	 * Called when entering the EDIT mode of the controller.
	 */
	@FXML
	void edit() {
		if (controllerState == ControllerState.RESET) {
			changeState(ControllerState.CREATE);
		} else if (controllerState == ControllerState.VIEW) {
			changeState(ControllerState.EDIT);
		}
	};

	/**
	 * @return the content of the name text field.
	 */
	String getName() {
		return name.getText();
	}

	/**
	 * @return a database entity.
	 */
	abstract Object getPersistent();

	boolean getSaveButtonValidState() {
		return nameValid;
	}

	/**
	 * To initialize an event listener for the name text field.
	 * Used to enable/disable the Save button.
	 */
	void initializeEventListenersForSaveButton() {
		name.textProperty().addListener(
			(observable, oldValue, newValue) -> {
				if (!newValue.trim().isEmpty()) {
					nameValid = true;
				} else {
					nameValid = false;
				}
				updateSaveButton();
			}
		);
	}

	/**
	 * To initialize the appearance of the text fields and combo boxes.
	 * Text field and combo boxes should be readable also in VIEW state.
	 */
	void initializeInputControlsStyle() {
		name.setStyle("-fx-opacity: 1;");
	}

	/**
	 * Helper function to determine whether we deal with a new database entity.
	 * @return true, if the database entity does not yet exist.
	 */
	boolean isNew() {
		return (id.getText() == null || id.getText() == "");
	}

	/**
	 * Helper function to check if input controls have a valid value.
	 * @return true, if the input controls have a valid value.
	 */
	boolean isValid() {
		return validation.emptyValidation("Name", getName().isEmpty());
	}

	/** Used when an existing database entity is saved.
	 * @return the database entity
	 */
	abstract Object persistExisting();

	/**
	 * Used when a new database entity is saved.
	 * @return the database entity.
	 */
	abstract Object persistNew();

	/** Opens a dialog to inform the user that a new database entity was
	 * created.
	 * @param object : the new database entity
	 */
	abstract void raiseAlertNew(Object object);

	/** Opens a dialog to inform the user that a database entity was
	 * updated.
	 * @param object : the changed database entity
	 */
	abstract void raiseAlertUpdate(Object object);

	/** Raises a event to signal that a database object was deleted.
	 * @param object : the deleted database entity
	 */
	abstract void raiseEventDelete(Object object);

	/** Raises a event to signal that a database object was saved.
	 * @param object : the deleted database entity
	 */
	abstract void raiseEventSave(Object object);

	/**
	 * Method that is called when the reset button is pressed.
	 */
	@FXML
	void reset() {
		changeState(ControllerState.RESET);
	}

	/**
	 * Method that is called when the save button is pressed.
	 */
	@FXML
	void save() {
		if (isValid()) {
			if (isNew()) {
				create();
			} else {
				update();
			}
			changeState(ControllerState.VIEW);
		}
	}

	/**
	 * Called when the controller enters the CREATE state. Used to enable the 
	 * controls on the view (not Edit, Save, Reset and Delete buttons).
	 */
	void setCreateStateActivation() {
		setInputControlsDisabled(false);
	};

	/**
	 * Called when the controller enters the CREATE state. Used to enable the
	 * Edit, Save, Reset and Delete buttons.
	 */
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

	/**
	 * Called when the controller enters the CREATE state. Used to set the
	 * content of the controls.
	 */
	void setCreateStateProperties() {
	}
	
	/**
	 * Called when the controller enters the EDIT state. Used to enable the 
	 * controls on the view (not Edit, Save, Reset and Delete buttons).
	 */
	void setEditStateActivation() {
		setInputControlsDisabled(false);
	};

	/**
	 * Called when the controller enters the EDIT state. Used to enable/disable the
	 * Edit, Save, Reset and Delete buttons.
	 */
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

	/**
	 * Called when the controller enters the EDIT state. Used to set the
	 * content of the controls.
	 */
	void setEditStateProperties() {
	};

	/**
	 * Called when the controller enters the EDIT state. Used to disable the 
	 * controls on the view (not Edit, Save, Reset and Delete buttons).
	 */
	void setResetStateActivation() {
		setInputControlsDisabled(true);
	}

	/**
	 * Called when the controller enters the RESET state. Used to disable the
	 * Edit, Save, Reset and Delete buttons.
	 */
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

	/**
	 * Called when the controller enters the RESET state. Used to set the
	 * content of the controls.
	 */
	void setResetStateProperties() {
		setInputControlsCleared();
	}

	/**
	 * Called when the controller enters the VIEW state. Used to disable the 
	 * controls on the view (not Edit, Save, Reset and Delete buttons).
	 */
	void setViewStateActivation() {
		setInputControlsDisabled(true);
	}

	/**
	 * Called when the controller enters the CREATE state. Used to enable the
	 * Edit, Save, Reset and Delete buttons.
	 */
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

	/**
	 * Called when the controller enters the VIEW state. Used to set the
	 * content of the controls.
	 */
	void setViewStateProperties() {
		setInputControlsViewState();
	}
	
	/**
	 * Disables the save button in RESET and VIEW state, enables the save
	 * button depending on the method getSaveButtonValidŜtate which can be 
	 * re-implemented in derived classes.
	 */
	void updateSaveButton() {
		if (controllerState == ControllerState.RESET || 
			controllerState == ControllerState.VIEW) {
			save.setDisable(true);
		} else {
			save.setDisable(!getSaveButtonValidState());
		}
	}
	
	/**
	 * Clear all input controls, needs to be re-implemented in derived classes.
	 */
	void setInputControlsCleared() {
		id.setText(null);
		name.clear();
	}

	/** Enables/disables the input controls on the view. 
	 * @param disabled : true, to disable the controls.
	 */
	void setInputControlsDisabled(boolean disabled) {
		name.setDisable(disabled);
	}

	/**
	 * To set the content of input controls in view state. Can be re-implemented
	 * in derived classes but currently not used.
	 */
	void setInputControlsViewState() {
	}
	

}