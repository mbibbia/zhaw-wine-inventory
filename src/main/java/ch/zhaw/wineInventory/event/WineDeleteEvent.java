package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Wine;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a wine is deleted.
 *
 */
public class WineDeleteEvent extends ApplicationEvent {

	private Wine wine;

	private static final long serialVersionUID = 1L;

	public WineDeleteEvent(Object source, Wine wine) {
		super(source);
		this.wine = wine;

	}

	/**
	 * Returns wine bean that has been deleted.
	 * 
	 * @return Wine
	 */
	public Wine getWine() {
		return this.wine;
	}

}
