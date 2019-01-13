package ch.zhaw.wineInventory.controller.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Helper class for input validation..
 *
 */
@Component
public class ControllerValidation {

	/**
	 * Validates an input value with supplied pattern.
	 * 
	 * @param field   (label of validated value)
	 * @param value   (input value for validation)
	 * @param pattern (regex pattern)
	 * @return whether validation is successful (true) or not (false)
	 */
	public final boolean validate(String field, String value, String pattern) {
		if (!value.isEmpty()) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(value);
			if (m.find() && m.group().equals(value)) {
				return true;
			} else {
				validationAlert(field, false);
				return false;
			}
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	/**
	 * Checks if an input is empty / null.
	 * 
	 * @param field (label of validated value)
	 * @param empty (true or false)
	 * @return whether validation is successful (true) or not (false)
	 */
	public boolean emptyValidation(String field, boolean empty) {
		if (!empty) {
			return true;
		} else {
			validationAlert(field, true);
			return false;
		}
	}

	/**
	 * Raises alert when validation has failed
	 * 
	 * @param field (label of validated value)
	 * @param empty (true or false)
	 */
	private void validationAlert(String field, boolean empty) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Validation Error");
		alert.setHeaderText(null);
		if (empty) {
			alert.setContentText("Please Enter " + field);
		} else {
			alert.setContentText("Please Enter Valid " + field);
		}
		alert.showAndWait();
	}

}
