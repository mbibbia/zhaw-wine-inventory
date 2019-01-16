package ch.zhaw.wineInventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ch.zhaw.wineInventory.bean.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

	Image findByName(String name);

}
