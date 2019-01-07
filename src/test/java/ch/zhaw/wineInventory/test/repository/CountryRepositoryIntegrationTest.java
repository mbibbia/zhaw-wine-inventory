package ch.zhaw.wineInventory.test.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.repository.CountryRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CountryRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CountryRepository countryRepository;

	@Test
	public void whenFindByName_thenReturnCountry() {

		// given
		Country schweiz = new Country();
		schweiz.setName("Schweiz");
		entityManager.persist(schweiz);
		entityManager.flush();

		// when
		Country found = countryRepository.findByName(schweiz.getName());

		// then
		assertEquals(found.getName(), schweiz.getName());

	}

	@Test
	public void whenFindByCode_thenReturnCountry() {

		// given
		Country ch = new Country();
		ch.setCode("CH");
		entityManager.persist(ch);
		entityManager.flush();

		// when
		Country found = countryRepository.findByCode(ch.getCode());

		// then
		assertEquals(found.getCode(), ch.getCode());

	}

}
