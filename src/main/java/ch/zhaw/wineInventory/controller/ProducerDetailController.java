package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.event.CountrySaveEvent;
import ch.zhaw.wineInventory.event.ProducerDetailsEvent;
import ch.zhaw.wineInventory.event.ProducerSaveEvent;
import ch.zhaw.wineInventory.service.CountryService;
import ch.zhaw.wineInventory.service.ProducerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View ProducerDetail.fxml.
 *
 */

@Controller
public class ProducerDetailController extends MainDetailController {

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
	class ShowProducerDetailEventHandler implements ApplicationListener<ProducerDetailsEvent> {

		@Override
		public void onApplicationEvent(ProducerDetailsEvent event) {
			id.setText(Long.toString(event.getProducer().getId()));
			name.setText(event.getProducer().getName());
			company.setText(event.getProducer().getCompany());
			addressLine1.setText(event.getProducer().getAddressLine1());
			addressLine2.setText(event.getProducer().getAddressLine2());
			zipCode.setText(event.getProducer().getZipCode());
			place.setText(event.getProducer().getPlace());
			phone.setText(event.getProducer().getPhone());
			fax.setText(event.getProducer().getFax());
			email.setText(event.getProducer().getEmail());
			url.setText(event.getProducer().getUrl());
			if (event.getProducer().getCountry() != null) {
				country.setValue(event.getProducer().getCountry());
			}
			if (event.getProducer().getRegion() != null) {
				region.setValue(event.getProducer().getRegion());
			}
		}

	}

	@FXML
	private TextField company;

	@FXML
	private TextField addressLine1;

	@FXML
	private TextField addressLine2;

	@FXML
	private TextField zipCode;

	@FXML
	private TextField place;

	@FXML
	private TextField phone;

	@FXML
	private TextField fax;

	@FXML
	private TextField email;

	@FXML
	private TextField url;

	@FXML
	private ComboBox<Country> country;

	@FXML
	private ComboBox<Region> region;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private CountryService countryService;

	public String getAddressLine1() {
		return addressLine1.getText();
	}

	public String getAddressLine2() {
		return addressLine2.getText();
	}

	public String getCompany() {
		return company.getText();
	}

	public String getEmail() {
		return email.getText();
	}

	public String getFax() {
		return fax.getText();
	}

	public String getPhone() {
		return phone.getText();
	}

	public String getPlace() {
		return place.getText();
	}

	public String getUrl() {
		return url.getText();
	}

	public String getZipCode() {
		return zipCode.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		country.setItems(loadCountries());
	}

	private Country getCountry() {
		return country.getValue();
	}

	private Region getRegion() {
		return region.getValue();
	}

	@FXML
	private void handleRegionClicked() {

		if (country.getValue() != null) {
			ObservableList<Region> regions = FXCollections.observableArrayList(country.getValue().getRegions());
			region.setItems(regions);
		}
	}

	private ObservableList<Country> loadCountries() {
		ObservableList<Country> list = FXCollections.observableArrayList(countryService.findAll());
		list.add(0, new Country());
		return list;
	}

	void clearFields() {

		super.clearFields();
		company.clear();
		addressLine1.clear();
		addressLine2.clear();
		zipCode.clear();
		place.clear();
		phone.clear();
		fax.clear();
		email.clear();
		url.clear();
		country.setValue(null);
		region.setValue(null);

	}

	@Override
	void deletePersistent(Object object) {
		producerService.delete((Producer) object);
	}

	@Override
	Object getPersistent() {
		return producerService.find(Long.parseLong(id.getText()));
	}

	@Override
	Object persistExisting() {
		Producer producer = (Producer) getPersistent();
		producer.setName(getName());
		producer.setCompany(getCompany());
		producer.setAddressLine1(getAddressLine1());
		producer.setAddressLine2(getAddressLine2());
		producer.setZipCode(getZipCode());
		producer.setPlace(getPlace());
		producer.setPhone(getPhone());
		producer.setFax(getFax());
		producer.setEmail(getEmail());
		producer.setUrl(getUrl());
		producer.setCountry(getCountry());
		producer.setRegion(getRegion());
		return producerService.update(producer);
	}

	@Override
	Object persistNew() {
		Producer producer = new Producer();
		producer.setName(getName());
		producer.setCompany(getCompany());
		producer.setAddressLine1(getAddressLine1());
		producer.setAddressLine2(getAddressLine2());
		producer.setZipCode(getZipCode());
		producer.setPlace(getPlace());
		producer.setPhone(getPhone());
		producer.setFax(getFax());
		producer.setEmail(getEmail());
		producer.setUrl(getUrl());
		producer.setCountry(getCountry());
		producer.setRegion(getRegion());
		return producerService.save(producer);
	}

	@Override
	void raiseAlertNew(Object object) {
		Producer producer = (Producer) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Producer saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText(
				"The producer " + producer.getName() + " has been created and \n id is " + producer.getId() + ".");
		alert.showAndWait();

	}

	@Override
	void raiseAlertUpdate(Object object) {
		Producer producer = (Producer) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Producer updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The producer " + producer.getName() + " has been updated.");
		alert.showAndWait();

	}

	@Override
	void raiseEventDelete(Object object) {
		// TODO Auto-generated method stub

		// ProducerDeleteEvent producerEvent = new
		// ProducerDeleteEvent(this, (Producer) object);
		// applicationEventPublisher.publishEvent(producerEvent);
	}

	@Override
	void raiseEventSave(Object object) {
		ProducerSaveEvent producerEvent = new ProducerSaveEvent(this, (Producer) object);
		applicationEventPublisher.publishEvent(producerEvent);

	}

}
