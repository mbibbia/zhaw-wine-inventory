package ch.zhaw.wineInventory.event;

import ch.zhaw.wineInventory.bean.WineType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a wine type is saved.
 *
 */
public class ChangeWineTypeEvent extends ChangeEvent {

	private WineType wineType;

	private static final long serialVersionUID = 1L;

	public ChangeWineTypeEvent(Object source,
							   WineType wineType,
							   ChangeEntityEventType changeType) {
		super(source, changeType);
		this.wineType = wineType;
	}

	/**
	 * Returns wine type bean that has been saved.
	 * 
	 * @return WineType
	 */
	public WineType getWineType() {
		return this.wineType;
	}

}
