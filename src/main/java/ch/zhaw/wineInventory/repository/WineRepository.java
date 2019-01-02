package ch.zhaw.wineInventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Repository interface for wine beans extends JpaRepository and adds
 *         bean specific methods
 *
 */
@Repository
public interface WineRepository extends JpaRepository<Wine, Long> {

	List<Wine> findByCountry(Country country);

	List<Wine> findByRegion(Region region);

	List<Wine> findByClassification(Classification classification);

	List<Wine> findByType(WineType wineType);

	List<Wine> findByProducer(Producer producer);

}
