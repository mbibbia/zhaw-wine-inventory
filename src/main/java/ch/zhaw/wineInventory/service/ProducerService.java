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
 *         Service interface for producer beans extends interface GenericService
 *         and adds bean specific methods
 *
 */
@Service
public interface ProducerService extends GenericService<Producer> {

	Producer findByName(String name);

	Producer findByCompany(String company);

	List<Producer> findByPlace(String place);

	List<Producer> findByCountry(Country country);

	List<Producer> findByRegion(Region region);

}
