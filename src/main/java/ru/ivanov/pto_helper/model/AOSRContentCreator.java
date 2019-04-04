package ru.ivanov.pto_helper.model;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class AOSRContentCreator {

    public AOSRContent createAOSR(Row row, LinkedHashMap<AOSR_FIELDS, Integer> mapFieldsColumns) {
        AOSRContent aosrContent = new AOSRContent();
        String material_dataValue ="";
        String drawing_and_resultsValue = "";
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getColumnIndex() > 0) {
                if (cell.getCellType() == CellType.STRING) {
                    for (Map.Entry entry : mapFieldsColumns.entrySet()) {
                        int numColumn = (int) entry.getValue();
                        if (cell.getColumnIndex() == numColumn) {
                            String cellValue = cell.getStringCellValue();
                            AOSR_FIELDS field = (AOSR_FIELDS) entry.getKey();
                            if (!(field == AOSR_FIELDS.ATTACHMENT)) {
                                if (field == AOSR_FIELDS.MATERIAL_DATA) {
                                    material_dataValue = cellValue;
                                }
                                if (field == AOSR_FIELDS.DRAWING_AND_RESULTS) {
                                    drawing_and_resultsValue = cellValue;
                                }
                                aosrContent.addValue(cellValue, field);
                                break;
                            } else {
                                aosrContent.addValue(material_dataValue, drawing_and_resultsValue, field);
                                break;
                            }
                        }
                    }
                } else if (cell.getCellType() == CellType.NUMERIC) {
//                    int cellColumnNum = cell.getColumnIndex();                    //данный блок кода добавляет номер акта в поле AOSRContent
//                    if (cellColumnNum == 1) {
//                        int num = (int) cell.getNumericCellValue();
//                        aosrContent.aosrNum = num;
//                    } else {
                        for (Map.Entry entry : mapFieldsColumns.entrySet()) {
                            int numColumn = (int) entry.getValue();
                            if (cell.getColumnIndex() == numColumn) {
                                aosrContent.addValue(cell.getNumericCellValue(), (AOSR_FIELDS) entry.getKey());
                                break;
                            }
                        }
//                    }
                }
            }
        }
        return  aosrContent;
    }
}
