package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.WineType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a wine type is saved.
 *
 */
public class WineTypeSaveEvent extends ApplicationEvent {

	private WineType wineType;

	private static final long serialVersionUID = 1L;

	public WineTypeSaveEvent(Object source, WineType wineType) {
		super(source);
		this.wineType = wineType;

	}

	/**
	 * Returns wine type bean that has been saved.
	 * 
	 * @return WineType
	 */
	public WineType getClassification() {
		return this.wineType;
	}

}
