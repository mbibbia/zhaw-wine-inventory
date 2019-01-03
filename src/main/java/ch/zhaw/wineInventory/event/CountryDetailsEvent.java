package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Country;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a single country is clicked.
 *
 */
public class CountryDetailsEvent extends ApplicationEvent {

	private Country country;

	private static final long serialVersionUID = 1L;

	public CountryDetailsEvent(Object source, Country country) {
		super(source);
		this.country = country;

	}

	/**
	 * Returns pushed country bean.
	 * 
	 * @return Country
	 */
	public Country getCountry() {
		return this.country;
	}

}
