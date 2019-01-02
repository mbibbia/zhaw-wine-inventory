package ch.zhaw.wineInventory.service;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for wine type entities extends interface
 *         GenericService and adds entity specific methods
 *
 */
@Service
public interface WineTypeService extends GenericService<WineType> {

	/**
	 * Returns a wine type object found with supplied name.
	 * 
	 * @param name
	 * @return WineType
	 */
	WineType findByName(String name);

}
