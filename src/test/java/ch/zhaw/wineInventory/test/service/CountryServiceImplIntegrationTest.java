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

import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.repository.CountryRepository;
import ch.zhaw.wineInventory.service.CountryService;
import ch.zhaw.wineInventory.service.impl.CountryServiceImpl;

@RunWith(SpringRunner.class)
public class CountryServiceImplIntegrationTest {

	@TestConfiguration
	static class CountryServiceImplTestContextConfiguration {

		@Bean
		public CountryService countryService() {
			return new CountryServiceImpl();
		}

	}

	@Autowired
	private CountryService countryService;

	@MockBean
	private CountryRepository countryRepository;

	@Before
	public void setUp() {
		Country country = new Country();
		country.setCode("CH");
		country.setName("Schweiz");

		Mockito.when(countryRepository.findByCode(country.getCode())).thenReturn(country);
		Mockito.when(countryRepository.findByName(country.getName())).thenReturn(country);
	}

	@Test
	public void whenValidCode_thenCountryShouldBeFound() {

		String code = "CH";
		Country found = countryService.findByCode(code);

		assertEquals(found.getCode(), code);

	}

	@Test
	public void whenValidName_thenCountryShouldBeFound() {

		String name = "Schweiz";
		Country found = countryService.findByName(name);

		assertEquals(found.getName(), name);

	}

}
