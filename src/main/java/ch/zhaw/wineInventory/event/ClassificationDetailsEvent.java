package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Classification;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a single classification is clicked.
 *
 */
public class ClassificationDetailsEvent extends ApplicationEvent {

	private Classification classification;

	private static final long serialVersionUID = 1L;

	public ClassificationDetailsEvent(Object source, Classification classification) {
		super(source);
		this.classification = classification;

	}

	/**
	 * Returns pushed classification bean.
	 * 
	 * @return Classification
	 */
	public Classification getClassification() {
		return this.classification;
	}

}
