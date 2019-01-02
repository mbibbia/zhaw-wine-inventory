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
 *         Service interface for wine beans extends interface GenericService and
 *         adds bean specific methods
 *
 */
@Service
public interface WineService extends GenericService<Wine> {

	List<Wine> findByCountry(Country country);

	List<Wine> findByRegion(Region region);

	List<Wine> findByClassification(Classification classification);

	List<Wine> findByType(WineType wineType);

	List<Wine> findByProducer(Producer producer);

}
