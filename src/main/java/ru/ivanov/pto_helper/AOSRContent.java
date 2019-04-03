package ru.ivanov.pto_helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class AOSRContent {
    private LinkedHashMap<AOSR_FIELDS, ArrayList<String>> aosrContentMap;
    protected int aosrNum;
    protected boolean isReady;

    public LinkedHashMap<AOSR_FIELDS, ArrayList<String>> getAosrContentMap() {
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

    public void addValue(double value, AOSR_FIELDS field) {
        String str = String.valueOf((int) value);
        putValue(str, field);
    }

    public void addValue(String material_dataValue, String drawing_and_resultsValue, AOSR_FIELDS field) {
        String attachment = createAttachment(material_dataValue, drawing_and_resultsValue);
        putValue(attachment, field);
    }

    public void addValue(String value, AOSR_FIELDS field) {
        putValue(value, field);
    }

    private String createAttachment(String material_dataValue, String drawing_and_resultsValue) {
        int counter = 0;
        boolean isNext = false;
        StringBuffer builderMaterial = new StringBuffer();
        StringBuffer builderDrawing = new StringBuffer();
        StringBuffer attachmentBuilder = new StringBuffer();
        String[] materialSplit = material_dataValue.split(" ");
        String[] drawingSplit = drawing_and_resultsValue.split(" ");
        for (int i = 0; i < materialSplit.length; i++) {
            String str = materialSplit[i];
            if (!isNext) {
                for (ATTACH_CONST field : ATTACH_CONST.values()) {
                    String testWord = str.toLowerCase();
                    if (testWord.equals(field.toString())) {
                        counter++;
                        builderMaterial.append(counter + "." + firstUpperCase(str));
                        isNext = true;
                        break;
                    }
                }
            } else {
                builderMaterial.append(" " + str);
                if (str.endsWith(",") || i == materialSplit.length - 1) {
                    isNext = false;
                    attachmentBuilder.append(builderMaterial.toString());
                    attachmentBuilder.append(" ");
//                    System.out.println(builderMaterial);
                    builderMaterial.setLength(0);
                }
            }
        }

        isNext = true;
        counter++;
        builderDrawing.append(counter + "." + drawingSplit[0]);
        String nextStr = "";
        for (int i = 1; i < drawingSplit.length; i++) {
            if (!(i == drawingSplit.length - 1)) {
                nextStr = drawingSplit[i + 1];
            } else {
                nextStr = "";
            }
            String str = drawingSplit[i];
            if (isNext) {
                builderDrawing.append(" " + str);
                if (str.endsWith(",") || nextStr.isEmpty()) {
                    if (!nextStr.startsWith("№")) {
                        isNext = false;
//                        System.out.println(builderDrawing);
                        attachmentBuilder.append(builderDrawing.toString());
                        attachmentBuilder.append(" ");
                        builderDrawing.setLength(0);
                    }
                }
            } else {
                isNext = true;
                counter++;
                builderDrawing.append(counter + "." + firstUpperCase(str));
            }
        }
        return attachmentBuilder.toString();
    }


    public String firstUpperCase(String word){
        if(word == null || word.isEmpty()) return "";
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    //
    private void putValue(String text, AOSR_FIELDS field) {
        ArrayList<String> resultStringArray = new ArrayList<>();
        for (AOSR_FIELDS fields : AOSR_FIELDS.values()) {
            if (fields == field) {
                if (fields.getNextRowLength() == 0) {
                    resultStringArray.add(text);
                    aosrContentMap.put(field, resultStringArray);
                    break;
                } else {
                    StringBuffer strB = new StringBuffer();
                    String[] arrStringLine = text.split(" ");
                    String currentWord = arrStringLine[0];
                    String endOfLineWord = null;
                    int maxLineSize = fields.getFirstRowLength();
                    if (fields == field) {
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
                    aosrContentMap.put(field, resultStringArray);
                    break;
                }
            }
        }
    }

    public String getAOSRNum() {
        String aosrNum = null;
        for (Map.Entry<AOSR_FIELDS, ArrayList<String>> entry : aosrContentMap.entrySet()) {
            if (AOSR_FIELDS.NUM_AOSR == entry.getKey()){
                aosrNum = entry.getValue().get(0);
                return aosrNum;
            }
        }
        return aosrNum;
    }

    private String[] cleanStringArr(String[] arr) {
        ArrayList<String> cleandArr = new ArrayList<>();
        for (String str : arr) {
            if (!str.isEmpty()) {
                cleandArr.add(str);
            }
        }
        return (String[]) cleandArr.toArray();
    }
}
