package ch.zhaw.wineInventory.service.impl;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.repository.CountryRepository;
import ch.zhaw.wineInventory.service.CountryService;
import ch.zhaw.wineInventory.service.RegionService;

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
public class CountryServiceImpl implements CountryService {

	@Autowired
	private CountryRepository repository;

	@Autowired
	private RegionService regionService;

	/**
	 * Creates a new object.
	 */
	@Override
	public Country save(Country entity) {
		return repository.save(entity);
	}

	@Override
	public Country update(Country entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Country entity) {
		repository.delete(entity);
		for (Region region : entity.getRegions()) {
			regionService.delete(region);
		}

	}

	@Override
	public void delete(Long id) {
		Country country = find(id);
		repository.deleteById(id);
		for (Region region : country.getRegions()) {
			regionService.delete(region);
		}

	}

	@Override
	public void deleteInBatch(List<Country> entities) {
		HashSet<Region> regions = new HashSet<Region>();
		for (Country country : entities) {
			regions.addAll(country.getRegions());
		}
		repository.deleteInBatch(entities);
		for (Region region : regions) {
			regionService.delete(region);
		}
	}

	@Override
	public Country find(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Country> findAll() {
		return repository.findAll();
	}

	@Override
	public Country findByCode(String code) {
		return repository.findByCode(code);
	}

	@Override
	public Country findByName(String name) {
		return repository.findByName(name);
	}

}
