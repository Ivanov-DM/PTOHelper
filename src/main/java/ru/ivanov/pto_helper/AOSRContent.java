package ru.ivanov.pto_helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AOSRContent {
    private LinkedHashMap<String, ArrayList<String>> aosrContent;
    protected int aosrNum;
    protected boolean isReady;
    static int countString;
    static int countInteger;
//    private int EXCEL_NUM;                  //1
//    private String NUM_AOSR;                //2
//    private String DEW;                     //3 DAY OF END WORK      день окончания работ
//    private String MEW;                     //4 MONTH OF END WORK    месяц окончания работ
//    private String YEW;                     //5 YAER OF END WORK     год окончания работ
//    private String WORK_TYPE;               //6                      наименование работ
//    private String PROJECT_DATA;            //7                      наименование проекта
//    private String MATERIAL_DATA;           //8                      наименование применяемых материалов
//    private String DRAWING_AND_RESULTS;     //9                      исполнительные чертежи и результаты испытаний
//    private String DSW;                     //10 DAY OF START WORK    день начал работ
//    private String MSW;                     //11 MONTH OF START WORK  месяц начал работ
//    private String YSW;                     //12 YEAR OF START WORK   год начала работ
//    private String NTD_AND_PROJECT;         //13                      наименование НТД и РД
//    private String NEXT_WORK;               //14                      наименование последующих работ
//    private String ATTACHMENT;              //15                      приложение

    public LinkedHashMap<String, ArrayList<String>> getAosrContent() {
        return aosrContent;
    }

    public boolean getReady() {
        return isReady;
    }

    public AOSRContent() {
        initMap();
        aosrNum = 0;
        isReady = false;
    }

//    public void putValue(String key, String text) {
//        aosrContent = new LinkedHashMap<>();
//        aosrContent.put(key, getStringArr(text, key));
//    }

    private void initMap() {
        aosrContent = new LinkedHashMap<>(32);
//        for (AOSR_FIELDS fields : AOSR_FIELDS.values()) {
//            aosrContent.put(fields.toString(), new ArrayList<String>(32));
//        }
    }

    public void addValue(double value, String key) {
//        String str = String.valueOf((int) value);
//        putValue(str, key);
//        countInteger++;
//        System.out.println("CountInteger = " + countInteger + " " + key + "CONTENT: " + value);
    }

    public void addValue(String value, String key) {
//        putValue(value, key);
//        countString++;
//        System.out.println("CountString = " + countString + " " + key + "CONTENT: " + value);
    }

    private void putValue(String text, String key) {
        ArrayList<String> resultStringArray = new ArrayList<>();
        StringBuffer strB = new StringBuffer();
        String[] arrStringLine = text.split(" ");
        String currentWord = arrStringLine[0];
        String endOfLineWord = null;
        for (AOSR_FIELDS fields : AOSR_FIELDS.values()) {
            String str = fields.toString();
            int maxLineSize = fields.getFirstRowLength();
            if (str.equals(key)) {
                for (int i = 1; i < arrStringLine.length; i++) {
                    strB.append(currentWord + " ");
                    if (strB.length() < maxLineSize) {
                        currentWord = arrStringLine[i];
                        endOfLineWord = currentWord;
                    } else {
                        maxLineSize = fields.getNextRowLength();
                        strB.delete(strB.length() - endOfLineWord.length() - 1, strB.length());
                        resultStringArray.add(strB.toString());
                        strB.setLength(0);
                        i--;
                    }

                }
                strB.append(currentWord);
                resultStringArray.add(strB.toString());
            }
            aosrContent.put(str, resultStringArray);
        }
    }
}
