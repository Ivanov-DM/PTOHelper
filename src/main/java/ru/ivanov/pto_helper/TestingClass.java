package ru.ivanov.pto_helper;

public class TestingClass {
    public static void main(String[] args) {
        try {
            ExcelParser excelParser = new ExcelParser("testExcel");
            AOSRContent aosrContent = excelParser.createAOSR(1);
            excelParser.aosrContentInfo(aosrContent);











        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
