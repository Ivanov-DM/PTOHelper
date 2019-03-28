package ru.ivanov.pto_helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.xml.bind.ValidationEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App {

    public static final String EXCEL_FILE_NAME = "testExcel.xlsx";
    public static final String TEMPLANE_FILE_NAME = "";
    public static final String PATH_TO_SAVE = "";

    public static void main( String[] args ) throws IOException {
        ExcelParser excelParser = new ExcelParser(EXCEL_FILE_NAME);
        ArrayList<AOSRContent> aosrContenList = excelParser.getAOSRContentList();
        for (int i = 0; i < aosrContenList.size(); i++) {
            AOSRContent aosrContent = aosrContenList.get(i);
            if (aosrContent.isReady) {
                System.out.println(i);
            }
        }


//        XSSFWorkbook workbook = new XSSFWorkbook(EXCEL_FILE_NAME);
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rowIterator = sheet.rowIterator();
//        boolean status = false;
//        while(rowIterator.hasNext()) {
//            Row row = rowIterator.next();
//            Iterator<Cell> cellIterator = row.cellIterator();
//            while (cellIterator.hasNext()) {
//                Cell cell = cellIterator.next();
//                if (cell.getCellType() == CellType.FORMULA) {
//                    status = !cell.getBooleanCellValue(); //отрицание потому то в Excel файле если данные для акта готовы то ЛОЖЬ
//                    if (status) {
//                        System.out.println(status);
//                    }
//                }
//                if (status) {
//                    if (cell.getCellType() == CellType.STRING) {
//                        System.out.println(cell.getStringCellValue());
//                    } else if (cell.getCellType() == CellType.NUMERIC) {
//                        System.out.println((cell.getNumericCellValue()));
//                    }
//                }
//            }
//        }






//





















        //        try {
//            ExcelParser eParser = new ExcelParser(EXCEL_FILE_NAME);
//            LinkedHashMap<String, Integer> testMap = eParser.inintMap;
//            for (Map.Entry entry : testMap.entrySet()) {
//                System.out.println("Key: " + entry.getKey() + ", column: " + entry.getValue());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ArrayList<AOSRContent> aosrList = eParser.getAOSRContentList();
//        WordProcessor wProcessor = new WordProcessor(TEMPLANE_FILE_NAME);
//        wProcessor.createAOSR(from, to, PATH_TO_SAVE);





//        AOSRContent aosrContent = new AOSRContent();
//
//
//
//
//        LinkedHashMap<String, ArrayList<String>> map = aosrContent.getAosrContent();
//        for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
//            System.out.println("KEY - " + entry.getKey() + ", VALUE SIZE = " + entry.getValue().size());
//
//
//        }
    }
}
