package ch.zhaw.wineInventory.service;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.generic.GenericService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Service interface for country beans extends interface GenericService
 *         and adds bean specific methods
 *
 */
@Service
public interface CountryService extends GenericService<Country> {

	Country findByCode(String code);

	Country findByName(String name);

}
