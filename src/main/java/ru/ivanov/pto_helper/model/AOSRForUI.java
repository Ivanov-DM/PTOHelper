package ru.ivanov.pto_helper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AOSRForUI {

    private Integer num;
    private StringProperty aosrNum;
    private StringProperty workName;

    public AOSRForUI(Integer num, String aosrNum, String workName) {
        this.num = num;
        this.aosrNum = new SimpleStringProperty(aosrNum);
        this.workName =new SimpleStringProperty(workName);
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getAosrNum() {
        return aosrNum.get();
    }

    public StringProperty aosrNumProperty() {
        return aosrNum;
    }

    public void setAosrNum(String aosrNum) {
        this.aosrNum.set(aosrNum);
    }

    public String getWorkName() {
        return workName.get();
    }

    public StringProperty workNameProperty() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName.set(workName);
    }
}

