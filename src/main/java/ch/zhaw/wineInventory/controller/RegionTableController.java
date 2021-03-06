package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.event.ChangeCountryEvent;
import ch.zhaw.wineInventory.event.ChangeEntityEventType;
import ch.zhaw.wineInventory.event.RegionDetailsEvent;
import ch.zhaw.wineInventory.event.ChangeRegionEvent;
import ch.zhaw.wineInventory.service.RegionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View RegionTable.fxml.
 *
 */

@Controller
public class RegionTableController extends MainTableController {

	@Component
	class ChangeCountryEventHandler implements ApplicationListener<ChangeCountryEvent> {

		@Override
		public void onApplicationEvent(ChangeCountryEvent event) {
			if (tableView != null) {
				loadData();
			}
		}

	}

	@Component
	class ChangeRegionEventHandler implements ApplicationListener<ChangeRegionEvent> {

		@Override
		public void onApplicationEvent(ChangeRegionEvent event) {
			if (tableView != null) {
				loadData();
			}
		}

	}

	@FXML
	private TableColumn<Region, Country> colCountry;

	@Autowired
	private RegionService regionService;

	private final ObservableList<Region> regionList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	@Override
	void deleteInBatch(List<Object> objects) {
		@SuppressWarnings("unchecked")
		List<Region> regions = (List<Region>) (List<?>) objects;
		regionService.deleteInBatch(regions);
	}

	@Override
	void loadData() {
		regionList.clear();
		regionList.addAll(regionService.findAll());
		@SuppressWarnings("unchecked")
		ObservableList<Object> list = (ObservableList<Object>) (ObservableList<?>) regionList;
		tableView.setItems(list);
	}

	@Override
	void raiseEventShow(Object object) {
		RegionDetailsEvent regionEvent = new RegionDetailsEvent(this, (Region) object);
		applicationEventPublisher.publishEvent(regionEvent);
	}

	@Override
	void raiseEventDelete(Object object) {
		ChangeRegionEvent regionEvent = new ChangeRegionEvent(this,
			                                                  null,
			                                                  ChangeEntityEventType.DELETE);
		applicationEventPublisher.publishEvent(regionEvent);
	}

	@Override
	void setColumnProperties() {
		super.setColumnProperties();
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
	}

}
