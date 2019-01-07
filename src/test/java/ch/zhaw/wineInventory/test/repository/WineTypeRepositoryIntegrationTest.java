package ch.zhaw.wineInventory.test.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.repository.WineTypeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WineTypeRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private WineTypeRepository wineTypeRepository;

	@Test
	public void whenFindByName_thenReturnWineType() {

		// given
		WineType rotwein = new WineType();
		rotwein.setName("Rotwein");
		entityManager.persist(rotwein);
		entityManager.flush();

		// when
		WineType found = wineTypeRepository.findByName(rotwein.getName());

		// then
		assertEquals(found.getName(), rotwein.getName());

	}

}
