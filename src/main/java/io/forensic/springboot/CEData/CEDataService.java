package io.forensic.springboot.CEData;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CEDataService {

	@Autowired
	private CEDataRepository cEDataRepository;

	public List<CEData> getAllTopics() {
		// return topics;
		List<CEData> cEDatas = new ArrayList<>();
		cEDataRepository.findAll().forEach(cEDatas::add);
		return cEDatas;
	}

	public Optional<CEData> getPerson(CEDataIdentity id) {
		return cEDataRepository.findById(id);
	}

	public void addPerson(CEData cEData) {
		cEDataRepository.save(cEData);
	}

	public void updatePerson(String id, CEData cEData) {
		cEDataRepository.save(cEData);
	}

	public void readExcelData(String fileName) {
		try {
			// Create the input stream from the xlsx/xls file
			FileInputStream fis = new FileInputStream(fileName);

			// Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if (fileName.toLowerCase().endsWith("xlsx")) {
				workbook = new XSSFWorkbook(fis);
			} else if (fileName.toLowerCase().endsWith("xls")) {
				workbook = new HSSFWorkbook(fis);
			}

			// Get the number of sheets in the xlsx file
			int numberOfSheets = workbook.getNumberOfSheets();
			String sampleYear = null; // Collect sample_year
			String sampleId = null; // Collect sample_id
			// loop through each of the sheets
			for (int i = 0; i < numberOfSheets; i++) {

				// Get the nth sheet from the workbook
				Sheet sheet = workbook.getSheetAt(i);

				// First Sheet : AutosomalKit STRs
				if (sheet.getSheetName().equals("Autosomal STRs")) {
//					System.out.println("This is Autosomal STRs");
					// every sheet has rows, iterate over them
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();

						// Line 1 to 7
						if (line >= 1 && line <= 7) {
//							System.out.println("this is Line::"+line);
							List<String> data = new ArrayList<>();
							while (cellIterator.hasNext()) {
								// Get the Cell object
								Cell cell = cellIterator.next();
								// check the cell type and process accordingly
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									data.add(cell.getStringCellValue());
//									System.out.print(cell.getStringCellValue() + ",");
									break;
								case Cell.CELL_TYPE_NUMERIC:
									data.add("" + cell.getNumericCellValue());
//									System.out.print(cell.getNumericCellValue() + ",");
								}
							} // end of cell iterator
							if (data.get(0).equals("Created")) {
								String[] tmp = data.get(1).split(" ");
								sampleYear = tmp[2];
							}
							if (data.get(0).equals("Sample")) {
								sampleId = data.get(1);
							}
						}

						// Line 14 to 41 Collect CE_Data
						if (line >= 13 && line <= 40) {
							List<String> data = new ArrayList<>();
//							System.out.println("this is Line::"+line);
							while (cellIterator.hasNext()) {
								// Get the Cell object
								Cell cell = cellIterator.next();
								// check the cell type and process accordingly
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									data.add(cell.getStringCellValue());
//									System.out.print(cell.getStringCellValue() + ",");
									break;
								case Cell.CELL_TYPE_NUMERIC:
									data.add("" + cell.getNumericCellValue());
//									System.out.print(cell.getNumericCellValue() + ",");
									break;
								}
							} // end of cell iterator
//							System.out.println("data.get(0)::"+data.get(0));
//							System.out.println("data.get(1)::"+data.get(1));
//							System.out.println("sample_year::"+sampleYear);
//							System.out.println("sample_id::"+sampleId);
							cEDataRepository.save(new CEData(
									new CEDataIdentity(sampleYear, sampleId, data.get(0), data.get(1), "Forenseq"),
									"Autosomal"));
//							System.out.println("Print4");
						}

						line += 1; // Counting Line (Add 1/Loop)
//						System.out.println();
					} // end of rows iterator
				}

				// Second Sheet : Y STRs
				if (sheet.getSheetName().equals("Y STRs")) {
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						Map<String, String> locus = new HashMap<String, String>();

						// Line 13 to 36 Collect CE_Data of iSNPs
						if (line >= 13 && line <= 36) {
							List<String> data = new ArrayList<>();
//							System.out.println("\nthis is Line::" + line);
							while (cellIterator.hasNext()) {
								// Get the Cell object
								Cell cell = cellIterator.next();
								// check the cell type and process accordingly
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									data.add(cell.getStringCellValue());
//									System.out.print(cell.getStringCellValue() + ",");
									break;
								case Cell.CELL_TYPE_NUMERIC:
									data.add("" + cell.getNumericCellValue());
//									System.out.print(cell.getNumericCellValue() + ",");
									break;
								}
							} // end of cell iterator
							locus.put(data.get(0), data.get(1));
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("locus.size()::" + locus.size());
//							System.out.println("PUTED DATA-0 DATA-1");
							cEDataRepository.save(new CEData(
									new CEDataIdentity(sampleYear, sampleId, data.get(0), data.get(1), "Forenseq"),
									"Y"));
						}

						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

				// Third Sheet : X STRs
				if (sheet.getSheetName().equals("X STRs")) {
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						Map<String, String> locus = new HashMap<String, String>();

						// Line 13 to 36 Collect CE_Data of iSNPs
						if (line >= 13 && line <= 19) {
							List<String> data = new ArrayList<>();
//							System.out.println("\nthis is Line::" + line);
							while (cellIterator.hasNext()) {
								// Get the Cell object
								Cell cell = cellIterator.next();
								// check the cell type and process accordingly
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									data.add(cell.getStringCellValue());
//									System.out.print(cell.getStringCellValue() + ",");
									break;
								case Cell.CELL_TYPE_NUMERIC:
									data.add("" + cell.getNumericCellValue());
//									System.out.print(cell.getNumericCellValue() + ",");
									break;
								}
							} // end of cell iterator
							locus.put(data.get(0), data.get(1));
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("locus.size()::" + locus.size());
//							System.out.println("PUTED DATA-0 DATA-1");
							cEDataRepository.save(new CEData(
									new CEDataIdentity(sampleYear, sampleId, data.get(0), data.get(1), "Forenseq"),
									"X"));
						}

						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

				// Forth Sheet : iSNPs
				if (sheet.getSheetName().equals("iSNPs")) {
					Iterator<Row> rowIterator = sheet.iterator();
					// Tracking Current Line
					int line = 1;
					while (rowIterator.hasNext()) {
						// Get the row object
						Row row = rowIterator.next();
						// Every row has columns, get the column iterator and iterate over them
						Iterator<Cell> cellIterator = row.cellIterator();
						Map<String, String> locus = new HashMap<String, String>();

						// Line 14 to 41 Collect CE_Data of iSNPs
						if (line >= 13 && line <= 106) {
							List<String> data = new ArrayList<>();
//							System.out.println("\nthis is Line::" + line);
							while (cellIterator.hasNext()) {
								// Get the Cell object
								Cell cell = cellIterator.next();
								// check the cell type and process accordingly
								switch (cell.getCellType()) {
								case Cell.CELL_TYPE_STRING:
									data.add(cell.getStringCellValue());
//									System.out.print(cell.getStringCellValue() + ",");
									break;
								case Cell.CELL_TYPE_NUMERIC:
									data.add("" + cell.getNumericCellValue());
//									System.out.print(cell.getNumericCellValue() + ",");
									break;
								}
							} // end of cell iterator
							locus.put(data.get(0), data.get(1));
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("locus.size()::" + locus.size());
//							System.out.println("PUTED DATA-0 DATA-1");
							cEDataRepository.save(new CEData(
									new CEDataIdentity(sampleYear, sampleId, data.get(0), data.get(1), "Forenseq"),
									"iSNPs"));
						}

						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

			} // end of sheets for loop

			// close file input stream
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void deletePerson(CEDataIdentity id) {
		cEDataRepository.deleteById(id);
	}
}
