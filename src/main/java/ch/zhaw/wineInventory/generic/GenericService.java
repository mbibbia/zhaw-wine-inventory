package ch.zhaw.wineInventory.generic;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 *
 * @param <T>
 * 
 *        Interface with generic persistence methods
 * 
 */
@Service
public interface GenericService<T extends Object> {

	/**
	 * Saves an entity object.
	 * 
	 * @param entity
	 * @return Object
	 */
	T save(T entity);

	/**
	 * Updates an entity object.
	 * 
	 * @param entity
	 * @return Object
	 */
	T update(T entity);

	/**
	 * Deletes an entity object.
	 * 
	 * @param entity
	 */
	void delete(T entity);
	
	/**
	 * Deletes an entity object.
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * Deletes multiple entity objects.
	 * 
	 * @param entities
	 */
	void deleteInBatch(List<T> entities);

	/**
	 * Returns an entity object by id.
	 * 
	 * @param id
	 * @return Object
	 */
	T find(Long id);

	/**
	 * Returns all entity objects.
	 * 
	 * @return List of Object
	 */
	List<T> findAll();

}
