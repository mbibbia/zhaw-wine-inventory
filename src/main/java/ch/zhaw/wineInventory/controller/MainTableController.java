package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.config.StageManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Abstract base class for all TableControllers.
 *
 */
@Controller
abstract class MainTableController implements Initializable {

	@FXML
	TableView<Object> tableView;

	@FXML
	TableColumn<Object, Long> colId;

	@FXML
	TableColumn<Object, String> colName;

	@Lazy
	@Autowired
	StageManager stageManager;

	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setSelectionMode();
		setColumnProperties();
		loadData();
		notifyDetailView();

	}

	/**
	 * To delete one or many selected objects.
	 */
	@FXML
	void delete() {
		List<Object> objects = tableView.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			deleteInBatch(objects);
		}

		for (Object object : objects) {
			raiseEventDelete(object);
		}

		loadData();
	}

	/**
	 * Used to delete one or many entities from the database, needs to be
	 * implemented in the derived classes.
	 * 
	 * @param objects that should be deleted
	 */
	abstract void deleteInBatch(List<Object> objects);

	/**
	 * To edit one or many objects.
	 */
	@FXML
	void edit() {
		Object object = tableView.getSelectionModel().getSelectedItem();
		raiseEventShow(object);
	}

	/**
	 * Used to load data from database, needs to be implemented in the derived
	 * classes.
	 */
	abstract void loadData();

	/**
	 * Notify detail view on mouse clicked raising selected object.
	 */
	void notifyDetailView() {
		tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Object object = tableView.getSelectionModel().getSelectedItem();
				if (object != null) {
					raiseEventShow(object);
				}
			}
		});

	}

	/**
	 * To raise event when object shall be shown, needs to be implemented in derived
	 * classes.
	 * 
	 * @param object to be shown
	 */
	abstract void raiseEventShow(Object object);

	/**
	 * To raise event when object is deleted, needs to be implemented in derived
	 * classes.
	 * 
	 * @param object to be shown
	 */
	abstract void raiseEventDelete(Object object);

	/**
	 * Set column properties.
	 */
	void setColumnProperties() {
		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
	}

	/**
	 * Set row selection mode (single, multiple).
	 */
	void setSelectionMode() {
		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

}
