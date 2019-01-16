package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
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

@Controller
abstract class MainTableController implements Initializable {

	@FXML
	private TableView<Object> tableView;

	@FXML
	private TableColumn<Object, Long> colId;

	@FXML
	private TableColumn<Object, String> colName;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setSelectionMode();
		setColumnProperties();
		loadData();
		notifyDetailView();

	}

	@FXML
	void delete() {
		List<Object> objects = tableView.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			deleteInBatch(objects);
		loadData();
	}

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

	@FXML
	void edit() {

		Object object = tableView.getSelectionModel().getSelectedItem();
		raiseEventShow(object);

	}

	abstract void deleteInBatch(List<Object> objects);

	abstract void loadData();

	void raiseEventShow(Object object) {

	}

	void setColumnProperties() {

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));

	}

	void setSelectionMode() {

		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	}

}
