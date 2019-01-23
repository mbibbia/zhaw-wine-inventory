package ch.zhaw.wineInventory.event;

import ch.zhaw.wineInventory.bean.Region;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a region is saved.
 *
 */
public class ChangeRegionEvent extends ChangeEvent {

	private Region region;

	private static final long serialVersionUID = 1L;

	public ChangeRegionEvent(Object source,
			                 Region region,
			                 ChangeEntityEventType changeType) {
		super(source, changeType);
		this.region = region;

	}

	/**
	 * Returns region bean that has been saved.
	 * 
	 * @return Region
	 */
	public Region getRegion() {
		return this.region;
	}

}
