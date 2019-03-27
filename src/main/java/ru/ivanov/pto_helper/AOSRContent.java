package ru.ivanov.pto_helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class AOSRContent {
    private LinkedHashMap<String, ArrayList<String>> aosrContent;
    protected int aosrNum;
    private AOSR_FIELDS fields;
    private boolean isReady;
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

    public boolean isReady() {
        return isReady;
    }

    public AOSRContent() {
        initMap();
        aosrNum = 0;
        isReady = false;
    }

    public void putValue(String key, ArrayList<String> text) {
        aosrContent = new LinkedHashMap<>();
        aosrContent.put(key, text);
    }

    private void initMap() {
        aosrContent = new LinkedHashMap<>(32);
        for (AOSR_FIELDS fields : AOSR_FIELDS.values()) {
            aosrContent.put(fields.toString(), new ArrayList<String>(1000));
        }
    }

}
