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
import java.util.Map;

public class WordProcessor {
    private String annexTemplateFilePath_1 = "C:\\Users\\ПК\\IdeaProjects\\PTOHelper\\src\\main\\resources\\annexTemplateFile_1.docx";
    private String annexTemplateFilePath_2 = "C:\\Users\\ПК\\IdeaProjects\\PTOHelper\\src\\main\\resources\\annexTemplateFile_2.docx";
    XWPFDocument document;
    XWPFDocument documentAnnex_1;
    XWPFDocument documentAnnex_2;
    ArrayList<AOSRContent> aosrContenList;
    private LinkedHashMap<AOSR_FIELDS, ArrayList<DocumentTableCell>> mapCellAndRowFieldsfromAOSRTemplateFile;
    private LinkedHashMap<AOSR_FIELDS, ArrayList<DocumentTableCell>> mapCellAndRowFieldsFromAnnexFile_1;
    private LinkedHashMap<AOSR_FIELDS, ArrayList<DocumentTableCell>> mapCellAndRowFieldsFromAnnexFile_2;
    FileInputStream in;
    FileOutputStream out;


    public WordProcessor(String aosrTemplateFilePath, ArrayList<AOSRContent> aosrContenList) throws InvalidFormatException, IOException, RuntimeException {
        this.aosrContenList = aosrContenList;
        document = new XWPFDocument(OPCPackage.open(aosrTemplateFilePath));
        documentAnnex_1 = new XWPFDocument(OPCPackage.open(annexTemplateFilePath_1));
        documentAnnex_2 = new XWPFDocument(OPCPackage.open(annexTemplateFilePath_2));
        mapCellAndRowFieldsfromAOSRTemplateFile = initMapCellAndRowFields(document);
        mapCellAndRowFieldsFromAnnexFile_1 = initMapCellAndRowFields(documentAnnex_1);
        mapCellAndRowFieldsFromAnnexFile_2 = initMapCellAndRowFields(documentAnnex_2);
    }

    // создаем готовый АОСР
    public void createAOSR(ArrayList<Integer> aosrRange, String saveDirectoryPath) throws IOException, InvalidFormatException {
        String aosrNum = "";
        String outFilePath = "";
        for (int i = 0; i < aosrRange.size(); i++) {
            int numAOSR = aosrRange.get(i) - 1;
            AOSRContent aosrContent = aosrContenList.get(numAOSR);
            aosrNum = correctFileName(aosrContent.getAOSRNum());
            outFilePath = saveDirectoryPath + "/AOSR_" + aosrNum + ".docx";
            if (!isEnoughCellsforField(aosrContent, AOSR_FIELDS.MATERIAL_DATA)) {
                fillAOSRTemplateFile(documentAnnex_1, aosrContent, mapCellAndRowFieldsFromAnnexFile_1);
                String outAnnexFilePath_1 = saveDirectoryPath + "/AOSR_" + aosrNum + "_приложение к п.3.docx";
                String replacementText = "Приложение №1 к акту освидетельствования скрытых работ №" + aosrContent.getAOSRNum() + " от " + aosrContent.getAOSRDateEndWork();
                writeFile(documentAnnex_1, outAnnexFilePath_1);
                aosrContent.putValue(replacementText, AOSR_FIELDS.MATERIAL_DATA);
                aosrContent.putValue(replacementText, AOSR_FIELDS.ATTACHMENT);
            }
            if (!isEnoughCellsforField(aosrContent, AOSR_FIELDS.DRAWING_AND_RESULTS)) {
                fillAOSRTemplateFile(documentAnnex_2, aosrContent, mapCellAndRowFieldsFromAnnexFile_2);
                String outAnnexFilePath_2 = saveDirectoryPath + "/AOSR_" + aosrNum + "_приложение к п.4.docx";
                String replacementText = "Приложение №2 к акту освидетельствования скрытых работ №" + aosrContent.getAOSRNum() + " от " + aosrContent.getAOSRDateEndWork();
                writeFile(documentAnnex_2, outAnnexFilePath_2);
                aosrContent.putValue(replacementText, AOSR_FIELDS.DRAWING_AND_RESULTS);
                aosrContent.addAttachmenetValue(replacementText, AOSR_FIELDS.ATTACHMENT);
            }
            fillAOSRTemplateFile(document, aosrContent, mapCellAndRowFieldsfromAOSRTemplateFile);
            writeFile(document, outFilePath);
        }
    }

    private void writeFile(XWPFDocument doc, String filePath) {
        try {
            out = new FileOutputStream(filePath);
            doc.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // инициализация Word файла шаблона документа, в результате инициализации получаем
    // объект mapCellAndRowFields, являющегося LinkedHashMap, у которого Key = AOSR_FIELDS
    // Value - это ArrayList с информацией о ячейках таблицы, в которых находится указанное поле,
    // информация о ячейке - это номер строки и номер ячейки в этой строке

    private LinkedHashMap<AOSR_FIELDS, ArrayList<DocumentTableCell>> initMapCellAndRowFields(XWPFDocument document) throws RuntimeException {
        LinkedHashMap<AOSR_FIELDS, ArrayList<DocumentTableCell>> map = new LinkedHashMap<>(32);
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
                if (!arrCells.isEmpty()) {
                    map.put(fields, arrCells);
                }
            }
        }
        if (map.isEmpty()) {
            throw new RuntimeException();
        }
        return map;
    }

    private void fillAOSRTemplateFile(XWPFDocument doc, AOSRContent aosrContent, LinkedHashMap<AOSR_FIELDS, ArrayList<DocumentTableCell>> map) {
        LinkedHashMap<AOSR_FIELDS, ArrayList<String>> aosrContentMap = aosrContent.getAosrContentMap();
        for (AOSR_FIELDS fieldAOSRContent : aosrContentMap.keySet()) {
            for (AOSR_FIELDS fieldTemplateFile : map.keySet()) {
                if (fieldAOSRContent == fieldTemplateFile) {
                    ArrayList<String> aosrContentListForField = aosrContentMap.get(fieldAOSRContent);
                    ArrayList<DocumentTableCell> tableCellListForField = map.get(fieldTemplateFile);
                    String replacementText;
                    String replacementTextEnd = "";
                    for (int i = 0; i < tableCellListForField.size(); i++) {
                        DocumentTableCell currentTableCell = tableCellListForField.get(i);
                        if (i < aosrContentListForField.size()) {
                            replacementText = aosrContentListForField.get(i);
                            replacementTextEnd = replacementText;
                        } else if (fieldTemplateFile == AOSR_FIELDS.DEW || fieldTemplateFile == AOSR_FIELDS.MEW || fieldTemplateFile == AOSR_FIELDS.YEW){
                            replacementText = replacementTextEnd;
                        } else {
                            replacementText = "";
                        }
                        replaceText(doc, currentTableCell, replacementText);
                    }
                }
            }
        }
    }

    private boolean isEnoughCellsforField(AOSRContent aosrContent, AOSR_FIELDS field) {
        LinkedHashMap<AOSR_FIELDS, ArrayList<String>> aosrContentMap = aosrContent.getAosrContentMap();
        ArrayList<DocumentTableCell> tableCellListForField = mapCellAndRowFieldsfromAOSRTemplateFile.get(field);
        int numTableCellForField = tableCellListForField.size();
        for (AOSR_FIELDS fieldAOSRContent : aosrContentMap.keySet()) {
            if (fieldAOSRContent == field) {
                ArrayList<String> aosrContentListForField = aosrContentMap.get(fieldAOSRContent);
                int sizeAOSRContentListForField = aosrContentListForField.size();
                if (numTableCellForField < sizeAOSRContentListForField) {
                    return false;
                }
                break;
            }
        }
        return true;
    }

    // метод по замене содержимого ячейкм таблицы в файле Word
    public void replaceText(XWPFDocument doc, DocumentTableCell tableCell, String replacementVelue) {
        for (XWPFTable table : doc.getTables()) {
            XWPFTableCell cell = table.getRow(tableCell.getRow()).getCell(tableCell.getCell());
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                for (XWPFRun run : paragraph.getRuns()) {
                    String currentText = run.getText(0);
                    if (currentText != null) {
                        if(replacementVelue.equals("пусто") || replacementVelue.equals("Пусто")) {
                            replacementVelue = "";
                        }
                        currentText = currentText.replace(currentText, replacementVelue);
                        run.setText(currentText, 0);
                    }
                }
            }
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
