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

import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.repository.CountryRepository;
import ch.zhaw.wineInventory.repository.RegionRepository;
import ch.zhaw.wineInventory.service.RegionService;
import ch.zhaw.wineInventory.service.impl.RegionServiceImpl;

@RunWith(SpringRunner.class)
public class RegionServiceImplIntegrationTest {

	@TestConfiguration
	static class RegionServiceImplTestContextConfiguration {

		@Bean
		public RegionService regionService() {
			return new RegionServiceImpl();
		}

	}

	@Autowired
	private RegionService regionService;

	@MockBean
	private RegionRepository regionRepository;

	@MockBean
	private CountryRepository countryRepository;

	@Before
	public void setUp() {

		Region region = new Region();
		region.setName("Piemont");

		Mockito.when(regionRepository.findByName(region.getName())).thenReturn(region);

	}

	@Test
	public void whenValidName_thenRegionShouldBeFound() {

		String name = "Piemont";
		Region found = regionService.findByName(name);

		assertEquals(found.getName(), name);

	}

}
