package ch.zhaw.wineInventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.IOException;
import ch.zhaw.wineInventory.bean.Classification;
import ch.zhaw.wineInventory.bean.Country;
import ch.zhaw.wineInventory.bean.Producer;
import ch.zhaw.wineInventory.bean.Region;
import ch.zhaw.wineInventory.bean.Wine;
import ch.zhaw.wineInventory.bean.WineType;
import ch.zhaw.wineInventory.config.AppJavaConfig;
import ch.zhaw.wineInventory.service.ClassificationService;
import ch.zhaw.wineInventory.service.CountryService;
import ch.zhaw.wineInventory.service.ProducerService;
import ch.zhaw.wineInventory.service.RegionService;
import ch.zhaw.wineInventory.service.WineService;
import ch.zhaw.wineInventory.service.WineTypeService;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         This class is used to preload data when application is started.
 *         Please note that this will work only when H2 Database is configured!
 *
 */
@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	AppJavaConfig config;

	@Autowired
	private WineTypeService wineTypeService;

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private RegionService regionService;

	@Autowired
	private ProducerService producerService;

	@Autowired
	private WineService wineService;

	@Autowired
	public DataLoader() {

	}

	/**
	 * Method is processed implicitly and runs implemented methods.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {

		initWineTypes();
		initClassifications();
		initCountries();
		initRegions();
		initProducers();
		initWine();

	}

	private void initWine() {

		try {
			Workbook workbook = WorkbookFactory.create(new File("src/test/resources/data/Wine.xlsx"));

			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();

			sheet.forEach(row -> {

				if (row.getRowNum() == 0) {
					return;
				}

				Wine wine;
				wine = new Wine();

				row.forEach(cell -> {
					String cellValue = dataFormatter.formatCellValue(cell);
					switch (cell.getColumnIndex()) {
					case 0: // name
						wine.setName(cellValue);
					case 1: // type
						wine.setType(wineTypeService.findByName(cellValue));
					case 2: // classification
						wine.setClassification(classificationService.findByName(cellValue));
					default:
						break;

					}

				});
				wineService.save(wine);
			});

			// Closing the workbook
			workbook.close();

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initWineTypes() {

		WineType wineType;

		wineType = new WineType();
		wineType.setName("Rotwein");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Weisswein");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Rosé");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Schaumwein weiss");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Schaumwein rosé");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Süsswein rot");
		wineTypeService.save(wineType);

		wineType = new WineType();
		wineType.setName("Süsswein weiss");
		wineTypeService.save(wineType);

	}

	private void initClassifications() {

		Classification classification;

		classification = new Classification();
		classification.setName("DOC");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("DOCG");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("IGT");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("VdT");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("DOP");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("1er grand cru classé");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("2ème grand cru classé");
		classificationService.save(classification);

		classification = new Classification();
		classification.setName("AOC");
		classificationService.save(classification);

	}

	private void initCountries() {

		try {
			Workbook workbook = WorkbookFactory.create(new File("src/test/resources/data/Country.xlsx"));

			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();

			sheet.forEach(row -> {

				if (row.getRowNum() == 0) {
					return;
				}

				Country country;
				country = new Country();

				row.forEach(cell -> {
					String cellValue = dataFormatter.formatCellValue(cell);
					switch (cell.getColumnIndex()) {
					case 0:
						country.setCode(cellValue);
					case 1:
						country.setName(cellValue);
					default:
						break;

					}

				});
				countryService.save(country);
			});

			// Closing the workbook
			workbook.close();

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initRegions() {

		try {
			Workbook workbook = WorkbookFactory.create(new File("src/test/resources/data/Region.xlsx"));

			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();

			sheet.forEach(row -> {

				if (row.getRowNum() == 0) {
					return;
				}

				Region region;
				region = new Region();

				row.forEach(cell -> {
					String cellValue = dataFormatter.formatCellValue(cell);
					switch (cell.getColumnIndex()) {
					case 0:
						region.setCountry(countryService.findByCode(cellValue));
					case 1:
						region.setName(cellValue);
					default:
						break;

					}

				});
				regionService.save(region);
			});

			// Closing the workbook
			workbook.close();

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initProducers() {

		try {
			Workbook workbook = WorkbookFactory.create(new File("src/test/resources/data/Producer.xlsx"));

			Sheet sheet = workbook.getSheetAt(0);
			DataFormatter dataFormatter = new DataFormatter();

			sheet.forEach(row -> {

				if (row.getRowNum() == 0) {
					return;
				}

				Producer producer;
				producer = new Producer();

				row.forEach(cell -> {
					String cellValue = dataFormatter.formatCellValue(cell);
					switch (cell.getColumnIndex()) {
					case 0: // name
						if (cellValue.isEmpty()) {
							return;
						}
						producer.setName(cellValue);
					case 1: // company
						producer.setCompany(cellValue);
					case 2: // addressline1
						producer.setAddressLine1(cellValue);
					case 3: // addressline2
						producer.setAddressLine2(cellValue);
					case 4: // zipCode
						producer.setZipCode(cellValue);
					case 5: // place
						producer.setPlace(cellValue);
					case 6: // country
						producer.setCountry(countryService.findByCode(cellValue));
					case 7: // region
						producer.setRegion(regionService.findByName(cellValue));
					case 8: // phone
						producer.setPhone(cellValue);
					case 9: // fax
						producer.setFax(cellValue);
					case 10: // email
						producer.setEmail(cellValue);
					case 11: // website
						producer.setUrl(cellValue);
					default:
						break;

					}

				});
				producerService.save(producer);
			});

			// Closing the workbook
			workbook.close();

		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
