package ru.ivanov.pto_helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AOSRContent {
    private LinkedHashMap<String, ArrayList<String>> aosrContentMap;
    protected int aosrNum;
    protected boolean isReady;

    public LinkedHashMap<String, ArrayList<String>> getAosrContentMap() {
        return aosrContentMap;
    }

    public boolean getReady() {
        return isReady;
    }

    public AOSRContent() {
        initMap();
        aosrNum = 0;
        isReady = false;
    }

    private void initMap() {
        aosrContentMap = new LinkedHashMap<>(32);
    }

    public void addValue(double value, String key) {
        String str = String.valueOf((int) value);
        putValue(str, key);
    }

    public void addValue(String value, String key) {
        putValue(value, key);
    }

    //
    private void putValue(String text, String key) {
        ArrayList<String> resultStringArray = new ArrayList<>();
        for (AOSR_FIELDS fields : AOSR_FIELDS.values()) {
            String str = fields.toString();
            if (str.equals(key)) {
                if (fields.getNextRowLength() == 0) {
                    resultStringArray.add(text);
                    aosrContentMap.put(str, resultStringArray);
                    break;
                } else {
                    StringBuffer strB = new StringBuffer();
                    String[] arrStringLine = text.split(" ");
                    String currentWord = arrStringLine[0];
                    String endOfLineWord = null;
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
                    aosrContentMap.put(str, resultStringArray);
                    break;
                }
            }
        }
    }

    public String getAOSRNum() {
        String aosrNum = null;
        for (Map.Entry<String, ArrayList<String>> entry : aosrContentMap.entrySet()) {
            if (AOSR_FIELDS.NUM_AOSR.toString() == entry.getKey()){
                aosrNum = entry.getValue().get(0);
                return aosrNum;
            }
        }
        return aosrNum;
    }
}
