package ru.ivanov.pto_helper;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class WordFileTesting {

        // инициализация массива с константами ячеек таблицы из doxc файла
         private static String[] arrConst = {"NUM_AOSR",
                "DEW",                  // DAY OF END WORK      день окончания работ
                "MEW",                  // MONTH OF END WORK    месяц окончания работ
                "YEW",                  // YAER OF END WORK     год окончания работ
                "WORK_TYPE",            //                      наименование работ
                "PROJECT_DATA",         //                      наименование проекта
                "MATERIAL_DATA",        //                      наименование применяемых материалов
                "DRAWING_AND_RESULTS",  //                      исполнительные чертежи и результаты испытаний
                "DSW",                  // DAY OF START WORK    день начал работ
                "MSW",                  // MONTH OF START WORK  месяц начал работ
                "YSW",                  // YEAR OF START WORK   год начала работ
                "NTD_AND_PROJECT",      //                      наименование НТД и РД
                "NEXT_WORK",            //                      наименование последующих работ
                "ATTACHMENT"};          //                      приложения

        public static void main(String[] args) {
            try (FileInputStream fileInputStream = new FileInputStream("test.docx")) {

                // открываем файл и считываем его содержимое в объект XWPFDocument
                XWPFDocument doc = new XWPFDocument(OPCPackage.open(fileInputStream));
                XWPFDocument tempDoc = new XWPFDocument();
                String fileName = "tempDoc";
                XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(doc);

                XWPFDocument newDoc = new XWPFDocument();

                // считываем верхний колонтитул (хедер документа)
//                XWPFHeader docHeader = headerFooterPolicy.getDefaultHeader();
//            System.out.println(docHeader.getText());

                // печатаем содержимое всех параграфов документа в консоль
//                List<XWPFParagraph> paragraphs = doc.getParagraphs();
//                for (XWPFParagraph p : paragraphs) {
//                    System.out.println(doc.getPosOfParagraph(p));
//                    System.out.println(p.getText());
//                }
                // получаем объект Table для работы с таблицей
                List<XWPFTable> tables = doc.getTables();
                XWPFTable table = tables.get(0);


//                XWPFTableCell cell = table.getRow(0).getCell(1);
//                System.out.println(table.getNumberOfRows());
//                System.out.println(cell.getText());
                //Определение ширины ячейкии в непонятных единицах измерения, предположительно 1 см = 28,35*2 пт.
//                System.out.println(cell.getCTTc().getTcPr().getTcW().getW());
//                System.out.println(table.getWidth());

                // считываем нижний колонтитул (футер документа)
//                XWPFFooter docFooter = headerFooterPolicy.getDefaultFooter();
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

//                XWPFTableRow row60 = table.getRow(60);
//                XWPFTableCell cell60 = row60.getCell(1);
//                System.out.println(cell60.getText());
//                XWPFParagraph par = cell60.getParagraphArray(0);
//                System.out.println(par.getStyle());
//                par.setStyle("2aosrleft");
//                System.out.println(par.getStyle());


                // getWidth возвращает значение ширины ячейки в формате DXA, что значит 1 ед. = 1/1440 дюйма или 2,54/1440 см
//                System.out.println(cell60.getWidth());
//                System.out.println("Width cell = " + (cell60.getWidth() * 2.54 / 1440) + " см");


                LinkedHashMap<Table, String> mapTable = WordFileTesting.searchValue(table, arrConst);
                for (Map.Entry<Table, String> entry : mapTable.entrySet()) {
                    int row = entry.getKey().row;
                    int cell = entry.getKey().cell;
                    String str = entry.getValue() + ": row = " + row + ", cell = " + cell;
//                    XWPFParagraph par1 = newDoc.createParagraph();
//                    par1.createRun().setText(str);

//                    System.out.println(entry.getValue() + ": row = " + row + ", cell = " + cell);
                }



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

//                File newFile = new File("Number of rows and cells2.docx");
//                FileOutputStream newOut = new FileOutputStream(newFile);
//                newDoc.write(newOut);
//                newOut.close();
//                newDoc.close();


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // метод получает на вход таблицу и массив искомых значений и возвращает map со значениями
        public static LinkedHashMap<Table, String> searchValue(XWPFTable table, String[] arrConst) {
            LinkedHashMap<Table, String> map = new LinkedHashMap<>();
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

    //метод для получения массива строк, которые будут подставлены в ячейки таблицы
    public static ArrayList<String> getStringArr(String text, int maxLineSize) {
        ArrayList<String> resultStringArray = new ArrayList<>();
        StringBuilder strB = new StringBuilder();
        String[] arrStringLine = text.split(" ");
        String currentWord = arrStringLine[0];
        String endOfLineWord = null;
        for (int i = 1; i < arrStringLine.length; i++) {
            strB.append(currentWord + " ");
            if (strB.length() < maxLineSize) {
                currentWord = arrStringLine[i];
                endOfLineWord = currentWord;
            } else {
                strB.delete(strB.length() - endOfLineWord.length() - 1, strB.length());
                resultStringArray.add(strB.toString());
                strB.setLength(0);
                i--;
            }

        }
        strB.append(currentWord);
        resultStringArray.add(strB.toString());
        return resultStringArray;
    }

    public static boolean cangeTextIntoTableCell(String text, XWPFTable table, int numRow, int numCell) {
        try {
             XWPFTableCell cell = table.getRow(numRow).getCell(numCell);
             XWPFParagraph par = cell.getParagraphArray(0);
             String currentStylePar = par.getStyle();
             cell.removeParagraph(0);
             XWPFParagraph newPar = cell.addParagraph();
             newPar.setStyle(currentStylePar);
             XWPFRun run = newPar.createRun();
             run.setText(text);
             return true;
        } catch (Exception e) {
             e.printStackTrace();
             return false;
        }
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
