package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Producer;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a producer is saved.
 *
 */
public class ProducerSaveEvent extends ApplicationEvent {

	private Producer producer;

	private static final long serialVersionUID = 1L;

	public ProducerSaveEvent(Object source, Producer producer) {
		super(source);
		this.producer = producer;

	}

	/**
	 * Returns producer bean that has been saved.
	 * 
	 * @return Producer
	 */
	public Producer getProducer() {
		return this.producer;
	}

}
