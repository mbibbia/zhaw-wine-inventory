package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.event.ProducerDetailsEvent;
import ch.zhaw.wineInventory.event.ChangeProducerEvent;
import ch.zhaw.wineInventory.service.ProducerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View ProducerTable.fxml.
 *
 */

@Controller
public class ProducerTableController extends MainTableController {

	@FXML
	private TableColumn<Producer, String> colCompany;

	@FXML
	private TableColumn<Producer, String> colAddressLine1;

	@FXML
	private TableColumn<Producer, String> colAddressLine2;

	@FXML
	private TableColumn<Producer, String> colZipCode;

	@FXML
	private TableColumn<Producer, String> colPlace;

	@FXML
	private TableColumn<Producer, String> colPhone;

	@FXML
	private TableColumn<Producer, String> colFax;

	@FXML
	private TableColumn<Producer, String> colEmail;

	@FXML
	private TableColumn<Producer, String> colUrl;

	@FXML
	private TableColumn<Producer, Country> colCountry;

	@FXML
	private TableColumn<Producer, Region> colRegion;

	@Autowired
	private ProducerService producerService;

	private final ObservableList<Producer> producerList = FXCollections.observableArrayList();

	@Component
	class SaveProducerEventHandler implements ApplicationListener<ChangeProducerEvent> {

		@Override
		public void onApplicationEvent(ChangeProducerEvent event) {
			loadData();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	@Override
	void deleteInBatch(List<Object> objects) {
		@SuppressWarnings("unchecked")
		List<Producer> producers = (List<Producer>) (List<?>) objects;
		producerService.deleteInBatch(producers);
	}

	@Override
	void loadData() {
		producerList.clear();
		producerList.addAll(producerService.findAll());
		@SuppressWarnings("unchecked")
		ObservableList<Object> list = (ObservableList<Object>) (ObservableList<?>) producerList;
		tableView.setItems(list);
	}

	@Override
	void raiseEventShow(Object object) {
		ProducerDetailsEvent producerEvent = new ProducerDetailsEvent(this, (Producer) object);
		applicationEventPublisher.publishEvent(producerEvent);
	}

	@Override
	void raiseEventDelete(Object object) {
		ChangeProducerEvent producerEvent = new ChangeProducerEvent(this, null);
		applicationEventPublisher.publishEvent(producerEvent);
	}

	@Override
	void setColumnProperties() {
		super.setColumnProperties();
		colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
		colAddressLine1.setCellValueFactory(new PropertyValueFactory<>("addressLine1"));
		colAddressLine2.setCellValueFactory(new PropertyValueFactory<>("addressLine2"));
		colZipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
		colPlace.setCellValueFactory(new PropertyValueFactory<>("place"));
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
		colFax.setCellValueFactory(new PropertyValueFactory<>("fax"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colUrl.setCellValueFactory(new PropertyValueFactory<>("url"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
	}

}
