package ch.zhaw.wineInventory.test.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.repository.ClassificationRepository;
import ch.zhaw.wineInventory.service.ClassificationService;
import ch.zhaw.wineInventory.service.impl.ClassificationServiceImpl;

@RunWith(SpringRunner.class)
public class ClassificationServiceImplIntegrationTest {

	@TestConfiguration
	static class ClassificationServiceImplTestContextConfiguration {

		@Bean
		public ClassificationService classificationService() {
			return new ClassificationServiceImpl();
		}

	}

	@Autowired
	private ClassificationService classificationService;

	@MockBean
	private ClassificationRepository classificationRepository;

	@Before
	public void setUp() {
		Classification docg = new Classification();
		docg.setName("DOCG");

		Mockito.when(classificationRepository.findByName(docg.getName())).thenReturn(docg);
	}

	@Test
	public void whenValidName_thenClassificationShouldBeFound() {

		String name = "DOCG";
		Classification found = classificationService.findByName(name);

		assertEquals(found.getName(), name);

	}

}
