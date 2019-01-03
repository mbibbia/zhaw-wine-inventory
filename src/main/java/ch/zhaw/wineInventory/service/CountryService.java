package ch.zhaw.wineInventory.service;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for country entities extends interface GenericService
 *         and adds entity specific methods
 *
 */
@Service
public interface CountryService extends GenericService<Country> {

	/**
	 * Returns a country object found with supplied code.
	 * 
	 * @param code
	 * @return Country
	 */
	Country findByCode(String code);

	/**
	 * Returns a country object found with supplied name.
	 * 
	 * @param name
	 * @return Country
	 */
	Country findByName(String name);

}
