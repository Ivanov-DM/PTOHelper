package ru.ivanov.pto_helper;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.ivanov.pto_helper.WordFileTesting.arrConst;

public class WordProcessor {
    private String templateFilePath;
    XWPFDocument document;
    ArrayList<AOSRContent> aosrContenList;
    LinkedHashMap<AOSR_FIELDS, ArrayList<DocumentTableCell>> mapCellAndRowFields;
    FileInputStream in;
    FileOutputStream out;


    public WordProcessor(String templateFilePath, ArrayList<AOSRContent> aosrContenList) throws InvalidFormatException, IOException {
        this.templateFilePath = templateFilePath;
        this.aosrContenList = aosrContenList;
        document = new XWPFDocument(OPCPackage.open(templateFilePath));
        initMapCellAndRowFields();
    }

    public void createAOSR(int from, int to) throws IOException, InvalidFormatException {

        for (int i = from; i <= to; i++) {
            AOSRContent aosrContent = aosrContenList.get(i);

            fillAOSR(aosrContent);
//            LinkedHashMap<String, ArrayList<String>> map = aosrContent.getAosrContentMap();
//            for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
//                System.out.println("Field = " + entry.getKey());
//                ArrayList<String> arr = entry.getValue();
//                for (int j = 0; j < arr.size(); j++) {
//                    System.out.println("Value " + j + " = " + arr.get(j));
//                }
//            }








            String aosrNum = correctFileName(aosrContent.getAOSRNum());
            String outFilePath = "AOSR_" + aosrNum + ".docx";
            out = new FileOutputStream(outFilePath);
            document.write(out);
            out.close();
        }
    }

    // инициализация Word файла шаблона документа, в результате инициализации получаем
    // объект mapCellAndRowFields, являющегося LinkedHashMap, у которого Key = AOSR_FIELDS
    // Value - это ArrayList с информацией о ячейках таблицы, в которых находится указанное поле,
    // информация о ячейке - это номер строки и номер ячейки в этой строке

    private void initMapCellAndRowFields() {
        mapCellAndRowFields = new LinkedHashMap<>(32);
        for (AOSR_FIELDS fields : AOSR_FIELDS.values()) {
            ArrayList<DocumentTableCell> arrCells = new ArrayList<>();
            for (XWPFTable table : document.getTables()) {
                List<XWPFTableRow> rows = table.getRows();
                for (int i = 0; i < rows.size(); i++) {
                    int numRow = i;
                    List<XWPFTableCell> cells = rows.get(i).getTableCells();
                    for (int j = 0; j < cells.size(); j++) {
                        int numCell = j;
                        for (XWPFParagraph paragraph : cells.get(j).getParagraphs()) {
                            for (XWPFRun run : paragraph.getRuns()) {
                                String str = fields.toString();
                                if (str.equals(run.text())) {
                                    arrCells.add(new DocumentTableCell(numRow, numCell));
                                }
                            }
                        }
                    }
                }
                mapCellAndRowFields.put(fields, arrCells);
            }
        }
    }

    private void fillAOSR(AOSRContent aosrContent) {
    }

    public void setTemplateFilePath(String templaneFilePath) {
    }

    // метод по замене содержимого ячейкм таблицы в файле Word
    public boolean replaceText(XWPFRun run, String word, String value) {
        String currentText = run.getText(0);
        if (currentText != null && currentText.contains(word)) {
            currentText = currentText.replace(word, value);
            run.setText(currentText, 0);
            return true;
        } else {
            return false;
        }
    }

    //изменение запрещенных для использования в имени Файла или каталога символов для Windows на символ _
    private String correctFileName(String name) {
        String correctedName = name;
        String[] wrongWindowsChar = {"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};
        for (int i = 0; i < wrongWindowsChar.length; i++) {
            if (correctedName.contains(wrongWindowsChar[i])) {
                correctedName = correctedName.replace(wrongWindowsChar[i], "_");
            }
        }
        return correctedName;
    }

    class DocumentTableCell {
        String filed;
        int row;
        int cell;


        // Внутренний класс для хранения информации о каждой ячейки таблицы шаблонного файла Word
        // информация - это номер строки и номер ячейки в этой строке
        private DocumentTableCell(int row, int cell) {
            this.row = row;
            this.cell = cell;
        }

        public int getRow() {
            return row;
        }

        public int getCell() {
            return cell;
        }
    }
}
