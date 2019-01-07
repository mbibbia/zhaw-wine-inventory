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

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.repository.ProducerRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProducerRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ProducerRepository producerRepository;

	@Test
	public void whenFindByName_thenReturnProducer() {

		// given
		Producer newProducer = new Producer();
		newProducer.setName("Zweifel");
		entityManager.persist(newProducer);
		entityManager.flush();

		// when
		Producer found = producerRepository.findByName(newProducer.getName());

		// then
		assertEquals(found.getName(), newProducer.getName());

	}

	@Test
	public void whenFindByCompany_thenReturnProducer() {

		// given
		Producer newProducer = new Producer();
		newProducer.setCompany("Zweifel");
		entityManager.persist(newProducer);
		entityManager.flush();

		// when
		Producer found = producerRepository.findByCompany(newProducer.getCompany());

		// then
		assertEquals(found.getCompany(), newProducer.getCompany());

	}

	@Test
	public void whenFindByPlace_thenReturnProducers() {

		// given
		Producer newProducer = new Producer();
		newProducer.setPlace("Zürich");
		entityManager.persist(newProducer);
		Producer newProducer1 = new Producer();
		newProducer1.setPlace("Aarau");
		entityManager.persist(newProducer1);
		entityManager.flush();

		// when
		List<Producer> found = producerRepository.findByPlace(newProducer.getPlace());

		// then
		assertThat(found, hasItems(newProducer));

	}

	@Test
	public void whenFindByCountry_thenReturnProducers() {

		// given

		// Insert country first
		Country country = new Country();
		country.setCode("CH");
		entityManager.persist(country);
		entityManager.flush();

		// Create producer and set previously inserted country
		Producer newProducer = new Producer();
		newProducer.setCountry(country);
		entityManager.persist(newProducer);
		entityManager.flush();

		// when
		List<Producer> found = producerRepository.findByCountry(newProducer.getCountry());

		// then
		assertThat(found, hasItems(newProducer));

	}

	@Test
	public void whenFindByRegion_thenReturnProducers() {

		// given

		// Insert region first
		Region region = new Region();
		region.setName("Piemont");
		entityManager.persist(region);
		entityManager.flush();

		// Create producer and set previously inserted region
		Producer newProducer = new Producer();
		newProducer.setRegion(region);
		entityManager.persist(newProducer);
		entityManager.flush();

		// when
		List<Producer> found = producerRepository.findByRegion(newProducer.getRegion());

		// then
		assertThat(found, hasItems(newProducer));

	}

}
