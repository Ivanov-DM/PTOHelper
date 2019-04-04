package ru.ivanov.pto_helper.model;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    public static final String EXCEL_FILE_NAME = "testExcel.xlsx";
    public static final String TEMPLANE_FILE_NAME = "test.docx";
    public static final String PATH_TO_SAVE = "";

    public static void main( String[] args ) throws IOException, InvalidFormatException {

        //Создаем экземпляр ExcelParser и передаем ему путь к файлу Excel, из которого будем считывать данные
        ExcelParser excelParser = new ExcelParser(EXCEL_FILE_NAME);

        //Создаем экземпляр WordProcessor и передаем ему путь к файлу Word (шаблон АОСР) и сформированный массив
        // данных AOSRContent, который получаем в результате чтения ExcelParser файла Excel
        WordProcessor wordProcessor = new WordProcessor(TEMPLANE_FILE_NAME, excelParser.getAOSRContentListNew());



//        // Посмотреть содержимое mapCellAndRowFields в WordProcessor
//        System.out.println(wordProcessor.mapCellAndRowFields.size());
//        for (Map.Entry<AOSR_FIELDS, ArrayList<WordProcessor.DocumentTableCell>> entry : wordProcessor.mapCellAndRowFields.entrySet()) {
//            System.out.println("Field = " + entry.getKey());
//            ArrayList<WordProcessor.DocumentTableCell> arrCell = entry.getValue();
//            System.out.println("Num of cell = " + arrCell.size());
//            for (int i = 0; i < arrCell.size(); i++) {
//                int row = arrCell.get(i).getRow();
//                int cell = arrCell.get(i).getCell();
//                System.out.println("Cell #" + i + ": row = " + row + ", cell = " + cell);
//            }
//        }

//        wordProcessor.setTemplateFilePath(TEMPLANE_FILE_NAME);

//        wordProcessor.createAOSR(3, 3);

        //Посмотреть содержимое aosrContentList
//        ArrayList<AOSRContent> aosrContenList = excelParser.getAOSRContentList();
//        for (int i = 0; i < aosrContenList.size(); i++) {
//            AOSRContent aosrContent = aosrContenList.get(i);
//            if (aosrContent.isReady) {
//                LinkedHashMap<String, ArrayList<String>> map = aosrContent.getAosrContentMap();
//                for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
//                    System.out.println("Field = " + entry.getKey());
//                    ArrayList<String> arr = entry.getValue();
//                    for (int j = 0; j < arr.size(); j++) {
//                        System.out.println("Value " + j + " = " + arr.get(j));
//                    }
//                }
//            }
//        }
        // -------------------------------------

    }
}
