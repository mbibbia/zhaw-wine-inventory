package ch.zhaw.wineInventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for region entities extends interface GenericService
 *         and adds entity specific methods
 *
 */
@Service
public interface RegionService extends GenericService<Region> {

	/**
	 * Returns a region object found with supplied name.
	 * 
	 * @param name
	 * @return Region
	 */
	Region findByName(String name);

	/**
	 * Returns a list of region objects found with supplied country.
	 * 
	 * @param country
	 * @return List of Region
	 */
	List<Region> findByCountry(Country country);

}
