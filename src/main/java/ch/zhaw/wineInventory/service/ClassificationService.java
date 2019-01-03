package ch.zhaw.wineInventory.service;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for classification entities extends interface
 *         GenericService and adds entity specific methods
 *
 */
@Service
public interface ClassificationService extends GenericService<Classification> {

	/**
	 * Returns a classification object found with supplied name.
	 * 
	 * @param name
	 * @return Classification
	 */
	Classification findByName(String name);

}
