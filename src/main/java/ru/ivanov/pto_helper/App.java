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
                LinkedHashMap<String, ArrayList<String>> map = aosrContent.getAosrContent();
                for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
                    System.out.println("Field = " + entry.getKey());
                    ArrayList<String> arr = entry.getValue();
                    for (int j = 0; j < arr.size(); j++) {
                        System.out.println("Value " + j + " = " + arr.get(j));
                    }
                }
            }
        }
    }
}
