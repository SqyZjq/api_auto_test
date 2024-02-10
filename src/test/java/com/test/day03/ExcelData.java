package com.test.day03;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Desc: Excel数据
 */
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class ExcelData {
    private int caseId;
    private String title;
    private String method;
    private String url;
    private String header;
    private String param;

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

    @Override
    public String toString() {
        return "ExcelData{" +
                "caseId=" + caseId +
                ", title='" + title + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", header='" + header + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
