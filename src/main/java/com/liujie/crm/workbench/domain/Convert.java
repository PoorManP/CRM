package com.liujie.crm.workbench.domain;

public class Convert {
    private String flag;
    private String clueId;
    private String money;
    private String name;
    private String expectedDate;
    private String staga;
    private String activityId;
/*    flag	clueId  money  name  expectedDate  staga  activityId*/
    @Override
    public String toString() {
        String tradeName;
        return "Convert{" +
                "flag='" + flag + '\'' +
                ", clueId='" + clueId + '\'' +
                ", money='" + money + '\'' +
                ", tradeName='" + name + '\'' +
                ", expectedDate='" + expectedDate + '\'' +
                ", staga='" + staga + '\'' +
                ", activityId='" + activityId + '\'' +
                '}';
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getClueId() {
        return clueId;
    }

    public void setClueId(String clueId) {
        this.clueId = clueId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTradeName() {
        return name;
    }

    public void setTradeName(String name) {
        this.name = name;
    }

    public String getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(String expectedDate) {
        this.expectedDate = expectedDate;
    }

    public String getStaga() {
        return staga;
    }

    public void setStaga(String staga) {
        this.staga = staga;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
