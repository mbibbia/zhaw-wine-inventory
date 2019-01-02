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
 *         Service interface for region beans extends interface GenericService
 *         and adds bean specific methods
 *
 */
@Service
public interface RegionService extends GenericService<Region> {

	Region findByName(String name);

	List<Region> findByCountry(Country country);

}
