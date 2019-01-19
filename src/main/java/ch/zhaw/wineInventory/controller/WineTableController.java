package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.event.ChangeWineEvent;
import ch.zhaw.wineInventory.event.ChangeWineTypeEvent;
import ch.zhaw.wineInventory.event.ChangeClassificationEvent;
import ch.zhaw.wineInventory.event.ChangeCountryEvent;
import ch.zhaw.wineInventory.event.ChangeProducerEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import ch.zhaw.wineInventory.service.WineService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View WineTable.fxml.
 *
 */

@Controller
public class WineTableController extends MainTableController {

	@Component
	class SaveClassificationEventHandler implements ApplicationListener<ChangeClassificationEvent> {

		@Override
		public void onApplicationEvent(ChangeClassificationEvent event) {
			loadData();
		}

	}

	@Component
	class SaveCountryEventHandler implements ApplicationListener<ChangeCountryEvent> {

		@Override
		public void onApplicationEvent(ChangeCountryEvent event) {
			loadData();
		}

	}

	@Component
	class SaveProducerEventHandler implements ApplicationListener<ChangeProducerEvent> {

		@Override
		public void onApplicationEvent(ChangeProducerEvent event) {
			loadData();
		}

	}

	@Component
	class ChangeWineEventHandler implements ApplicationListener<ChangeWineEvent> {

		@Override
		public void onApplicationEvent(ChangeWineEvent event) {
			loadData();
		}

	}

	@Component
	class ChangeWineTypeEventHandler implements ApplicationListener<ChangeWineTypeEvent> {

		@Override
		public void onApplicationEvent(ChangeWineTypeEvent event) {
			loadData();
		}

	}

	@FXML
	private TableColumn<Wine, WineType> colType;

	@FXML
	private TableColumn<Wine, Classification> colClassification;

	@FXML
	private TableColumn<Wine, Country> colCountry;

	@FXML
	private TableColumn<Wine, Region> colRegion;

	@FXML
	private TableColumn<Wine, Producer> colProducer;

	@Autowired
	private WineService wineService;

	private final ObservableList<Wine> wineList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	@Override
	void deleteInBatch(List<Object> objects) {
		@SuppressWarnings("unchecked")
		List<Wine> wines = (List<Wine>) (List<?>) objects;
		wineService.deleteInBatch(wines);
	}

	@Override
	void loadData() {
		wineList.clear();
		wineList.addAll(wineService.findAll());
		@SuppressWarnings("unchecked")
		ObservableList<Object> list = (ObservableList<Object>) (ObservableList<?>) wineList;
		tableView.setItems(list);
	}

	@Override
	void raiseEventShow(Object object) {
		WineDetailsEvent wineEvent = new WineDetailsEvent(this, (Wine) object);
		applicationEventPublisher.publishEvent(wineEvent);
	}

	@Override
	void raiseEventDelete(Object object) {
		ChangeWineEvent wineEvent = new ChangeWineEvent(this, null);
		applicationEventPublisher.publishEvent(wineEvent);
	}

	@Override
	void setColumnProperties() {
		super.setColumnProperties();
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colClassification.setCellValueFactory(new PropertyValueFactory<>("classification"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
	}

}
