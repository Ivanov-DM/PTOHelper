package ru.ivanov.pto_helper;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelParser {
    XSSFWorkbook book;

    public ExcelParser(File file) throws IOException {
        book = ExcelParser.createBook(file);
    }



    private static XSSFWorkbook createBook(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        XSSFWorkbook book = new XSSFWorkbook(in);
        return book;
    }
}
