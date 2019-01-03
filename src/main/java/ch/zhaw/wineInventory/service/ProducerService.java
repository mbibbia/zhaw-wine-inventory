package ch.zhaw.wineInventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for producer entities extends interface
 *         GenericService and adds entity specific methods
 *
 */
@Service
public interface ProducerService extends GenericService<Producer> {

	/**
	 * Returns a producer object found with supplied name.
	 * 
	 * @param name
	 * @return Producer
	 */
	Producer findByName(String name);

	/**
	 * Returns a producer object found with supplied company.
	 * 
	 * @param company
	 * @return Producer
	 */
	Producer findByCompany(String company);

	/**
	 * Returns a list of producer objects found with supplied place.
	 * 
	 * @param place
	 * @return List of Producer
	 */
	List<Producer> findByPlace(String place);

	/**
	 * Returns a list of producer objects found with supplied country.
	 * 
	 * @param country
	 * @return List of Producer
	 */
	List<Producer> findByCountry(Country country);

	/**
	 * Returns a list of producer objects found with supplied region.
	 * 
	 * @param region
	 * @return List of Producer
	 */
	List<Producer> findByRegion(Region region);

}
