package ch.zhaw.wineInventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Repository interface for producer beans extends JpaRepository and
 *         adds bean specific methods
 *
 */
@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

	Producer findByName(String name);

	Producer findByCompany(String company);

	List<Producer> findByPlace(String place);

	List<Producer> findByCountry(Country country);

	List<Producer> findByRegion(Region region);

}
