package ch.zhaw.wineInventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Region;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Repository interface for region beans extends JpaRepository and adds
 *         bean specific methods
 *
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {

	Region findByName(String name);

	List<Region> findByCountry(Country country);

}
