package ch.zhaw.wineInventory.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import ch.zhaw.wineInventory.config.StageManager;
import ch.zhaw.wineInventory.controller.validation.ControllerValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Controller
abstract class MainDetailController implements Initializable {

	@Lazy
	@Autowired
	StageManager stageManager;

	@SuppressWarnings("unused")
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;

	@SuppressWarnings("unused")
	@Autowired
	ControllerValidation validation;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	private void create() {
		Object object = persistNew();
		raiseCreateAlert(object);
		raiseSaveEvent(object);

	};

	private void update() {
		Object object = persistExisting();
		raiseUpdateAlert(object);
		raiseSaveEvent(object);

	};

	abstract void clearFields();

	abstract boolean isNew();

	abstract boolean isValid();

	abstract Object persistExisting();

	abstract Object persistNew();

	abstract void raiseCreateAlert(Object object);

	abstract void raiseSaveEvent(Object object);

	abstract void raiseUpdateAlert(Object object);

	@FXML
	void reset() {
		clearFields();
	}

	@FXML
	void save() {
		if (isValid()) {
			if (isNew()) {
				create();
			} else {
				update();
			}
		}
		clearFields();
	}

}
