package com.test.day04;

/**
 * @Desc: Excel数据
 */
//todo:使用lombok插件，自动生成get/set方法,无参构造,全参构造,toString方法
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
    private String responseAssert;

    public String getResponseAssert() {
        return responseAssert;
    }

    public void setResponseAssert(String responseAssert) {
        this.responseAssert = responseAssert;
    }

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
