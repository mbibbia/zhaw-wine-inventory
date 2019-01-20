package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.controller.helper.ControllerState;
import ch.zhaw.wineInventory.event.ChangeCountryEvent;
import ch.zhaw.wineInventory.event.ChangeEntityEventType;
import ch.zhaw.wineInventory.event.RegionDetailsEvent;
import ch.zhaw.wineInventory.event.ChangeRegionEvent;
import ch.zhaw.wineInventory.event.CountryDetailsEvent;
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

	/*
	 * Event handler to display an object in the view.
	 */
	@Component
	class ShowRegionDetailEventHandler implements ApplicationListener<RegionDetailsEvent> {

		@Override
		public void onApplicationEvent(RegionDetailsEvent event) {
			id.setText(Long.toString(event.getRegion().getId()));
			country.setValue(event.getRegion().getCountry());
			name.setText(event.getRegion().getName());
			
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
			if (country != null) {
				country.setItems(loadCountries());
				country.setValue(event.getCountry());
			}
		}

	}

	/*
	 * Event handler for a saved or deleted object.
	 */
	@Component
	class ChangeRegionEventHandler implements ApplicationListener<ChangeRegionEvent> {

		@Override
		public void onApplicationEvent(ChangeRegionEvent event) {
			switch (event.getChangeType()) {
			case SAVE:
				RegionDetailsEvent regionDetailsEvent = new RegionDetailsEvent(this, event.getRegion());
				applicationEventPublisher.publishEvent(regionDetailsEvent);
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
	private ComboBox<Country> country;

	@Autowired
	private RegionService regionService;

	@Autowired
	private CountryService countryService;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		country.setItems(loadCountries());
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#initializeInputControlsStyle()
	 */
	@Override
	protected void initializeInputControlsStyle() {
		super.initializeInputControlsStyle();
		// Text field and combo boxes should be readable
		// also in VIEW state.
		country.setStyle("-fx-opacity: 1;");
	}

	private Country getCountry() {
		return country.getValue();
	}

	/** Loads all countries from the database.
	 * @return an observable list of countries
	 */
	private ObservableList<Country> loadCountries() {
		return FXCollections.observableArrayList(countryService.findAll());
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#deletePersistent(java.lang.Object)
	 */
	@Override
	void deletePersistent(Object object) {
		regionService.delete((Region) object);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#getPersistent()
	 */
	@Override
	Object getPersistent() {
		return regionService.find(Long.parseLong(id.getText()));
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#isValid()
	 */
	@Override
	boolean isValid() {
		return (validation.emptyValidation("Name", getName().isEmpty())
				&& validation.emptyValidation("Country", getCountry() == null));
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistExisting()
	 */
	@Override
	Object persistExisting() {
		Region region = (Region) getPersistent();
		region.setCountry(getCountry());
		region.setName(getName());
		return regionService.update(region);

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#persistNew()
	 */
	@Override
	Object persistNew() {
		Region region = new Region();
		region.setCountry(getCountry());
		region.setName(getName());
		return regionService.save(region);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertNew(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseAlertUpdate(java.lang.Object)
	 */
	@Override
	void raiseAlertUpdate(Object object) {
		Region region = (Region) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Region updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The region " + region.getName() + " has been updated.");
		alert.showAndWait();

	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventDelete(java.lang.Object)
	 */
	@Override
	void raiseEventDelete(Object object) {
		ChangeRegionEvent regionEvent = new ChangeRegionEvent(this,
			                                                  null,
			                                                  ChangeEntityEventType.DELETE);
		applicationEventPublisher.publishEvent(regionEvent);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#raiseEventSave(java.lang.Object)
	 */
	@Override
	void raiseEventSave(Object object) {
		ChangeRegionEvent regionEvent = new ChangeRegionEvent(this,
			                                                  (Region) object,
			                                                  ChangeEntityEventType.SAVE);
		applicationEventPublisher.publishEvent(regionEvent);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#setInputControlsDisabled(boolean)
	 */
	@Override
	protected void setInputControlsDisabled(boolean disabled) {
		super.setInputControlsDisabled(disabled);

		country.setDisable(disabled);
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.wineInventory.controller.MainDetailController#setInputControlsCleared()
	 */
	@Override
	protected void setInputControlsCleared() {
		super.setInputControlsCleared();

		country.setValue(null);
	}

}
