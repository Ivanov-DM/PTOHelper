package ru.ivanov.pto_helper;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelParser {
    XSSFWorkbook book;
    AOSRContent aosrContent;

    public ExcelParser(String fileName) throws IOException {
        book = ExcelParser.createBook(fileName);
    }

    public AOSRContent createAOSR(int numRow) {
        XSSFSheet sheet = book.getSheet("OGR");
        XSSFRow currentRow = sheet.getRow(numRow + 2);
        AOSRContent aosrContent = new AOSRContent();
//        aosrContent.setEXCEL_NUM(Integer.parseInt(currentRow.getCell(0).getStringCellValue()));
//        aosrContent.setNUM_AOSR(currentRow.getCell(1).getStringCellValue());
//        aosrContent.setDEW(currentRow.getCell(2).getStringCellValue());
//        aosrContent.setMEW(currentRow.getCell(3).getStringCellValue());
//        aosrContent.setYEW(currentRow.getCell(4).getStringCellValue());
//        aosrContent.setWORK_TYPE(currentRow.getCell(5).getStringCellValue());
//        aosrContent.setPROJECT_DATA(currentRow.getCell(6).getStringCellValue());
//        aosrContent.setMATERIAL_DATA(currentRow.getCell(7).getStringCellValue());
//        aosrContent.setDRAWING_AND_RESULTS(currentRow.getCell(8).getStringCellValue());
//        aosrContent.setDSW(currentRow.getCell(9).getStringCellValue());
//        aosrContent.setMSW(currentRow.getCell(10).getStringCellValue());
//        aosrContent.setYSW(currentRow.getCell(11).getStringCellValue());
//        aosrContent.setNTD_AND_PROJECT(currentRow.getCell(12).getStringCellValue());
//        aosrContent.setNEXT_WORK(currentRow.getCell(13).getStringCellValue());
//        aosrContent.setATTACHMENT(currentRow.getCell(14).getStringCellValue());
        return aosrContent;
    }

    public void aosrContentInfo(AOSRContent aosr) {
//        System.out.println(aosr.getATTACHMENT() + aosr.getMATERIAL_DATA());
    }



    private static XSSFWorkbook createBook(String fileName) throws IOException {
        FileInputStream in = new FileInputStream(new File(fileName));
        XSSFWorkbook book = new XSSFWorkbook(in);
        return book;
    }
}
