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
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.event.WineSaveEvent;
import ch.zhaw.wineInventory.event.WineTypeSaveEvent;
import ch.zhaw.wineInventory.event.ClassificationSaveEvent;
import ch.zhaw.wineInventory.event.CountrySaveEvent;
import ch.zhaw.wineInventory.event.ProducerSaveEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import ch.zhaw.wineInventory.service.ClassificationService;
import ch.zhaw.wineInventory.service.CountryService;
import ch.zhaw.wineInventory.service.ProducerService;
import ch.zhaw.wineInventory.service.WineService;
import ch.zhaw.wineInventory.service.WineTypeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View WineDetail.fxml.
 *
 */

@Controller
public class WineDetailController implements Initializable {

	@FXML
	private Label wineId;

	@FXML
	private Label userId;

	@FXML
	private TextField name;

	@FXML
	private ComboBox<WineType> wineType;

	@FXML
	private ComboBox<Classification> classification;

	@FXML
	private ComboBox<Country> country;

	@FXML
	private ComboBox<Region> region;

	@FXML
	private ComboBox<Producer> producer;

	@FXML
	private Button reset;

	@FXML
	private Button saveWine;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private WineService wineService;

	@Autowired
	private WineTypeService wineTypeService;

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private ProducerService producerService;

	@Component
	class ShowWineDetailEventHandler implements ApplicationListener<WineDetailsEvent> {

		@Override
		public void onApplicationEvent(WineDetailsEvent event) {
			wineId.setText(Long.toString(event.getWine().getId()));
			name.setText(event.getWine().getName());
			wineType.setValue(event.getWine().getType());
			classification.setValue(event.getWine().getClassification());
			country.setValue(event.getWine().getCountry());
			region.setValue(event.getWine().getRegion());
			producer.setValue(event.getWine().getProducer());
		}

	}

	@Component
	class SaveCountryEventHandler implements ApplicationListener<CountrySaveEvent> {

		@Override
		public void onApplicationEvent(CountrySaveEvent event) {
			country.setItems(loadCountries());
			country.setValue(event.getCountry());
		}

	}

	@Component
	class SaveClassificationEventHandler implements ApplicationListener<ClassificationSaveEvent> {

		@Override
		public void onApplicationEvent(ClassificationSaveEvent event) {
			classification.setItems(loadClassifications());
			classification.setValue(event.getClassification());
		}

	}

	@Component
	class SaveWineTypeEventHandler implements ApplicationListener<WineTypeSaveEvent> {

		@Override
		public void onApplicationEvent(WineTypeSaveEvent event) {
			wineType.setItems(loadTypes());
			wineType.setValue(event.getWineType());
		}

	}

	@Component
	class SaveProducerEventHandler implements ApplicationListener<ProducerSaveEvent> {

		@Override
		public void onApplicationEvent(ProducerSaveEvent event) {
			producer.setItems(loadProducers());
			producer.setValue(event.getProducer());
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wineType.setItems(loadTypes());
		classification.setItems(loadClassifications());
		country.setItems(loadCountries());
		producer.setItems(loadProducers());
	}

	private ObservableList<WineType> loadTypes() {
		return FXCollections.observableArrayList(wineTypeService.findAll());
	}

	private ObservableList<Classification> loadClassifications() {
		return FXCollections.observableArrayList(classificationService.findAll());
	}

	private ObservableList<Country> loadCountries() {
		return FXCollections.observableArrayList(countryService.findAll());
	}

	private ObservableList<Producer> loadProducers() {
		return FXCollections.observableArrayList(producerService.findAll());
	}

	private String getName() {
		return name.getText();
	}

	private WineType getType() {
		return wineType.getValue();
	}

	private Classification getClassification() {
		return classification.getValue();
	}

	private Country getCountry() {
		return country.getValue();
	}

	private Region getRegion() {
		return region.getValue();
	}

	private Producer getProducer() {
		return producer.getValue();
	}

	@FXML
	void reset(ActionEvent event) {
		clearFields();
	}

	private void clearFields() {

		wineId.setText(null);
		name.clear();
		wineType.getSelectionModel().clearSelection();
		classification.getSelectionModel().clearSelection();
		country.getSelectionModel().clearSelection();
		region.getSelectionModel().clearSelection();
		producer.getSelectionModel().clearSelection();

	}

	@FXML
	private void handleRegionClicked() {

		if (country.getValue() != null) {
			ObservableList<Region> regions = FXCollections.observableArrayList(country.getValue().getRegions());
			region.setItems(regions);
		}
	}

	@FXML
	private void saveWine(ActionEvent event) {

		/*
		 * if (validate("Name", getName(), "[a-zA-Z]+") && validate("Type", getType(),
		 * "[a-zA-Z]+") && emptyValidation("Classification", getClassification() &&
		 * emptyValidation("Country", getCountry() {
		 */

		if (wineId.getText() == null || wineId.getText() == "") {

			Wine wine = new Wine();
			wine.setName(getName());
			wine.setType(getType());
			wine.setClassification(getClassification());
			wine.setCountry(getCountry());
			wine.setRegion(getRegion());
			wine.setProducer(getProducer());

			Wine newWine = wineService.save(wine);

			saveAlert(newWine);

			raiseEventSaveWine(newWine);

		} else {
			Wine wine = wineService.find(Long.parseLong(wineId.getText()));
			wine.setName(getName());
			wine.setType(getType());
			wine.setClassification(getClassification());
			wine.setCountry(getCountry());
			wine.setRegion(getRegion());
			wine.setProducer(getProducer());
			Wine updatedWine = wineService.update(wine);
			updateAlert(updatedWine);

			raiseEventSaveWine(updatedWine);
		}

		clearFields();

	}

	private void raiseEventSaveWine(final Wine wine) {
		WineSaveEvent wineEvent = new WineSaveEvent(this, wine);
		applicationEventPublisher.publishEvent(wineEvent);
	}

	private void saveAlert(Wine wine) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been created and \n id is " + wine.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Wine wine) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been updated.");
		alert.showAndWait();
	}

}
