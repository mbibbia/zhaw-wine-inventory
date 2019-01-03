package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Region;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a single region is clicked.
 *
 */
public class RegionDetailsEvent extends ApplicationEvent {

	private Region region;

	private static final long serialVersionUID = 1L;

	public RegionDetailsEvent(Object source, Region region) {
		super(source);
		this.region = region;

	}

	/**
	 * Returns pushed region bean.
	 * 
	 * @return Region
	 */
	public Region getRegion() {
		return this.region;
	}

}
