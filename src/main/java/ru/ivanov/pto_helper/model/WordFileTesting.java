package ru.ivanov.pto_helper.model;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

public class WordFileTesting {

        // инициализация массива с константами ячеек таблицы из doxc файла
        static String[] arrConst = {"NUM_AOSR",
                "DEW",                  // DAY OF END WORK
                "MEW",                  // MONTH OF END WORK
                "YEW",                  // YAER OF END WORK
                "WORK_TYPE",            //
                "PROJECT_DATA",         //
                "MATERIAL_DATA",        //
                "DSW",                  // DAY OF START WORK
                "MSW",                  // MONTH OF START WORK
                "YSW",                  // YEAR OF START WORK
                "NTD_AND_PROJECT",      //
                "NEXT_WORK",            //
                "ATTACHMENT"};          //

        public static void main(String[] args) {
            try (FileInputStream fileInputStream = new FileInputStream("test.docx")) {

                // открываем файл и считываем его содержимое в объект XWPFDocument
                XWPFDocument doc = new XWPFDocument(OPCPackage.open(fileInputStream));
                XWPFDocument tempDoc = new XWPFDocument();
                String fileName = "tempDoc";
                XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc);

                // считываем верхний колонтитул (херед документа)
                XWPFHeader docHeader = headerFooterPolicy.getDefaultHeader();
//            System.out.println(docHeader.getText());

                // печатаем содержимое всех параграфов документа в консоль
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                for (XWPFParagraph p : paragraphs) {
                    System.out.println(doc.getPosOfParagraph(p));
                    System.out.println(p.getText());
                }

                List<XWPFTable> tables = doc.getTables();
                XWPFTable table = tables.get(0);
                XWPFTableCell cell = table.getRow(0).getCell(1);
                System.out.println(table.getNumberOfRows());
                System.out.println(cell.getText());
                //Определение ширины ячейкии в непонятных единицах измерения, предположительно 1 см = 28,35*2 пт.
                System.out.println(cell.getCTTc().getTcPr().getTcW().getW());
                System.out.println(table.getWidth());

                // считываем нижний колонтитул (футер документа)
                XWPFFooter docFooter = headerFooterPolicy.getDefaultFooter();
//            System.out.println(docFooter.getText());
//            List<XWPFTableRow> tableRows = table.getRows();
//            for (int i = 0; i < tableRows.size(); i++) {
//                List<XWPFTableCell> tableCells = tableRows.get(i).getTableCells();
//                for (XWPFTableCell c : tableCells) {
//                    if (c.getText().equals("NUM_AOSR")) {
//                        c.setColor("FF00FF");
//                    }
//                }
//            }

//            //создание мар c данными: константа + номер строки, номер ячейки
//            HashMap<Table, String> mapTable = OpenWordDocumentExample.searchValue(table, arrConst);
//            for(Map.Entry entry : mapTable.entrySet()) {
//                int row = ((Table)entry.getKey()).row;
//                int c = ((Table)entry.getKey()).cell;
//                XWPFTableCell cellColor = table.getRow(row).getCell(c);
//                cellColor.setColor("FF00FF");
//                System.out.println(entry.getValue() + " row = " + row + "  = " + c);
//            }

                XWPFTableRow row60 = table.getRow(60);
                XWPFTableCell cell60 = row60.getCell(1);
                System.out.println(cell60.getText());
                XWPFParagraph par = cell60.getParagraphArray(0);
                System.out.println(par.getStyle());
                par.setStyle("2aosrleft");
                System.out.println(par.getStyle());


                // getWidth возвращает значение ширины ячейки в формате DXA, что значит 1 ед. = 1/1440 дюйма или 2,54/1440 см
                System.out.println(cell60.getWidth());
                System.out.println("Width cell = " + (cell60.getWidth() * 2.54 / 1440) + " см");







//            XWPFParagraph par = cell60.getParagraphArray(0);
//            XWPFRun run = par.createRun();
//            run.addCarriageReturn();
//            StringBuffer text = new StringBuffer("Hello from Intellig Idea");
//            run.setText(text);
//            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//            extractor.appendParagraphText(text, par);

//            // итератор для элементов документа
//            Iterator<IBodyElement> iterator = doc.getBodyElementsIterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next().getElementType().toString());
//            }

            /*System.out.println("_____________________________________");
            // печатаем все содержимое Word файла
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            System.out.println(extractor.getText());*/
                FileOutputStream out = new FileOutputStream("test.docx");
                doc.write(out);
                out.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // метод получает на вход таблицу и массив искомых значений и возвращает map со значениями
        public static HashMap<Table, String> searchValue(XWPFTable table, String[] arrConst) {
            HashMap<Table, String> map = new HashMap<>();
            List<XWPFTableRow> tableRows = table.getRows();
            for (int i = 0; i < tableRows.size(); i++) {
                List<XWPFTableCell> tableCells = tableRows.get(i).getTableCells();
                for (int j = 0; j < tableCells.size(); j++) {
                    for (String val : arrConst) {
                        if (tableCells.get(j).getText().equals(val)) {
                            map.put(new Table(i, j), val);
                        }
                    }
                }
            }
            return map;
        }

        static class Table {
            int row;
            int cell;

            Table (int row, int cell) {
                this.row = row;
                this.cell = cell;
            }
        }
}
