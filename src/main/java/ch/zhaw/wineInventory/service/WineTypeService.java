package ch.zhaw.wineInventory.service;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for wine type beans extends interface
 *         GenericService and adds bean specific methods
 *
 */
@Service
public interface WineTypeService extends GenericService<WineType> {

	WineType findByName(String name);

}
