package com.sugar.pojo;

import java.util.List;

public class ApiCase extends ExcelObject {
  private String caseId;
  private String apiId;
  private String requestData;
  private String responseData;
  private String extractRespData;
  private String expectedRespKeyInfo;
  private ApiInfo apiInfo;
  private List<SqlCheck> bfSqlChecks;
  private List<SqlCheck> afSqlChecks;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    public String getExtractRespData() {
        return extractRespData;
    }

    public void setExtractRespData(String extractRespData) {
        this.extractRespData = extractRespData;
    }

    public String getExpectedRespKeyInfo() {
        return expectedRespKeyInfo;
    }

    public void setExpectedRespKeyInfo(String expectedRespKeyInfo) {
        this.expectedRespKeyInfo = expectedRespKeyInfo;
    }

    public ApiInfo getApiInfo() {
        return apiInfo;
    }

    public void setApiInfo(ApiInfo apiInfo) {
        this.apiInfo = apiInfo;
    }

    public List<SqlCheck> getBfSqlChecks() {
        return bfSqlChecks;
    }

    public void setBfSqlChecks(List<SqlCheck> bfSqlChecks) {
        this.bfSqlChecks = bfSqlChecks;
    }

    public List<SqlCheck> getAfSqlChecks() {
        return afSqlChecks;
    }

    public void setAfSqlChecks(List<SqlCheck> afSqlChecks) {
        this.afSqlChecks = afSqlChecks;
    }

    @Override
    public String toString() {
        return "ApiCase{" +
                "rowNo='" + getRowNo() + '\'' +
                "caseId='" + caseId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", requestData='" + requestData + '\'' +
                ", responseData='" + responseData + '\'' +
                ", extractRespData='" + extractRespData + '\'' +
                ", expectedRespKeyInfo='" + expectedRespKeyInfo + '\'' +
                ", apiInfo=" + apiInfo +
                ", bfSqlChecks=" + bfSqlChecks +
                ", afSqlChecks=" + afSqlChecks +
                '}';
    }
}
