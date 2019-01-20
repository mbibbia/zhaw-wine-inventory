package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Wine;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a wine is saved.
 *
 */
public class ChangeWineEvent extends ApplicationEvent {

	private Wine wine;
	private ChangeEntityEventType type;

	private static final long serialVersionUID = 1L;

	public ChangeWineEvent(Object source, Wine wine, ChangeEntityEventType type) {
		super(source);
		this.wine = wine;
		this.type = type;

	}

	/**
	 * Returns wine bean that has been saved.
	 * 
	 * @return Wine
	 */
	public Wine getWine() {
		return this.wine;
	}

	public ChangeEntityEventType getType() {
		return this.type;
	}

}
