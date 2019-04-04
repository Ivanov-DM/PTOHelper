package ru.ivanov.pto_helper.model;

public class AOSRForUI {

    private Integer num;
    private String aosrNum;
    private String workName;

    public AOSRForUI(Integer num, String aosrNum, String workName) {
        this.num = num;
        this.aosrNum = aosrNum;
        this.workName = workName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAosrNum() {
        return aosrNum;
    }

    public void setAosrNum(String aosrNum) {
        this.aosrNum = aosrNum;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
}

