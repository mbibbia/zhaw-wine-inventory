package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Region;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a region is saved.
 *
 */
public class SaveRegionEvent extends ApplicationEvent {

	private Region region;

	private static final long serialVersionUID = 1L;

	public SaveRegionEvent(Object source, Region region) {
		super(source);
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
