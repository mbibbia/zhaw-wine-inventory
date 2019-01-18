package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.event.CountryDetailsEvent;
import ch.zhaw.wineInventory.event.ChangeCountryEvent;
import ch.zhaw.wineInventory.service.CountryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View CountryTable.fxml.
 *
 */

@Controller
public class CountryTableController extends MainTableController {

	@FXML
	private TableColumn<Country, String> colCode;

	@Autowired
	private CountryService countryService;

	private final ObservableList<Country> countryList = FXCollections.observableArrayList();

	@Component
	class SaveCountryEventHandler implements ApplicationListener<ChangeCountryEvent> {

		@Override
		public void onApplicationEvent(ChangeCountryEvent event) {
			loadData();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	@Override
	void setColumnProperties() {
		super.setColumnProperties();
		colCode.setCellValueFactory(new PropertyValueFactory<>("code"));

	}

	@Override
	void deleteInBatch(List<Object> objects) {
		@SuppressWarnings("unchecked")
		List<Country> countries = (List<Country>) (List<?>) objects;
		countryService.deleteInBatch(countries);

	}

	@Override
	void loadData() {
		countryList.clear();
		countryList.addAll(countryService.findAll());
		@SuppressWarnings("unchecked")
		ObservableList<Object> list = (ObservableList<Object>) (ObservableList<?>) countryList;
		tableView.setItems(list);

	}

	@Override
	void raiseEventShow(Object object) {
		CountryDetailsEvent countryEvent = new CountryDetailsEvent(this, (Country) object);
		applicationEventPublisher.publishEvent(countryEvent);

	}

}
