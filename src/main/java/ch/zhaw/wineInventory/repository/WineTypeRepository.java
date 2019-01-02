package ch.zhaw.wineInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zhaw.wineInventory.bean.WineType;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Repository interface for wine type beans extends JpaRepository and
 *         adds bean specific methods
 *
 */
@Repository
public interface WineTypeRepository extends JpaRepository<WineType, Long> {

	WineType findByName(String name);

}
