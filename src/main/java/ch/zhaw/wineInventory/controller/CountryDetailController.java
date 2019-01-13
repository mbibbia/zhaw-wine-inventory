package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.controller.validation.ControllerValidation;
import ch.zhaw.wineInventory.event.CountryDetailsEvent;
import ch.zhaw.wineInventory.event.CountrySaveEvent;
import ch.zhaw.wineInventory.service.CountryService;
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
 *         Controller for FXML View CountryDetail.fxml.
 *
 */

@Controller
public class CountryDetailController implements Initializable {

	@Component
	class ShowCountryDetailEventHandler implements ApplicationListener<CountryDetailsEvent> {

		@Override
		public void onApplicationEvent(CountryDetailsEvent event) {
			countryId.setText(Long.toString(event.getCountry().getId()));
			code.setText(event.getCountry().getCode());
			name.setText(event.getCountry().getName());
		}

	}

	@FXML
	private Label countryId;

	@FXML
	private TextField code;

	@FXML
	private TextField name;

	@FXML
	private Button reset;

	@FXML
	private Button saveCountry;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private CountryService countryService;

	@Autowired
	private ControllerValidation validation;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private void clearFields() {

		countryId.setText(null);
		name.clear();
		code.clear();

	}

	private String getCode() {
		return code.getText();
	}

	private String getName() {
		return name.getText();
	}

	private void raiseEventSaveCountry(final Country country) {
		CountrySaveEvent countryEvent = new CountrySaveEvent(this, country);
		applicationEventPublisher.publishEvent(countryEvent);
	}

	private void saveAlert(Country country) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The country " + country.getName() + " has been created and \n id is " + country.getId() + ".");
		alert.showAndWait();
	}

	@FXML
	private void saveCountry(ActionEvent event) {

		if (validation.emptyValidation("Code", getCode().isEmpty())
				&& validation.emptyValidation("Name", getName().isEmpty())) {

			if (countryId.getText() == null || countryId.getText() == "") {

				Country country = new Country();
				country.setCode(getCode());
				country.setName(getName());

				Country newCountry = countryService.save(country);

				saveAlert(newCountry);

				raiseEventSaveCountry(newCountry);

			} else {
				Country country = countryService.find(Long.parseLong(countryId.getText()));
				country.setCode(getCode());
				country.setName(getName());
				Country updatedCountry = countryService.update(country);
				updateAlert(updatedCountry);

				raiseEventSaveCountry(updatedCountry);
			}

			clearFields();

		}

	}

	private void updateAlert(Country country) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The country " + country.getName() + " has been updated.");
		alert.showAndWait();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

}
