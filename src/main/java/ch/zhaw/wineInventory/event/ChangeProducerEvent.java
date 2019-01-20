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
public class ChangeProducerEvent extends ChangeEvent {

	private Producer producer;

	private static final long serialVersionUID = 1L;

	public ChangeProducerEvent(Object source,
			                   Producer producer,
			                   ChangeEntityEventType changeType) {
		super(source, changeType);
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
