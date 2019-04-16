package ru.ivanov.pto_helper.model;

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
    private void putValue(String text, AOSR_FIELDS currentField) {
        ArrayList<String> resultStringArray = new ArrayList<>();
        String str = text.replaceAll("\\s+", " ");
        for (AOSR_FIELDS field : AOSR_FIELDS.values()) {
            if (field == currentField) {
                if (field.getNextRowLength() == 0) {
                    resultStringArray.add(str);
                    aosrContentMap.put(currentField, resultStringArray);
                    break;
                } else {
                    StringBuffer sb = new StringBuffer();
                    String[] arrStringLine = str.split(" ");
                    String currentWord = arrStringLine[0];
                    String endOfLineWord = null;
                    int maxLineSize = field.getFirstRowLength();
                    for (int i = 1; i <= arrStringLine.length; i++) {
                        sb.append(currentWord + " ");
                        if (sb.length() < maxLineSize) {
                            if (i < arrStringLine.length) {
                                currentWord = arrStringLine[i];
                                endOfLineWord = currentWord;
                            } else {
                                sb.delete(sb.length() - 1, sb.length());
                            }
                        } else {
                            maxLineSize = field.getNextRowLength();
                            sb.delete(sb.length() - endOfLineWord.length() - 2, sb.length());
                            resultStringArray.add(sb.toString());
                            sb.setLength(0);
                            i--;
                        }
                    }

                    resultStringArray.add(sb.toString());
//                    for (String o : resultStringArray) {
//                        System.out.println(o.length());
//                        System.out.println(o);
//                    }
                    aosrContentMap.put(currentField, resultStringArray);
                    break;
                }
            }
        }
    }

    public String getAOSRNum() {
        String aosrNum = "";
        for (Map.Entry<AOSR_FIELDS, ArrayList<String>> entry : aosrContentMap.entrySet()) {
            if (AOSR_FIELDS.NUM_AOSR == entry.getKey()){
                ArrayList<String> arr = entry.getValue();
                for (String o : arr) {
                    aosrNum += o;
                }
                return aosrNum;
            }
        }
        return aosrNum;
    }

    public String getWorkName() {
        String workName = "";
        for (Map.Entry<AOSR_FIELDS, ArrayList<String>> entry : aosrContentMap.entrySet()) {
            if (AOSR_FIELDS.WORK_TYPE == entry.getKey()){
                ArrayList<String> arr = entry.getValue();
                for (String o : arr) {
                    workName += o;
                }
                return workName;
            }
        }
        return workName;
    }

    public String getRDNum() {
        String rdNum="";
        for (Map.Entry<AOSR_FIELDS, ArrayList<String>> entry : aosrContentMap.entrySet()) {
            if (AOSR_FIELDS.PROJECT_DATA == entry.getKey()) {
                ArrayList<String> arr = entry.getValue();
                for (String o : arr) {
                    rdNum += o;
                }
                break;
            }
        }
        StringBuffer sb = new StringBuffer();
        int letter = sb.length();
        String[] strInSplit = rdNum.split(",");
        for (String o : strInSplit) {
            if (o.contains("шифр проекта")) {
                String strOut1 = o.replaceAll(" ", "");
                String strOut2 = strOut1.replaceAll("шифрпроекта", "");
                sb.append(strOut2 + ", ");
                if (sb.indexOf("и") > 0) {
                    int i = strOut2.indexOf('и') + letter;
                    sb.insert(i, " ");
                    letter =  sb.length();
                }
            }
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

    public String getAOSRDate() {
        StringBuffer aosrDate = new StringBuffer();
        for (Map.Entry<AOSR_FIELDS, ArrayList<String>> entry : aosrContentMap.entrySet()) {
            if (AOSR_FIELDS.DSW == entry.getKey()) {
                aosrDate.append(entry.getValue().get(0) + " ");
                continue;
            }
            if (AOSR_FIELDS.MSW == entry.getKey()) {
                aosrDate.append(entry.getValue().get(0) + " ");
                continue;
            }
            if (AOSR_FIELDS.YSW == entry.getKey()) {
                aosrDate.append(entry.getValue().get(0) + " - ");
                continue;
            }
            if (AOSR_FIELDS.DEW == entry.getKey()) {
                aosrDate.append(entry.getValue().get(0) + " ");
                continue;
            }
            if (AOSR_FIELDS.MEW == entry.getKey()) {
                aosrDate.append(entry.getValue().get(0) + " ");
                continue;
            }
            if (AOSR_FIELDS.YEW == entry.getKey()) {
                aosrDate.append(entry.getValue().get(0));
                continue;
            }
        }
        return aosrDate.toString();
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
