package ch.zhaw.wineInventory.test.repository;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.repository.ClassificationRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClassificationRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ClassificationRepository classificationRepository;

	@Test
	public void whenFindByName_thenReturnClassification() {

		// given
		Classification docg = new Classification();
		docg.setName("DOCG");
		entityManager.persist(docg);
		entityManager.flush();

		// when
		Classification found = classificationRepository.findByName(docg.getName());

		// then
		assertEquals(found.getName(), docg.getName());

	}

}
