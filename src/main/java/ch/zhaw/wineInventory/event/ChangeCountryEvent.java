package ch.zhaw.wineInventory.event;

import ch.zhaw.wineInventory.bean.Country;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a country is saved.
 *
 */
public class ChangeCountryEvent extends ChangeEvent {

	private Country country;

	private static final long serialVersionUID = 1L;

	public ChangeCountryEvent(Object source,
			                  Country country,
			                  ChangeEntityEventType changeType) {
		super(source, changeType);
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
