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

import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.repository.WineRepository;
import ch.zhaw.wineInventory.service.WineService;
import ch.zhaw.wineInventory.service.impl.WineServiceImpl;

@RunWith(SpringRunner.class)
public class WineServiceImplIntegrationTest {

	@TestConfiguration
	static class WineServiceImplTestContextConfiguration {

		@Bean
		public WineService wineService() {
			return new WineServiceImpl();
		}

	}

	@Autowired
	private WineService wineService;

	@MockBean
	private WineRepository wineRepository;

	@Before
	public void setUp() {
		Wine wine = new Wine();
		wine.setName("Barolo");

		Mockito.when(wineRepository.findByName(wine.getName())).thenReturn(wine);

	}

	@Test
	public void whenValidName_thenWineShouldBeFound() {

		String name = "Barolo";
		Wine found = wineService.findByName(name);

		assertEquals(found.getName(), name);

	}

}
