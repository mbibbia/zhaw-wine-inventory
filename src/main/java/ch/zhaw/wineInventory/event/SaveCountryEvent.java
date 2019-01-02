package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Country;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a country is saved.
 *
 */
public class SaveCountryEvent extends ApplicationEvent {

	private Country country;

	private static final long serialVersionUID = 1L;

	public SaveCountryEvent(Object source, Country country) {
		super(source);
		this.country = country;

	}

	/**
	 * Returns country bean that has been saved.
	 * 
	 * @return Country
	 */
	public Country getCountry() {
		return this.country;
	}

}
