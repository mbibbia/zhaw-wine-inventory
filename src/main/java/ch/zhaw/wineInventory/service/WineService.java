package ch.zhaw.wineInventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for wine entities extends interface GenericService
 *         and adds entity specific methods
 *
 */
@Service
public interface WineService extends GenericService<Wine> {

	/**
	 * Returns a wine object found with supplied name.
	 * 
	 * @param name
	 * @return Wine
	 */
	Wine findByName(String name);

	/**
	 * Returns a list of wine objects found with supplied country.
	 * 
	 * @param place
	 * @return List of Wine
	 */
	List<Wine> findByCountry(Country country);

	/**
	 * Returns a list of wine objects found with supplied region.
	 * 
	 * @param region
	 * @return List of Wine
	 */
	List<Wine> findByRegion(Region region);

	/**
	 * Returns a list of wine objects found with supplied classification.
	 * 
	 * @param classification
	 * @return List of Wine
	 */
	List<Wine> findByClassification(Classification classification);

	/**
	 * Returns a list of wine objects found with supplied wine type.
	 * 
	 * @param wineType
	 * @return List of Wine
	 */
	List<Wine> findByType(WineType wineType);

	/**
	 * Returns a list of wine objects found with supplied producer.
	 * 
	 * @param producer
	 * @return List of Wine
	 */
	List<Wine> findByProducer(Producer producer);

}
