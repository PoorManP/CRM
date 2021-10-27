package com.liujie.crm.workbench.domain;

public class ClueCondition {

    private int pageSize;
    private int pageNo;
    private String name;
    private String company;
    private String mphone;
    private String source;
    private String owner;
    private String phone;
    private String clueState;

    private Integer skipCount;

    @Override
    public String toString() {
        return "ClueCondition{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", mphone='" + mphone + '\'' +
                ", source='" + source + '\'' +
                ", owner='" + owner + '\'' +
                ", phone='" + phone + '\'' +
                ", clueState='" + clueState + '\'' +
                ", skipCount=" + skipCount +
                '}';
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClueState() {
        return clueState;
    }

    public void setClueState(String clueState) {
        this.clueState = clueState;
    }

    public Integer getSkipCount() {
        return skipCount;
    }

    public void setSkipCount(Integer skipCount) {
        this.skipCount = skipCount;
    }
}
