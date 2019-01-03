package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Producer;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a single producer is clicked.
 *
 */
public class ProducerDetailsEvent extends ApplicationEvent {

	private Producer producer;

	private static final long serialVersionUID = 1L;

	public ProducerDetailsEvent(Object source, Producer producer) {
		super(source);
		this.producer = producer;

	}

	/**
	 * Returns pushed producer bean.
	 * 
	 * @return Producer
	 */
	public Producer getProducer() {
		return this.producer;
	}

}
