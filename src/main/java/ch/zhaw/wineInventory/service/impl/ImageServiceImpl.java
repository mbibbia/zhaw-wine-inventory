package ch.zhaw.wineInventory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.wineInventory.bean.Image;
import ch.zhaw.wineInventory.repository.ImageRepository;
import ch.zhaw.wineInventory.service.ImageService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Persistence Service for country entities implements interface
 *         CountryService, uses CountryRepository and implements entity specific
 *         methods.
 *
 */
@Component
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository repository;

	/**
	 * Creates new object.
	 */
	@Override
	public Image save(Image entity) {
		return repository.save(entity);
	}

	/**
	 * Updates existing object
	 */
	@Override
	public Image update(Image entity) {
		return repository.save(entity);
	}

	/**
	 * Deletes an object
	 */
	@Override
	public void delete(Image entity) {
		repository.delete(entity);
	}

	/**
	 * Deletes object with this id
	 */
	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	/**
	 * Deletes multiple objects
	 */
	@Override
	public void deleteInBatch(List<Image> entities) {
		repository.deleteInBatch(entities);
	}

	/**
	 * Find an object by id
	 */
	@Override
	public Image find(Long id) {
		return repository.getOne(id);
	}

	/**
	 * Find all objects
	 */
	@Override
	public List<Image> findAll() {
		return repository.findAll();
	}

	/**
	 * Find an object by name
	 */
	@Override
	public Image findByName(String name) {
		return repository.findByName(name);
	}

}
