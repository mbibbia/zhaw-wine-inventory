package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.controller.helper.ControllerState;
import ch.zhaw.wineInventory.event.CountryDetailsEvent;
import ch.zhaw.wineInventory.event.ChangeCountryEvent;
import ch.zhaw.wineInventory.event.ChangeEntityEventType;
import ch.zhaw.wineInventory.service.CountryService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View CountryDetail.fxml.
 *
 */

@Controller
public class CountryDetailController extends MainDetailController {

	/*
	 * Event handler to display an object in the view.
	 */
	@Component
	class ShowCountryDetailEventHandler implements ApplicationListener<CountryDetailsEvent> {

		@Override
		public void onApplicationEvent(CountryDetailsEvent event) {
			id.setText(Long.toString(event.getCountry().getId()));
			code.setText(event.getCountry().getCode());
			name.setText(event.getCountry().getName());

			changeState(ControllerState.VIEW);
		}

	}
	
	/*
	 * Event handler for a saved or deleted object.
	 */
	@Component
	class ChangeCountryEventHandler implements ApplicationListener<ChangeCountryEvent> {

		@Override
		public void onApplicationEvent(ChangeCountryEvent event) {
			switch (event.getChangeType()) {
			case SAVE:
				CountryDetailsEvent countryDetailsEvent = new CountryDetailsEvent(this, event.getCountry());
				applicationEventPublisher.publishEvent(countryDetailsEvent);
				break;
			case DELETE:
				reset();
				break;
			
			default:
				break;
			}
		}

	}
	

	@FXML
	private TextField code;

	@Autowired
	private CountryService countryService;

	private boolean codeValid = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#initializeEventListenersForSaveButton()
	 */
	protected void initializeEventListenersForSaveButton() {
		super.initializeEventListenersForSaveButton();

		code.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.trim().isEmpty()) {
				codeValid = true;
			} else {
				codeValid = false;
			}
			updateSaveButton();
		});
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#isValid()
	 */
	@Override
	boolean isValid() {
		return super.isValid() && validation.validate("Code", getCode(), "[A-Z]{2}");

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#getSaveButtonValidState()
	 */
	protected boolean getSaveButtonValidState() {
		return codeValid && super.getSaveButtonValidState();
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#initializeInputControlsStyle()
	 */
	@Override
	protected void initializeInputControlsStyle() {
		super.initializeInputControlsStyle();
		// Text field and combo boxes should be readable
		// also in VIEW state.
		code.setStyle("-fx-opacity: 1;");
	}

	private String getCode() {
		return code.getText();
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#deletePersistent(java.lang.Object)
	 */
	@Override
	void deletePersistent(Object object) {
		countryService.delete((Country) object);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#getPersistent()
	 */
	@Override
	Object getPersistent() {
		return countryService.find(Long.parseLong(id.getText()));
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistExisting()
	 */
	@Override
	Object persistExisting() {
		Country country = (Country) getPersistent();
		country.setCode(getCode());
		country.setName(getName());
		return countryService.update(country);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistNew()
	 */
	@Override
	Object persistNew() {
		Country country = new Country();
		country.setCode(getCode());
		country.setName(getName());
		return countryService.save(country);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertNew(java.lang.Object)
	 */
	@Override
	void raiseAlertNew(Object object) {
		Country country = (Country) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Country saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The country " + country.getName() + " has been created and \n id is " + country.getId() + ".");
		alert.showAndWait();

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertUpdate(java.lang.Object)
	 */
	@Override
	void raiseAlertUpdate(Object object) {
		Country country = (Country) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Country updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The country " + country.getName() + " has been updated.");
		alert.showAndWait();

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventDelete(java.lang.Object)
	 */
	@Override
	void raiseEventDelete(Object object) {
		ChangeCountryEvent countryEvent = new ChangeCountryEvent(this,
			                                                     null,
			                                                     ChangeEntityEventType.DELETE);
		applicationEventPublisher.publishEvent(countryEvent);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventSave(java.lang.Object)
	 */
	@Override
	void raiseEventSave(Object object) {
		ChangeCountryEvent countryEvent = new ChangeCountryEvent(this,
			                                                     (Country)object,
			                                                     ChangeEntityEventType.SAVE);
		applicationEventPublisher.publishEvent(countryEvent);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#setInputControlsDisabled(boolean)
	 */
	@Override
	protected void setInputControlsDisabled(boolean disabled) {
		super.setInputControlsDisabled(disabled);

		code.setDisable(disabled);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#setInputControlsCleared()
	 */
	@Override
	protected void setInputControlsCleared() {
		super.setInputControlsCleared();

		code.clear();
	}

}
