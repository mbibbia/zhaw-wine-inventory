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
public class ClassificationSaveEvent extends ApplicationEvent {

	private Classification classification;

	private static final long serialVersionUID = 1L;

	public ClassificationSaveEvent(Object source, Classification classification) {
		super(source);
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
