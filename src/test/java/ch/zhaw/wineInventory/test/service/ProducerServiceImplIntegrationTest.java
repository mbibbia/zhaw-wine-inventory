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

import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.repository.ProducerRepository;
import ch.zhaw.wineInventory.service.ProducerService;
import ch.zhaw.wineInventory.service.impl.ProducerServiceImpl;

@RunWith(SpringRunner.class)
public class ProducerServiceImplIntegrationTest {

	@TestConfiguration
	static class ProducerServiceImplTestContextConfiguration {

		@Bean
		public ProducerService producerService() {
			return new ProducerServiceImpl();
		}

	}

	@Autowired
	private ProducerService producerService;

	@MockBean
	private ProducerRepository producerRepository;

	@Before
	public void setUp() {
		Producer producer = new Producer();
		producer.setName("Zweifel");
		producer.setCompany("Zweifel Weine");

		Mockito.when(producerRepository.findByName(producer.getName())).thenReturn(producer);
		Mockito.when(producerRepository.findByCompany(producer.getCompany())).thenReturn(producer);

	}

	@Test
	public void whenValidName_thenProducerShouldBeFound() {

		String name = "Zweifel";
		Producer found = producerService.findByName(name);

		assertEquals(found.getName(), name);

	}

	@Test
	public void whenValidCompany_thenProducerShouldBeFound() {

		String company = "Zweifel Weine";
		Producer found = producerService.findByCompany(company);

		assertEquals(found.getCompany(), company);

	}

}
