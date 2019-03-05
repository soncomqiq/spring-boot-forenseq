package io.forensic.springboot.Other;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.forensic.springboot.CEData.CEData;
import io.forensic.springboot.CEData.CEDataIdentity;
import io.forensic.springboot.forenseq.Forenseq;
import io.forensic.springboot.forenseq.ForenseqIdentity;
import io.forensic.springboot.forenseqX.ForenseqX;
import io.forensic.springboot.forenseqX.ForenseqXIdentity;
import io.forensic.springboot.forenseqY.ForenseqY;
import io.forensic.springboot.forenseqY.ForenseqYIdentity;
import io.forensic.springboot.person.Person;

@Service
public class OtherService {

	@Autowired
	private OtherRepository repository;

	public List<String> getAllAutosomalKit() {
		return repository.findDistinctAutosomalKit();
	}

	public List<String> getAllYKit() {
		return repository.findDistinctYKit();
	}

	public List<String> getAllXKit() {
		return repository.findDistinctXKit();
	}

	public List<String> getAllLocusAutosom() {
		return repository.findLocusAutosom();
	}

	public List<String> getAllLocusY() {
		return repository.findLocusY();
	}

	public List<String> getAllLocusX() {
		return repository.findLocusX();
	}

	public List<Object[]> searchByExcelData(String fileName) {
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

			List<RequestLocus> searchLocus = new ArrayList<RequestLocus>();

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
						}

						// Collect iSNPs Data
						if (line >= 43) {
							List<String> data = new ArrayList<>();
//							System.out.println("this is Line::" + line);
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
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("data.get(2)::" + data.get(2));
//							System.out.println("data.get(3)::" + data.get(3));
//							System.out.println("data.get(4)::" + data.get(4));
//							System.out.println("locus.get(data.get(0))::" + locus.size());
							if (data.get(2).equalsIgnoreCase("Yes")) {
								searchLocus.add(new RequestLocus(data.get(0), data.get(1)));
							}
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
						}

						if (line >= 39) {
//							System.out.println("this is Line::" + line);
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
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("data.get(2)::" + data.get(2));
//							System.out.println("data.get(3)::" + data.get(3));
//							System.out.println("data.get(4)::" + data.get(4));
							if (data.get(2).equalsIgnoreCase("Yes")) {
								searchLocus.add(new RequestLocus(data.get(0), data.get(1)));
							}
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

						}

						// Line 22 and above
						if (line >= 22) {
//							System.out.println("this is Line::" + line);
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
//							System.out.println("data.get(0)::" + data.get(0));
//							System.out.println("locus.get(data.get(0))::" + locus.get(data.get(0)));
//							System.out.println("data.get(1)::" + data.get(1));
//							System.out.println("data.get(2)::" + data.get(2));
//							System.out.println("data.get(3)::" + data.get(3));
							if (data.get(2).equalsIgnoreCase("Yes")) {
								searchLocus.add(new RequestLocus(data.get(0), data.get(1)));
							}
						}

						line += 1; // Counting Line (Add 1/Loop)
					} // end of rows iterator
				}

			} // end of sheets for loop
			fis.close();
			List<Object[]> result = getPersonByLocus(searchLocus);
			return result;
			// close file input stream

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<LocusInfoGraph> getStatsGraph(String locus, String table) {
		List<LocusInfoGraph> result = new ArrayList<LocusInfoGraph>();
		List<Object[]> tmp = null;
		if (table.equalsIgnoreCase("Autosom")) {
			tmp = repository.findStatsGraphA(locus);
		} else if (table.equalsIgnoreCase("Y_STRs")) {
			tmp = repository.findStatsGraphY(locus);
		} else if (table.equalsIgnoreCase("X_STRs")) {
			tmp = repository.findStatsGraphX(locus);
		}
		for (int i = 0; i < tmp.size(); i++) {
			result.add(new LocusInfoGraph(tmp.get(i)[0].toString(), tmp.get(i)[1].toString()));
		}
		return result;
	}

	public List<Object[]> getPersonByLocus(List<RequestLocus> locus) {
		String query = "SELECT b.Sample_Year, b.Sample_ID FROM (SELECT a.Sample_Year, a.Sample_ID, COUNT(*) AS amount FROM (SELECT * FROM (select * from forenseq UNION select * from forenseqY UNION select * from forenseqX) tmp WHERE ";
		int length = locus.size();
		for (int i = 0; i < length; i++) {
			RequestLocus rlc = locus.get(i);
			if (i == 0) {
				query += " tmp._type = \"Yes\" and ( tmp.locus = \"" + rlc.getLocus() + "\" and" + " tmp.allele = "
						+ rlc.getAllele() + " )";
			} else {
				query += " or ( tmp.locus = \"" + rlc.getLocus() + "\" and" + " tmp.allele = " + rlc.getAllele() + " )";
			}
		}
		query += ") a GROUP BY a.Sample_Year, a.Sample_ID) b WHERE b.amount = " + length + ";";
		System.out.println(query);
		return repository.mycustomQueryY(query);
	}

	public List<String> getLocusAutosomalKit(String kit) {
		return repository.getLocusAutosomalKit(kit);
	}

	public List<String> getLocusYKit(String kit) {
		return repository.getLocusYKit(kit);
	}

	public List<String> getLocusXKit(String kit) {
		return repository.getLocusXKit(kit);
	}

	public List<Hetero> getHetero() {
		List<Object[]> tmp = repository.getHetero();
		List<Hetero> result = new ArrayList<Hetero>();
		for (int i = 0; i < tmp.size(); i++) {
			if (tmp.get(i)[2] != null) {
				result.add(new Hetero(tmp.get(i)[0].toString(), tmp.get(i)[1].toString(), tmp.get(i)[2].toString()));
			} else {
				result.add(new Hetero(tmp.get(i)[0].toString(), tmp.get(i)[1].toString(), "0"));
			}
		}
		return result;
	}
}
