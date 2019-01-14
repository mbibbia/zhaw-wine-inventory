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
import ch.zhaw.wineInventory.event.CountrySaveEvent;
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

	@FXML
	private TextField code;

	@Autowired
	private CountryService countryService;

	private boolean codeValid = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

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

	@Override
	boolean isValid() {
		return validation.emptyValidation("Name", getName().isEmpty())
				&& validation.validate("Code", getCode(), "[A-Z]{2}");

	}

	protected boolean getSaveButtonValidState() {
		return codeValid && super.getSaveButtonValidState();
	}

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

	@Override
	void deletePersistent(Object object) {
		countryService.delete((Country) object);

	}

	@Override
	Object getPersistent() {
		return countryService.find(Long.parseLong(id.getText()));
	}

	@Override
	Object persistExisting() {
		Country country = (Country) getPersistent();
		country.setCode(getCode());
		country.setName(getName());
		return countryService.update(country);
	}

	@Override
	Object persistNew() {
		Country country = new Country();
		country.setCode(getCode());
		country.setName(getName());
		return countryService.save(country);
	}

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

	@Override
	void raiseAlertUpdate(Object object) {
		Country country = (Country) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Country updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The country " + country.getName() + " has been updated.");
		alert.showAndWait();

	}

	@Override
	void raiseEventDelete(Object object) {
		// TODO Auto-generated method stub

		// CountryDeleteEvent countryEvent = new
		// CountryDeleteEvent(this, (Country) object);
		// applicationEventPublisher.publishEvent(countryEvent);

	}

	@Override
	void raiseEventSave(Object object) {
		CountrySaveEvent countryEvent = new CountrySaveEvent(this, (Country) object);
		applicationEventPublisher.publishEvent(countryEvent);

	}

	@Override
	protected void setInputControlsDisabled(boolean disabled) {
		super.setInputControlsDisabled(disabled);

		code.setDisable(disabled);
	}

	@Override
	protected void setInputControlsCleared() {
		super.setInputControlsCleared();

		code.clear();
	}

}
