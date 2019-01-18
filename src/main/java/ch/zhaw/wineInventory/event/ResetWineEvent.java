package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when wine data is reseted.
 *
 */
public class ResetWineEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	public ResetWineEvent(Object source) {
		super(source);

	}

}
