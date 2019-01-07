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

import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.repository.WineTypeRepository;
import ch.zhaw.wineInventory.service.WineTypeService;
import ch.zhaw.wineInventory.service.impl.WineTypeServiceImpl;

@RunWith(SpringRunner.class)
public class WineTypeServiceImplIntegrationTest {

	@TestConfiguration
	static class WineTypeServiceImplTestContextConfiguration {

		@Bean
		public WineTypeService wineTypeService() {
			return new WineTypeServiceImpl();
		}

	}

	@Autowired
	private WineTypeService wineTypeService;

	@MockBean
	private WineTypeRepository wineTypeRepository;

	@Before
	public void setUp() {
		WineType rotwein = new WineType();
		rotwein.setName("Rotwein");

		Mockito.when(wineTypeRepository.findByName(rotwein.getName())).thenReturn(rotwein);
	}

	@Test
	public void whenValidName_thenWineTypeShouldBeFound() {

		String name = "Rotwein";
		WineType found = wineTypeService.findByName(name);

		assertEquals(found.getName(), name);

	}

}
