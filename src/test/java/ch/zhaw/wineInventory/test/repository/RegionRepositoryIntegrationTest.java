package ch.zhaw.wineInventory.test.repository;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.repository.RegionRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RegionRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RegionRepository regionRepository;

	@Test
	public void whenFindByName_thenReturnRegion() {

		// given
		Region newRegion = new Region();
		newRegion.setName("Piemont");
		entityManager.persist(newRegion);
		entityManager.flush();

		// when
		Region found = regionRepository.findByName(newRegion.getName());

		// then
		assertEquals(found.getName(), newRegion.getName());

	}

	@Test
	public void whenFindByCountry_thenReturnRegion() {

		// given

		// Insert country first
		Country country = new Country();
		country.setCode("IT");
		entityManager.persist(country);
		entityManager.flush();

		// Create producer and set previously inserted country
		Region newRegion = new Region();
		newRegion.setCountry(country);
		entityManager.persist(newRegion);
		entityManager.flush();

		// when
		List<Region> found = regionRepository.findByCountry(newRegion.getCountry());

		// then
		assertThat(found, hasItems(newRegion));

	}

}
