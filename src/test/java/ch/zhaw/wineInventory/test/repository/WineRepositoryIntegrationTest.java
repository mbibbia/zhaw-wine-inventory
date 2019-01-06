package ch.zhaw.wineInventory.test.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.repository.WineRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WineRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private WineRepository wineRepository;

	@Test
	public void whenFindByName_thenReturnWine() {

		// given
		Wine newWine = new Wine();
		newWine.setName("Zweifel");
		entityManager.persist(newWine);
		entityManager.flush();

		// when
		Wine found = wineRepository.findByName(newWine.getName());

		// then
		assertEquals(found.getName(), newWine.getName());

	}

	@Test
	public void whenFindByCountry_thenReturnWines() {

		// given

		// Insert country first
		Country country = new Country();
		country.setCode("CH");
		entityManager.persist(country);
		entityManager.flush();

		// Create wine and set previously inserted country
		Wine newWine = new Wine();
		newWine.setCountry(country);
		entityManager.persist(newWine);
		entityManager.flush();

		// when
		List<Wine> found = wineRepository.findByCountry(newWine.getCountry());

		// then
		assertThat(found, hasItems(newWine));

	}

	@Test
	public void whenFindByRegion_thenReturnWines() {

		// given

		// Insert region first
		Region region = new Region();
		region.setName("Piemont");
		entityManager.persist(region);
		entityManager.flush();

		// Create wine and set previously inserted region
		Wine newWine = new Wine();
		newWine.setRegion(region);
		entityManager.persist(newWine);
		entityManager.flush();

		// when
		List<Wine> found = wineRepository.findByRegion(newWine.getRegion());

		// then
		assertThat(found, hasItems(newWine));

	}

	@Test
	public void whenFindByClassification_thenReturnWines() {

		// given

		// Insert classification first
		Classification classification = new Classification();
		classification.setName("DOCG");
		entityManager.persist(classification);
		entityManager.flush();

		// Create wine and set previously inserted classification
		Wine newWine = new Wine();
		newWine.setClassification(classification);
		entityManager.persist(newWine);
		entityManager.flush();

		// when
		List<Wine> found = wineRepository.findByClassification(newWine.getClassification());

		// then
		assertThat(found, hasItems(newWine));

	}

	@Test
	public void whenFindByType_thenReturnWines() {

		// given

		// Insert wine type first
		WineType wineType = new WineType();
		wineType.setName("Rotwein");
		entityManager.persist(wineType);
		entityManager.flush();

		// Create wine and set previously inserted wine type
		Wine newWine = new Wine();
		newWine.setType(wineType);
		entityManager.persist(newWine);
		entityManager.flush();

		// when
		List<Wine> found = wineRepository.findByType(newWine.getType());

		// then
		assertThat(found, hasItems(newWine));

	}

	@Test
	public void whenFindByProducer_thenReturnWines() {

		// given

		// Insert wine type first
		Producer producer = new Producer();
		producer.setName("Zweifel");
		entityManager.persist(producer);
		entityManager.flush();

		// Create wine and set previously inserted wine type
		Wine newWine = new Wine();
		newWine.setProducer(producer);
		entityManager.persist(newWine);
		entityManager.flush();

		// when
		List<Wine> found = wineRepository.findByProducer(newWine.getProducer());

		// then
		assertThat(found, hasItems(newWine));

	}

}
