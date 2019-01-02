package ch.zhaw.wineInventory.service;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for classification beans extends interface
 *         GenericService and adds bean specific methods
 *
 */
@Service
public interface ClassificationService extends GenericService<Classification> {

	Classification findByName(String name);

}
