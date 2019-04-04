package ru.ivanov.pto_helper.model;

public enum AOSR_FIELDS {

    NUM_AOSR(30, 0),                                   //0                       № акта
    DEW(5, 0),                                         //1  DAY OF END WORK      день окончания работ
    MEW(12, 0),                                        //2  MONTH OF END WORK    месяц окончания работ
    YEW(5, 0),                                         //3  YAER OF END WORK     год окончания работ
    WORK_TYPE(60, 120),                                //4                       наименование работ
    PROJECT_DATA(71, 120),                             //5                       наименование проекта
    MATERIAL_DATA(84, 120),                            //6                       наименование применяемых материалов
    DRAWING_AND_RESULTS(120, 120),                     //7                       исполнительные чертежи и результаты испытаний
    DSW(5, 0),                                         //8  DAY OF START WORK    день начал работ
    MSW(12, 0),                                        //9 MONTH OF START WORK  месяц начал работ
    YSW(5, 0),                                         //10 YEAR OF START WORK   год начала работ
    NTD_AND_PROJECT(84, 120),                          //11                      наименование НТД и РД
    NEXT_WORK(71, 120),                                //12                      наименование последующих работ
    ATTACHMENT(120, 120);                              //13                      приложение

    private int firstRowLength;
    private int nextRowLength;

    AOSR_FIELDS(int firstRowLength, int nextRowLength) {
        this.firstRowLength = firstRowLength;
        this.nextRowLength = nextRowLength;
    }

    public int getFirstRowLength() {
        return firstRowLength;
    }

    public int getNextRowLength() {
        return nextRowLength;
    }

}
