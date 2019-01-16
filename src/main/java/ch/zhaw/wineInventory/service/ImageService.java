package ch.zhaw.wineInventory.service;

import org.springframework.stereotype.Service;

import ch.zhaw.wineInventory.bean.Image;
import ch.zhaw.wineInventory.generic.GenericService;

@Service
public interface ImageService extends GenericService<Image> {

	Image findByName(String name);

}
