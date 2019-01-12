package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.event.WineSaveEvent;
import ch.zhaw.wineInventory.event.WineTypeSaveEvent;
import ch.zhaw.wineInventory.event.ClassificationSaveEvent;
import ch.zhaw.wineInventory.event.CountrySaveEvent;
import ch.zhaw.wineInventory.event.ProducerSaveEvent;
import ch.zhaw.wineInventory.event.WineDetailsEvent;
import ch.zhaw.wineInventory.service.WineService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Controller for FXML View WineTable.fxml.
 *
 */

@Controller
public class WineTableController implements Initializable {
	@FXML
	private TableView<Wine> wineTable;

	@FXML
	private TableColumn<Wine, Long> colWineId;

	@FXML
	private TableColumn<Wine, String> colName;

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

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private WineService wineService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final ObservableList<Wine> wineList = FXCollections.observableArrayList();

	@Component
	class SaveWineEventHandler implements ApplicationListener<WineSaveEvent> {

		@Override
		public void onApplicationEvent(WineSaveEvent event) {
			loadWines();
		}

	}

	@Component
	class SaveCountryEventHandler implements ApplicationListener<CountrySaveEvent> {

		@Override
		public void onApplicationEvent(CountrySaveEvent event) {
			loadWines();
		}

	}

	@Component
	class SaveClassificationEventHandler implements ApplicationListener<ClassificationSaveEvent> {

		@Override
		public void onApplicationEvent(ClassificationSaveEvent event) {
			loadWines();
		}

	}

	@Component
	class SaveWineTypeEventHandler implements ApplicationListener<WineTypeSaveEvent> {

		@Override
		public void onApplicationEvent(WineTypeSaveEvent event) {
			loadWines();
		}

	}

	@Component
	class SaveProducerEventHandler implements ApplicationListener<ProducerSaveEvent> {

		@Override
		public void onApplicationEvent(ProducerSaveEvent event) {
			loadWines();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		wineTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		setColumnProperties();
		loadWines();

		wineTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
    		if (newSelection != null) {
    			int index = wineTable.getSelectionModel().getSelectedIndex();
    			Wine wine = wineTable.getSelectionModel().getTableView().getItems().get(index);
				raiseEventShowWine(wine);
    		}
		});

	}

	private void setColumnProperties() {
		colWineId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("name"));
		colType.setCellValueFactory(new PropertyValueFactory<>("type"));
		colClassification.setCellValueFactory(new PropertyValueFactory<>("classification"));
		colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));
		colProducer.setCellValueFactory(new PropertyValueFactory<>("producer"));
	}

	private void loadWines() {
		wineList.clear();
		wineList.addAll(wineService.findAll());
		wineTable.setItems(wineList);
	}

	private void raiseEventShowWine(final Wine wine) {
		WineDetailsEvent wineEvent = new WineDetailsEvent(this, wine);
		applicationEventPublisher.publishEvent(wineEvent);
	}

	Callback<TableColumn<Wine, Boolean>, TableCell<Wine, Boolean>> cellFactory = new Callback<TableColumn<Wine, Boolean>, TableCell<Wine, Boolean>>() {
		@Override
		public TableCell<Wine, Boolean> call(final TableColumn<Wine, Boolean> param) {
			final TableCell<Wine, Boolean> cell = new TableCell<Wine, Boolean>() {
				Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
				final Button btnEdit = new Button();

				@Override
				public void updateItem(Boolean check, boolean empty) {
					super.updateItem(check, empty);
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnEdit.setOnAction(e -> {
							Wine wine = getTableView().getItems().get(getIndex());
							raiseEventShowWine(wine);
						});

						btnEdit.setStyle("-fx-background-color: transparent;");
						ImageView iv = new ImageView();
						iv.setImage(imgEdit);
						iv.setPreserveRatio(true);
						iv.setSmooth(true);
						iv.setCache(true);
						btnEdit.setGraphic(iv);

						setGraphic(btnEdit);
						setAlignment(Pos.CENTER);
						setText(null);
					}
				}

			};
			return cell;
		}
	};

	@FXML
	void editWine(ActionEvent event) {
		Wine wine = wineTable.getSelectionModel().getSelectedItem();
		raiseEventShowWine(wine);

	}

	@FXML
	void deleteWines(ActionEvent event) {
		List<Wine> wines = wineTable.getSelectionModel().getSelectedItems();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete selected?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			wineService.deleteInBatch(wines);

		loadWines();
	}

}
