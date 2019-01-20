package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Classification;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a classification is saved.
 *
 */
public class ChangeClassificationEvent extends ChangeEvent {

	private Classification classification;

	private static final long serialVersionUID = 1L;

	public ChangeClassificationEvent(Object source,
			                         Classification classification,
			                         ChangeEntityEventType changeType) {
		super(source, changeType);
		this.classification = classification;

	}

	/**
	 * Returns classification bean that has been saved.
	 * 
	 * @return Classification
	 */
	public Classification getClassification() {
		return this.classification;
	}

}
