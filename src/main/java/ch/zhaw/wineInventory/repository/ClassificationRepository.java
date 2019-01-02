package ch.zhaw.wineInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zhaw.wineInventory.bean.Classification;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Repository interface for classification entities extends JpaRepository
 *         and adds entity specific methods
 *
 */
@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {

	Classification findByName(String name);

}
