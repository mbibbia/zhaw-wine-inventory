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
import ch.zhaw.wineInventory.event.WineSaveEvent;
import ch.zhaw.wineInventory.event.WineTypeSaveEvent;
import ch.zhaw.wineInventory.event.ClassificationSaveEvent;
import ch.zhaw.wineInventory.event.CountrySaveEvent;
import ch.zhaw.wineInventory.event.ProducerSaveEvent;
import ch.zhaw.wineInventory.event.WineDeleteEvent;
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
	class DeleteWineEventHandler implements ApplicationListener<WineDeleteEvent> {

		@Override
		public void onApplicationEvent(WineDeleteEvent event) {
			loadData();
		}

	}

	@Component
	class SaveClassificationEventHandler implements ApplicationListener<ClassificationSaveEvent> {

		@Override
		public void onApplicationEvent(ClassificationSaveEvent event) {
			loadData();
		}

	}

	@Component
	class SaveCountryEventHandler implements ApplicationListener<CountrySaveEvent> {

		@Override
		public void onApplicationEvent(CountrySaveEvent event) {
			loadData();
		}

	}

	@Component
	class SaveProducerEventHandler implements ApplicationListener<ProducerSaveEvent> {

		@Override
		public void onApplicationEvent(ProducerSaveEvent event) {
			loadData();
		}

	}

	@Component
	class SaveWineEventHandler implements ApplicationListener<WineSaveEvent> {

		@Override
		public void onApplicationEvent(WineSaveEvent event) {
			loadData();
		}

	}

	@Component
	class SaveWineTypeEventHandler implements ApplicationListener<WineTypeSaveEvent> {

		@Override
		public void onApplicationEvent(WineTypeSaveEvent event) {
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
	void setColumnProperties() {
		super.setColumnProperties();
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colClassification.setCellValueFactory(new PropertyValueFactory<>("classification"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));

	}

}
