package ch.zhaw.wineInventory.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Image;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.controller.helper.ControllerState;
import ch.zhaw.wineInventory.event.WineSaveEvent;
import ch.zhaw.wineInventory.event.WineTypeSaveEvent;
import ch.zhaw.wineInventory.event.ClassificationSaveEvent;
import ch.zhaw.wineInventory.event.CountrySaveEvent;
import ch.zhaw.wineInventory.event.ProducerSaveEvent;
import ch.zhaw.wineInventory.event.ImageDetailsEvent;
import ch.zhaw.wineInventory.event.WineDeleteEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import ch.zhaw.wineInventory.service.ClassificationService;
import ch.zhaw.wineInventory.service.CountryService;
import ch.zhaw.wineInventory.service.ImageService;
import ch.zhaw.wineInventory.service.ProducerService;
import ch.zhaw.wineInventory.service.WineService;
import ch.zhaw.wineInventory.service.WineTypeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View WineDetail.fxml.
 *
 */
@Controller
public class WineDetailController extends MainDetailController {

	@Component
	class SaveClassificationEventHandler implements ApplicationListener<ClassificationSaveEvent> {

		@Override
		public void onApplicationEvent(ClassificationSaveEvent event) {
			classification.setItems(loadClassifications());
			classification.setValue(event.getClassification());
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
	class SaveProducerEventHandler implements ApplicationListener<ProducerSaveEvent> {

		@Override
		public void onApplicationEvent(ProducerSaveEvent event) {
			producer.setItems(loadProducers());
			producer.setValue(event.getProducer());
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
	class ShowWineDetailEventHandler implements ApplicationListener<WineDetailsEvent> {

		@Override
		public void onApplicationEvent(WineDetailsEvent event) {
			id.setText(Long.toString(event.getWine().getId()));
			name.setText(event.getWine().getName());
			wineType.setValue(event.getWine().getType());
			classification.setValue(event.getWine().getClassification());
			country.setValue(event.getWine().getCountry());
			region.setValue(event.getWine().getRegion());
			producer.setValue(event.getWine().getProducer());

			changeState(ControllerState.VIEW);
		}

	}

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
	private Button browseImage;

	@FXML
	private Button removeImage;

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
	
	@Autowired
	private ImageService imageService;
	
	private Image image;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		wineType.setItems(loadTypes());
		classification.setItems(loadClassifications());
		country.setItems(loadCountries());
		producer.setItems(loadProducers());
	}
	
	@Override
	protected void initializeInputControlsStyle() {
		super.initializeInputControlsStyle();
		// Text field and combo boxes should be readable
		// also in VIEW state.
		wineType.setStyle("-fx-opacity: 1;");
		classification.setStyle("-fx-opacity: 1;");
		country.setStyle("-fx-opacity: 1;");
		region.setStyle("-fx-opacity: 1;");
		producer.setStyle("-fx-opacity: 1;");
	}

	@Override
	protected void setInputControlsCleared() {
		super.setInputControlsCleared();

		wineType.setValue(null);
		classification.setValue(null);
		country.setValue(null);
		region.setValue(null);
		producer.setValue(null);
	}

	@Override
	protected void setInputControlsDisabled(boolean disabled) {
		super.setInputControlsDisabled(disabled);

		wineType.setDisable(disabled);
		classification.setDisable(disabled);
		country.setDisable(disabled);
		region.setDisable(disabled);
		producer.setDisable(disabled);
	}

	private Classification getClassification() {
		return classification.getValue();
	}

	private Country getCountry() {
		return country.getValue();
	}

	private Producer getProducer() {
		return producer.getValue();
	}

	private Region getRegion() {
		return region.getValue();
	}

	private WineType getType() {
		return wineType.getValue();
	}

	@FXML
	private void handleRegionClicked() {

		if (country.getValue() != null) {
			ObservableList<Region> regions = FXCollections.observableArrayList(country.getValue().getRegions());
			region.setItems(regions);
		}
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

	private ObservableList<WineType> loadTypes() {
		return FXCollections.observableArrayList(wineTypeService.findAll());
	}

	@Override
	void deletePersistent(Object object) {
		wineService.delete((Wine) object);
	}

	@Override
	Object getPersistent() {
		return wineService.find(Long.parseLong(id.getText()));
	}

	@Override
	Object persistExisting() {
		Wine wine = (Wine) getPersistent();
		wine.setName(getName());
		wine.setType(getType());
		wine.setClassification(getClassification());
		wine.setCountry(getCountry());
		wine.setRegion(getRegion());
		wine.setProducer(getProducer());
		wine.setImage(image);
		return wineService.update(wine);
	}

	@Override
	Object persistNew() {
		Wine wine = new Wine();
		wine.setName(getName());
		wine.setType(getType());
		wine.setClassification(getClassification());
		wine.setCountry(getCountry());
		wine.setRegion(getRegion());
		wine.setProducer(getProducer());
		wine.setImage(image);
		return wineService.save(wine);
	}

	@Override
	void raiseAlertNew(Object object) {
		Wine wine = (Wine) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been created and \n id is " + wine.getId() + ".");
		alert.showAndWait();
	}

	@Override
	void raiseAlertUpdate(Object object) {
		Wine wine = (Wine) object;
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wine updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The wine " + wine.getName() + " has been updated.");
		alert.showAndWait();

	}
		
	@Override
	void raiseEventDelete(Object object) {
		WineDeleteEvent wineEvent = new WineDeleteEvent(this, (Wine) object);
		applicationEventPublisher.publishEvent(wineEvent);

	}

	@Override
	void raiseEventSave(Object object) {
		WineSaveEvent wineEvent = new WineSaveEvent(this, (Wine) object);
		applicationEventPublisher.publishEvent(wineEvent);

	}
	
	@FXML
	private void browseImage() {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(browseImage.getScene().getWindow());

		if (file != null) {

			BufferedImage bImage;
			try {
				bImage = ImageIO.read(file);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, "jpg", bos);
				byte[] data = bos.toByteArray();
				image = new Image();
				image.setName(file.getName());
				image.setType(getFileExtension(file));
				image.setData(data);
				imageService.save(image);
				raiseEventShowImage(image);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	@FXML
	private void removeImage() {
		image = null;
		if (id.getText() != null || id.getText() != "") {
			Wine wine = wineService.find(Long.parseLong(id.getText()));
			wine.setImage(image);
			raiseEventShowImage(image);
		}

	}

	private static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
	
	private void raiseEventShowImage(Image image) {
		ImageDetailsEvent imageEvent = new ImageDetailsEvent(this, image);
		applicationEventPublisher.publishEvent(imageEvent);
	}
	

}
