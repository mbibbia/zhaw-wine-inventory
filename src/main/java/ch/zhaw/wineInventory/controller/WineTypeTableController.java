package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.event.WineTypeSaveEvent;
import ch.zhaw.wineInventory.event.WineTypeDetailsEvent;
import ch.zhaw.wineInventory.service.WineTypeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View WineTypeTable.fxml.
 *
 */

@Controller
public class WineTypeTableController extends MainTableController {

	@Component
	class SaveWineTypeEventHandler implements ApplicationListener<WineTypeSaveEvent> {

		@Override
		public void onApplicationEvent(WineTypeSaveEvent event) {
			loadData();
		}

	}

	@Autowired
	private WineTypeService wineTypeService;

	private final ObservableList<WineType> wineTypeList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
	}

	@Override
	void deleteInBatch(List<Object> objects) {
		@SuppressWarnings("unchecked")
		List<WineType> wineTypes = (List<WineType>) (List<?>) objects;
		wineTypeService.deleteInBatch(wineTypes);

	}

	@Override
	void loadData() {
		wineTypeList.clear();
		wineTypeList.addAll(wineTypeService.findAll());
		@SuppressWarnings("unchecked")
		ObservableList<Object> list = (ObservableList<Object>) (ObservableList<?>) wineTypeList;
		tableView.setItems(list);

	}

	@Override
	void raiseEventShow(Object object) {
		WineTypeDetailsEvent wineTypeEvent = new WineTypeDetailsEvent(this, (WineType) object);
		applicationEventPublisher.publishEvent(wineTypeEvent);

	}

	@Override
	void setColumnProperties() {
		super.setColumnProperties();
	}

}
