package ru.ivanov.pto_helper.model;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

    // создаем готовый АОСР
    public void createAOSR(int from, int to, String saveDirectoryPath) throws IOException, InvalidFormatException {
        for (int i = from; i <= to; i++) {
            AOSRContent aosrContent = aosrContenList.get(i);
            fillAOSR(aosrContent);
            String aosrNum = correctFileName(aosrContent.getAOSRNum());
            String outFilePath = saveDirectoryPath + "/AOSR_" + aosrNum + ".docx";
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

    // заполняем шаблонный файл АОСР данными из объекта AOSRContent
    private void fillAOSR(AOSRContent aosrContent) {
        LinkedHashMap<AOSR_FIELDS, ArrayList<String>> aosrContentMap = aosrContent.getAosrContentMap();
        for (AOSR_FIELDS fieldAOSRContent : aosrContentMap.keySet()) {
            for (AOSR_FIELDS fieldTemplateFile : mapCellAndRowFields.keySet()) {
                if (fieldAOSRContent == fieldTemplateFile) {
                    ArrayList<String> aosrContentListForField = aosrContentMap.get(fieldAOSRContent);
                    int sizeAOSRContentListForField = aosrContentListForField.size();
                    ArrayList<DocumentTableCell> tableCellListForField = mapCellAndRowFields.get(fieldTemplateFile);
                    int numTableCellForField = tableCellListForField.size();
                    String replacementText;
                    String replacementTextEnd = "";
                    if (numTableCellForField < sizeAOSRContentListForField) {
                        System.out.println("Не помещается");
                    } else {
                        for (int i = 0; i < numTableCellForField ; i++) {
                            DocumentTableCell currenttableCell = tableCellListForField.get(i);
                            if (i < sizeAOSRContentListForField) {
                                replacementText = aosrContentListForField.get(i);
                                replacementTextEnd = replacementText;
                            } else if (fieldTemplateFile == AOSR_FIELDS.DEW || fieldTemplateFile == AOSR_FIELDS.MEW || fieldTemplateFile == AOSR_FIELDS.YEW){
                                replacementText = replacementTextEnd;
                            } else {
                                replacementText = "";
                            }
                            replaceText(currenttableCell, replacementText);
                        }
                    }
                }

            }
        }
    }

    public void setTemplateFilePath(String templaneFilePath) {
    }

    // метод по замене содержимого ячейкм таблицы в файле Word
    public void replaceText(DocumentTableCell tableCell, String replacementVelue) {
        for (XWPFTable table : document.getTables()) {
            XWPFTableCell cell = table.getRow(tableCell.getRow()).getCell(tableCell.getCell());
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String currentText = run.getText(0);
                    if (currentText != null) {
                        if(replacementVelue.equals("пусто")|| replacementVelue.equals("Пусто")) {
                            replacementVelue = "";
                        }
                        currentText = currentText.replace(currentText, replacementVelue);
                        run.setText(currentText, 0);
                    }
                }
            }
        }
    }


//        String currentText = run.getText(0);
//        if (currentText != null && currentText.contains(word)) {
//            currentText = currentText.replace(word, value);
//            run.setText(currentText, 0);
//            return true;
//        } else {
//            return false;
//        }
//    }

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
