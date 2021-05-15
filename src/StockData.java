import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StockData { // read stock excel data
	private TreeMap<String, String> stockDataTreeMap;
	
	StockData(){
		stockDataTreeMap = new TreeMap<String, String>();
		try {
			File CosdaqFile = new File("./kosdaq.xlsx");
			File CospiFile = new File("./kospi.xlsx");
			
			FileInputStream cosdaqStream = new FileInputStream(CosdaqFile);
			FileInputStream cospiStream = new FileInputStream(CospiFile);
			
			XSSFWorkbook cosdaqWorkbook = new XSSFWorkbook(cosdaqStream);
			XSSFWorkbook cospiWorkbook = new XSSFWorkbook(cospiStream);
			
			XSSFSheet cosdaqSheet = cosdaqWorkbook.getSheetAt(0);
			XSSFSheet cospiSheet = cospiWorkbook.getSheetAt(0);
			
			int cosdaqRows = cosdaqSheet.getPhysicalNumberOfRows();
			int cospiRows = cospiSheet.getPhysicalNumberOfRows();
			
			for(int i=1; i<cosdaqRows; i++) {
				XSSFRow row = cosdaqSheet.getRow(i);
				stockDataTreeMap.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
			}
			for(int i=1; i<cospiRows; i++) {
				XSSFRow row = cospiSheet.getRow(i);
				stockDataTreeMap.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public TreeMap<String, String> getStockData() { 
		return stockDataTreeMap;
	}
}
