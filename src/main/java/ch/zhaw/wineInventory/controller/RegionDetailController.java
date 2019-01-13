package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.event.CountrySaveEvent;
import ch.zhaw.wineInventory.event.RegionDetailsEvent;
import ch.zhaw.wineInventory.event.RegionSaveEvent;
import ch.zhaw.wineInventory.service.CountryService;
import ch.zhaw.wineInventory.service.RegionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View RegionDetail.fxml. 
 *
 */

@Controller
public class RegionDetailController extends MainDetailController {

	@Component
	class SaveCountryEventHandler implements ApplicationListener<CountrySaveEvent> {

		@Override
		public void onApplicationEvent(CountrySaveEvent event) {
			if (country != null) {
				country.setItems(loadCountries());
				country.setValue(event.getCountry());
			}
		}

	}

	@Component
	class ShowRegionDetailEventHandler implements ApplicationListener<RegionDetailsEvent> {

		@Override
		public void onApplicationEvent(RegionDetailsEvent event) {
			id.setText(Long.toString(event.getRegion().getId()));
			country.setValue(event.getRegion().getCountry());
			name.setText(event.getRegion().getName());
		}

	}

	@FXML
	private ComboBox<Country> country;

	@Autowired
	private RegionService regionService;

	@Autowired
	private CountryService countryService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		country.setItems(loadCountries());
	}

	void clearFields() {
		super.clearFields();
		country.setValue(null);
	}

	private Country getCountry() {
		return country.getValue();
	}

	private ObservableList<Country> loadCountries() {
		return FXCollections.observableArrayList(countryService.findAll());
	}

	@Override
	boolean isValid() {
		return (validation.emptyValidation("Name", getName().isEmpty())
				&& validation.emptyValidation("Country", getCountry() == null));
	}

	@Override
	void deletePersistent(Object object) {
		regionService.delete((Region) object);
	}

	@Override
	Object getPersistent() {
		return regionService.find(Long.parseLong(id.getText()));
	}

	@Override
	Object persistExisting() {
		Region region = (Region) getPersistent();
		region.setCountry(getCountry());
		region.setName(getName());
		return regionService.update(region);

	}

	@Override
	Object persistNew() {
		Region region = new Region();
		region.setCountry(getCountry());
		region.setName(getName());
		return regionService.save(region);
	}

	@Override
	void raiseAlertNew(Object object) {
		Region region = (Region) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Region saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The region " + region.getName() + " has been created and \n id is " + region.getId() + ".");
		alert.showAndWait();

	}

	@Override
	void raiseAlertUpdate(Object object) {
		Region region = (Region) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Region updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The region " + region.getName() + " has been updated.");
		alert.showAndWait();

	}

	@Override
	void raiseEventDelete(Object object) {
		// TODO Auto-generated method stub

		// RegionDeleteEvent regionEvent = new
		// RegionDeleteEvent(this, (Region) object);
		// applicationEventPublisher.publishEvent(regionEvent);

	}

	@Override
	void raiseEventSave(Object object) {
		RegionSaveEvent regionEvent = new RegionSaveEvent(this, (Region) object);
		applicationEventPublisher.publishEvent(regionEvent);

	}

}
