package io.forensic.springboot.Other;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
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

	public List<iSNPRespone> findISNPStat() {
		List<iSNPRespone> result = new ArrayList<iSNPRespone>();
		List<Object[]> tmp = repository.findISNPStat();
		for (int i = 0; i < tmp.size(); i++) {
			result.add(new iSNPRespone(tmp.get(i)[0].toString(), tmp.get(i)[1].toString(),
					Integer.parseInt(tmp.get(i)[2].toString())));
		}
		return result;
	}

	public List<Alignment> getAlignment(String locus, String allele) {
		Map<String, List<String>> Motif = new HashMap<String, List<String>>();
		// Autosomal
		Motif.put("CSF1PO", Arrays.asList("1AGAT"));
		Motif.put("D13S317", Arrays.asList("1TATC"));
		Motif.put("D16S539", Arrays.asList("1GATA"));
		Motif.put("D18S51", Arrays.asList("1AGAA"));
		Motif.put("D21S11",
				Arrays.asList("1TCTA", "1TCTG", "1TCTA", "2TA", "1TCTA", "2TCA", "1TCTA", "2TCCATA", "1TCTA"));
		Motif.put("D3S1358", Arrays.asList("2TCTA", "1TCTG", "1TCTA"));
		Motif.put("D5S818", Arrays.asList("1AGAT"));
		Motif.put("D7S820", Arrays.asList("1GATA"));
		Motif.put("D8S1179", Arrays.asList("1TCTA", "1TCTG", "1TCTA"));
		Motif.put("FGA", Arrays.asList("1TTTC", "2TTTTTTCT", "1CTTT", "2CTCC", "1TTCC"));
		Motif.put("TH01", Arrays.asList("1AATG"));
		Motif.put("TPOX", Arrays.asList("1AATG"));
		Motif.put("vWA", Arrays.asList("1TCTA", "1TCTG", "1TCTA", "2TCCA", "1TCTA"));
		Motif.put("D2S1338", Arrays.asList("1TGCC", "1TTCC"));
		Motif.put("D19S433", Arrays.asList("2AAGG", "2AAAG", "2AAGG", "2TAGG", "1AAGG"));
		Motif.put("Penta D", Arrays.asList("1AAAGA"));
		Motif.put("Penta E", Arrays.asList("1AAAGA"));
		Motif.put("D10S1248", Arrays.asList("1GGAA"));
		Motif.put("D1S1656", Arrays.asList("1TAGA", "1TAGG", "1TG"));
		Motif.put("D12S391", Arrays.asList("1AGAT", "1AGAC", "2AGAT"));
		Motif.put("D2S441", Arrays.asList("1TCTA"));
		Motif.put("D22S1045", Arrays.asList("1ATT", "1ACT", "1ATT"));
		Motif.put("SE33", Arrays.asList("1AAAG", "2AG", "1AAAG", "2AG", "1AAAG", "2G", "1AAAG", "2AG"));
		Motif.put("D6S1043", Arrays.asList("1AGAT"));
		Motif.put("D17S1301", Arrays.asList("3None"));
		Motif.put("D20S482", Arrays.asList("3None"));
		Motif.put("D4S2408", Arrays.asList("3None"));
		Motif.put("D9S1122", Arrays.asList("3None"));
		// Y_somal
		Motif.put("DYS19", Arrays.asList("1TAGA"));
		Motif.put("DYS385a-b", Arrays.asList("1GAAA"));
		Motif.put("DYS389I", Arrays.asList("1TCTG", "1TCTA"));
		Motif.put("DYF387S1", Arrays.asList("3None"));
		Motif.put("DYS390", Arrays.asList("1TCTA", "1TCTG"));
		Motif.put("DYS391", Arrays.asList("1TCTA"));
		Motif.put("DYS392", Arrays.asList("1TAT"));
		Motif.put("DYS393", Arrays.asList("1AGAT"));
		Motif.put("DYS437", Arrays.asList("1TCTA"));
		Motif.put("DYS438", Arrays.asList("1TTTTC"));
		Motif.put("DYS439", Arrays.asList("1AGAT"));
		Motif.put("DYS448", Arrays.asList("1AGAGAT"));
		Motif.put("DYS449", Arrays.asList("3None"));
		Motif.put("DYS456", Arrays.asList("1AGAT"));
		Motif.put("DYS458", Arrays.asList("1GAAA"));
		Motif.put("DYS460", Arrays.asList("1ATAG"));
		Motif.put("DYS481", Arrays.asList("1CTT"));
		Motif.put("DYS505", Arrays.asList("1TCCT"));
		Motif.put("DYS518", Arrays.asList("3None"));
		Motif.put("DYS522", Arrays.asList("1GATA"));
		Motif.put("DYS533", Arrays.asList("1ATCT"));
		Motif.put("DYS549", Arrays.asList("1GATA"));
		Motif.put("DYS570", Arrays.asList("1TTTC"));
		Motif.put("DYS576", Arrays.asList("1AAAG"));
		Motif.put("DYS612", Arrays.asList("1CCT", "1CTT", "1TCT", "1CCT", "1TCT"));
		Motif.put("DYS627", Arrays.asList("3None"));
		Motif.put("DYS635", Arrays.asList("1TSTA"));
		Motif.put("DYS643", Arrays.asList("1CTTTT"));
		Motif.put("Y-GATA-H4", Arrays.asList("1TAGA"));
		// X_Somal
		Motif.put("DXS7132", Arrays.asList("1TCTA"));
		Motif.put("DXS7423", Arrays.asList("1TCCA", "2TCTGTCCT", "1TCCA"));
		Motif.put("DXS8378", Arrays.asList("1CTAT"));
		Motif.put("DXS10074", Arrays.asList("1AAGA"));
		Motif.put("DXS10079", Arrays.asList("1AGAG", "2TGAAAGAG", "1AGAA", "2AGAG", "1AGAA"));
		Motif.put("DXS10101", Arrays.asList("1AAAG", "2GAAAGAAG", "1GAAA", "2A", "1GAAA", "2AAGA", "1AAAG", "2AAAAAGAA",
				"1AAAG", "2AA"));
		Motif.put("DXS10103", Arrays.asList("1TAGA", "2CTGA", "2CAGA", "1TAGA", "1CAGA", "2TAGA"));
		Motif.put("DXS10134", Arrays.asList("1GAAA", "2GAGA", "1GAAA", "2AA", "2GAAA", "2GAGA", "1GAAA", "2GAGA",
				"1GACAGA", "2GAAA", "2GTAA", "1GAAA", "2AAA", "1GAAA", "2AAA", "1GAAA"));
		Motif.put("DXS10135", Arrays.asList("1AAGA", "2GAAAG", "1GAAA"));
		Motif.put("DXS10146", Arrays.asList("1TTCC", "2T", "1TTCC", "2TTTC", "2CTCCCTTCC", "2TTCC", "2TCCC",
				"2TTCTTCTTTC", "1TTCC", "2TTTCTT", "1CTTT", "2CTTC", "1CTTT", "2T", "1CTTT"));
		Motif.put("DXS10148", Arrays.asList("1GGAA", "1AAGA", "1AAAG", "1AAGG"));
		Motif.put("HPRTB", Arrays.asList("1AGAT"));

		List<Object[]> tmp = repository.findAllForenseqTable(locus, Float.parseFloat(allele));
		List<Alignment> result = new ArrayList<Alignment>();

		for (int i = 0; i < tmp.size(); i++) {
			int array_iterate = 0;
			int sequence_iterate = 0;
			int count = 0;
			int count_total = 0;
			String seqAlignment = "";
			String sequence = tmp.get(i)[7].toString();
			while (array_iterate < Motif.get(locus).size()) {
				if (count_total == (int) Float.parseFloat(allele)) {
					seqAlignment += "(" + Motif.get(locus).get(array_iterate).substring(1) + ")" + count + " ";
					array_iterate += 1;
					count_total += count;
					count = 0;
					seqAlignment += sequence.substring(sequence_iterate);
				} else {
					if (Motif.get(locus).get(array_iterate).substring(0, 1).equals("1")) {
						if (sequence
								.substring(sequence_iterate,
										sequence_iterate + Motif.get(locus).get(array_iterate).substring(1).length())
								.equals(Motif.get(locus).get(array_iterate).substring(1))) {
							count += 1;
							count_total += 1;
							sequence_iterate += Motif.get(locus).get(array_iterate).substring(1).length();
						} else {
							seqAlignment += "(" + Motif.get(locus).get(array_iterate).substring(1) + ")" + count + " ";
							array_iterate += 1;
							count = 0;
						}
					} else if (Motif.get(locus).get(array_iterate).substring(0, 1).equals("2")) {
						seqAlignment += sequence.substring(sequence_iterate,
								sequence_iterate + Motif.get(locus).get(array_iterate).substring(1).length()) + " ";
						array_iterate += 1;
						sequence_iterate += Motif.get(locus).get(array_iterate).substring(1).length();
					} else if (Motif.get(locus).get(array_iterate).substring(0, 1).equals("3")) {
						seqAlignment += "No Repeated Data";
						break;
					}
				}
			}
			result.add(new Alignment(tmp.get(i)[0].toString(), tmp.get(i)[1].toString(), tmp.get(i)[2].toString(),
					locus, allele, tmp.get(i)[5].toString(), tmp.get(i)[6].toString(), sequence, seqAlignment));
		}

		System.out.println("tmp::" + tmp);
		System.out.println("Locus::" + locus);
		System.out.println("Allele::" + allele);

		return result;
	}

	public List<RequestLocus> getLocusListAlign() {
		List<String> tmp = repository.findDistinctLocus();
		List<RequestLocus> result = new ArrayList<RequestLocus>();
		for (int i = 0; i < tmp.size(); i++) {
			result.add(new RequestLocus(tmp.get(i), null));
		}

		return result;
	}

	public List<RequestLocus> getAlleleInfoAlign() {
		List<Object[]> tmp = repository.findAlleleInfo();
		List<RequestLocus> result = new ArrayList<RequestLocus>();
		for (int i = 0; i < tmp.size(); i++) {
			result.add(new RequestLocus(tmp.get(i)[0].toString(), tmp.get(i)[1].toString()));
		}

		return result;
	}

	public List<MapStats> getStatisticMap(String locus) {
		List<MapStats> result = new ArrayList<MapStats>();
		List<String> color = new ArrayList<String>();
		Map<String, String> mapColor = new HashMap<String, String>();
		// Add Colors
		color.add("rgba(255,87,34,0.8)");
		color.add("rgba(255,0,0,0.8)");
		color.add("rgba(255,255,0,0.8)");
		color.add("rgba(255,0,255,0.8)");
		color.add("rgba(0,0,255,0.8)");
		color.add("rgba(0,255,0,0.8)");
		color.add("rgba(128,0,128,0.8)");
		color.add("rgba(0,255,255,0.8)");
		color.add("rgba(128,128,0,0.8)");
		color.add("rgba(0,0,128,0.8)");
		color.add("rgba(88,214,141,0.8)");
		color.add("rgba(52,152,219,0.8)");
		color.add("rgba(236,112,99,0.8)");
		color.add("rgba(220,118,51,0.8)");
		List<Object[]> tmp = repository.findAlleleMap(locus);
		int count_color = 0;
		for (int i = 0; i < tmp.size(); i++) {
			if (mapColor.get(tmp.get(i)[0].toString()) == null) {
				mapColor.put(tmp.get(i)[0].toString(), color.get(count_color++));
			}
			result.add(new MapStats(tmp.get(i)[0].toString(), tmp.get(i)[1].toString(), tmp.get(i)[2].toString(),
					Float.parseFloat(tmp.get(i)[5].toString()), Float.parseFloat(tmp.get(i)[6].toString()),
					tmp.get(i)[7].toString(), mapColor.get(tmp.get(i)[0].toString())));
		}
		return result;
	}
}