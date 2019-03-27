package com.gmail.khitirinikoloz.objects;

public class Subject {

    private int id;
    private String subjName;
    private int creditNum;

    public Subject(int id, String subjName, int creditNum) {
        this.id = id;
        this.subjName = subjName;
        this.creditNum = creditNum;
    }

    public Subject(String subjName, int creditNum) {
        this.subjName = subjName;
        this.creditNum = creditNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjName() {
        return subjName;
    }

    public void setSubjName(String subjName) {
        this.subjName = subjName;
    }

    public int getCreditNum() {
        return creditNum;
    }

    public void setCreditNum(int creditNum) {
        this.creditNum = creditNum;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", subjName='" + subjName + '\'' +
                ", creditNum=" + creditNum +
                '}';
    }
}
