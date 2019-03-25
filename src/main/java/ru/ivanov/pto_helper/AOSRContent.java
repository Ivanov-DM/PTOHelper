package ru.ivanov.pto_helper;

public class AOSRContent {
    private String NUM_AOSR;
    private String DEW;                     // DAY OF END WORK      день окончания работ
    private String MEW;                     // MONTH OF END WORK    месяц окончания работ
    private String YEW;                     // YAER OF END WORK     год окончания работ
    private String WORK_TYPE;               //                      наименование работ
    private String PROJECT_DATA;            //                      наименование проекта
    private String MATERIAL_DATA;           //                      наименование применяемых материалов
    private String DRAWING_AND_RESULTS;     //                      исполнительные чертежи и результаты испытаний
    private String DSW;                     // DAY OF START WORK    день начал работ
    private String MSW;                     // MONTH OF START WORK  месяц начал работ
    private String YSW;                     // YEAR OF START WORK   год начала работ
    private String NTD_AND_PROJECT;         //                      наименование НТД и РД
    private String NEXT_WORK;               //                      наименование последующих работ
    private String ATTACHMENT;              //                      приложение

    public AOSRContent(String NUM_AOSR, String DEW, String MEW, String YEW, String WORK_TYPE,
                       String PROJECT_DATA, String MATERIAL_DATA, String DRAWING_AND_RESULTS,
                       String DSW, String MSW, String YSW, String NTD_AND_PROJECT,
                       String NEXT_WORK, String ATTACHMENT) {
        this.NUM_AOSR = NUM_AOSR;
        this.DEW = DEW;
        this.MEW = MEW;
        this.YEW = YEW;
        this.WORK_TYPE = WORK_TYPE;
        this.PROJECT_DATA = PROJECT_DATA;
        this.MATERIAL_DATA = MATERIAL_DATA;
        this.DRAWING_AND_RESULTS = DRAWING_AND_RESULTS;
        this.DSW = DSW;
        this.MSW = MSW;
        this.YSW = YSW;
        this.NTD_AND_PROJECT = NTD_AND_PROJECT;
        this.NEXT_WORK = NEXT_WORK;
        this.ATTACHMENT = ATTACHMENT;
    }

    public String getNUM_AOSR() {
        return NUM_AOSR;
    }

    public void setNUM_AOSR(String NUM_AOSR) {
        this.NUM_AOSR = NUM_AOSR;
    }

    public String getDEW() {
        return DEW;
    }

    public void setDEW(String DEW) {
        this.DEW = DEW;
    }

    public String getMEW() {
        return MEW;
    }

    public void setMEW(String MEW) {
        this.MEW = MEW;
    }

    public String getYEW() {
        return YEW;
    }

    public void setYEW(String YEW) {
        this.YEW = YEW;
    }

    public String getWORK_TYPE() {
        return WORK_TYPE;
    }

    public void setWORK_TYPE(String WORK_TYPE) {
        this.WORK_TYPE = WORK_TYPE;
    }

    public String getPROJECT_DATA() {
        return PROJECT_DATA;
    }

    public void setPROJECT_DATA(String PROJECT_DATA) {
        this.PROJECT_DATA = PROJECT_DATA;
    }

    public String getMATERIAL_DATA() {
        return MATERIAL_DATA;
    }

    public void setMATERIAL_DATA(String MATERIAL_DATA) {
        this.MATERIAL_DATA = MATERIAL_DATA;
    }

    public String getDRAWING_AND_RESULTS() {
        return DRAWING_AND_RESULTS;
    }

    public void setDRAWING_AND_RESULTS(String DRAWING_AND_RESULTS) {
        this.DRAWING_AND_RESULTS = DRAWING_AND_RESULTS;
    }

    public String getDSW() {
        return DSW;
    }

    public void setDSW(String DSW) {
        this.DSW = DSW;
    }

    public String getMSW() {
        return MSW;
    }

    public void setMSW(String MSW) {
        this.MSW = MSW;
    }

    public String getYSW() {
        return YSW;
    }

    public void setYSW(String YSW) {
        this.YSW = YSW;
    }

    public String getNTD_AND_PROJECT() {
        return NTD_AND_PROJECT;
    }

    public void setNTD_AND_PROJECT(String NTD_AND_PROJECT) {
        this.NTD_AND_PROJECT = NTD_AND_PROJECT;
    }

    public String getNEXT_WORK() {
        return NEXT_WORK;
    }

    public void setNEXT_WORK(String NEXT_WORK) {
        this.NEXT_WORK = NEXT_WORK;
    }

    public String getATTACHMENT() {
        return ATTACHMENT;
    }

    public void setATTACHMENT(String ATTACHMENT) {
        this.ATTACHMENT = ATTACHMENT;
    }
}