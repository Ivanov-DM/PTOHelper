package ru.ivanov.pto_helper;

public enum AOSR_FIELDS {
    NUM_AOSR(30, 0),                                   //1                       № акта
    DEW(5, 0),                                         //2  DAY OF END WORK      день окончания работ
    MEW(12, 0),                                        //3  MONTH OF END WORK    месяц окончания работ
    YEW(5, 0),                                         //4  YAER OF END WORK     год окончания работ
    WORK_TYPE(64, 126),                                //5                       наименование работ
    PROJECT_DATA(71, 126),                             //6                       наименование проекта
    MATERIAL_DATA(84, 126),                            //7                       наименование применяемых материалов
    DRAWING_AND_RESULTS(126, 126),                     //8                       исполнительные чертежи и результаты испытаний
    DSW(5, 0),                                         //9  DAY OF START WORK    день начал работ
    MSW(12, 0),                                        //10 MONTH OF START WORK  месяц начал работ
    YSW(5, 0),                                         //11 YEAR OF START WORK   год начала работ
    NTD_AND_PROJECT(84, 126),                          //12                      наименование НТД и РД
    NEXT_WORK(71, 126),                                //13                      наименование последующих работ
    ATTACHMENT(126, 126);                              //14                      приложение

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
