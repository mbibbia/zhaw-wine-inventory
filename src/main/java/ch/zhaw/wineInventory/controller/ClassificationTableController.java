package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.event.ClassificationDetailsEvent;
import ch.zhaw.wineInventory.event.ClassificationSaveEvent;
import ch.zhaw.wineInventory.service.ClassificationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View ClassificationTable.fxml.
 *
 */

@Controller
public class ClassificationTableController extends MainTableController {

	@Component
	class SaveClassificationEventHandler implements ApplicationListener<ClassificationSaveEvent> {

		@Override
		public void onApplicationEvent(ClassificationSaveEvent event) {
			loadData();
		}

	}

	@Autowired
	private ClassificationService classificationService;

	private final ObservableList<Classification> classificationList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

	}

	@Override
	void deleteInBatch(List<Object> objects) {
		@SuppressWarnings("unchecked")
		List<Classification> classifications = (List<Classification>) (List<?>) objects;
		classificationService.deleteInBatch(classifications);
	}

	@Override
	void loadData() {

		classificationList.clear();
		classificationList.addAll(classificationService.findAll());
		@SuppressWarnings("unchecked")
		ObservableList<Object> list = (ObservableList<Object>) (ObservableList<?>) classificationList;
		tableView.setItems(list);

	}

	@Override
	void raiseEventShow(Object object) {

		ClassificationDetailsEvent classificationEvent = new ClassificationDetailsEvent(this, (Classification) object);
		applicationEventPublisher.publishEvent(classificationEvent);

	}

}
