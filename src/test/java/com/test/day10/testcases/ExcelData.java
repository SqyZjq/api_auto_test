package com.test.day10.testcases;

/**
 * @Desc: Excel数据
 */
public class ExcelData {
    private int caseId;
    private String title;
    private String method;
    private String url;
    private String header;
    private String param;
    private String responseAssert;
    private String extract;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResponseAssert() {
        return responseAssert;
    }

    public void setResponseAssert(String responseAssert) {
        this.responseAssert = responseAssert;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    @Override
    public String toString() {
        return "ExcelData{" +
                "caseId=" + caseId +
                ", title='" + title + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", header='" + header + '\'' +
                ", param='" + param + '\'' +
                ", responseAssert='" + responseAssert + '\'' +
                ", extract='" + extract + '\'' +
                '}';
    }
}
