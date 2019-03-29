package ru.ivanov.pto_helper;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelParser {
    XSSFWorkbook book;
    FileInputStream in;
    LinkedHashMap<AOSR_FIELDS, Integer> mapFieldsColumns;

    public ExcelParser(String fileName) throws IOException {
        in = new FileInputStream(new File(fileName));
        book = new XSSFWorkbook(in);
        mapFieldsColumns = new LinkedHashMap<>();
        initExcelFie();
    }

    // составление map, где key = поле класса AOSRContent, value = номер столбца +
    public void initExcelFie() {
        XSSFSheet sheet = book.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while(cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getCellType() == CellType.STRING) {
                    for (AOSR_FIELDS fields : AOSR_FIELDS.values()) {
                        String str = fields.toString();
                        if (str.equals(cell.getStringCellValue())) {
                            mapFieldsColumns.put(fields, cell.getColumnIndex());
                        }
                    }
                }
            }
        }
    }

    // возвращает список объектов класса AOSRContent (только тех, которые в excel файле имеют статус ЛОЖЬ в графе isEmpty)
    public ArrayList<AOSRContent> getAOSRContentList() {
        ArrayList<AOSRContent> aosrContentList = new ArrayList<>(32);
        boolean aosrIsReady = false;
        XSSFSheet sheet = book.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.rowIterator();
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (row.getRowNum() > 2) {
                Iterator<Cell> cellIterator = row.cellIterator();
                AOSRContent aosrContent = new AOSRContent();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == CellType.FORMULA) {
                        aosrIsReady = !cell.getBooleanCellValue(); //отрицание потому то в Excel файле если данные для акта готовы то ЛОЖЬ
                        if (aosrIsReady) {
                            aosrContent.isReady = aosrIsReady;
                            continue;
                        }
                    }
                    if (aosrIsReady) {
                        if (cell.getCellType() == CellType.STRING) {
                            for (Map.Entry entry : mapFieldsColumns.entrySet()) {
                                int numColumn = (int) entry.getValue();
                                if (cell.getColumnIndex() == numColumn) {
                                    aosrContent.addValue(cell.getStringCellValue(), entry.getKey().toString());
                                    break;
                                }
                            }
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            int cellColumnNum = cell.getColumnIndex();
                            if (cellColumnNum == 1) {
                                int num = (int) cell.getNumericCellValue();
                                aosrContent.aosrNum = num;
                            } else {
                                for (Map.Entry entry : mapFieldsColumns.entrySet()) {
                                    int numColumn = (int) entry.getValue();
                                    if (cell.getColumnIndex() == numColumn) {
                                        aosrContent.addValue(cell.getNumericCellValue(), entry.getKey().toString());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                aosrContentList.add(aosrContent);
            }
        }
        return aosrContentList;
    }
}
