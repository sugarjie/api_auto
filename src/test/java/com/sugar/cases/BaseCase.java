package com.sugar.cases;

import com.sugar.pojo.*;
import com.sugar.utils.*;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCase {
    @BeforeSuite
    public void beforeSuite() {
        ApiUtils.addGlobalData("mobile_phone", "18301256355");
    }

    @DataProvider
    public Object[][] getData() {
        //用例
        List<Object> apiCases = ExcelUtils.read("/test_case.xlsx", 0, ApiCase.class);
        //接口详细信息
        List<Object> apiInfos = ExcelUtils.read("/test_case.xlsx", 1, ApiInfo.class);
        //表数据验证信息
        List<Object> sqlChecks = ExcelUtils.read("/test_case.xlsx", 2, SqlCheck.class);
        Object[][] datas = new Object[apiCases.size()][];
        // 存放apiId 和ApiInfo对应关系
        Map<String, ApiInfo> apiInfoMap = new HashMap<>();
        for (Object object : apiInfos) {
            ApiInfo apiInfo = (ApiInfo) object;
            apiInfoMap.put(apiInfo.getApiId(), apiInfo);
        }
        Map<String, List<SqlCheck>> sqlCheckMap = new HashMap<>();
        for (Object object : sqlChecks) {
            SqlCheck sqlCheck = (SqlCheck) object;
            List<SqlCheck> sqlCheckList = sqlCheckMap.get(sqlCheck.getCaseId() + "_" + sqlCheck.getType());
            if (sqlCheckList == null) {
                sqlCheckList = new ArrayList<>();
            }
            sqlCheckList.add(sqlCheck);
            sqlCheckMap.put(sqlCheck.getCaseId() + "_" + sqlCheck.getType(),sqlCheckList);
        }
        // 重新处理用例
        int index = 0;
        for (Object object : apiCases) {
            ApiCase apiCase = (ApiCase) object;
            apiCase.setApiInfo(apiInfoMap.get(apiCase.getApiId()));
            apiCase.setBfSqlChecks(sqlCheckMap.get(apiCase.getCaseId()+"_"+"bf"));
            apiCase.setAfSqlChecks(sqlCheckMap.get(apiCase.getCaseId()+"_"+"af"));
            Object[] item = {apiCase};
            datas[index] = item;
            index++;
        }
        return datas;

    }

    @Test(dataProvider = "getData")
    public void test(ApiCase apiCase) {
        //前置验证
        SqlCheckUtils.bfCheck(apiCase);
        //发包
        String result = HttpUtils.execute(apiCase);
        //抽取数据
        ApiUtils.extractRespData(result, apiCase);
        //提取要回写数据
        CellData cellData = new CellData(0, apiCase.getRowNo(), 4, result);
        ApiUtils.addCellData(cellData);
        //后置验证
        SqlCheckUtils.afCheck(apiCase);
        //断言
        AssertUtils.assertExpectedRespKeyInfo(result, apiCase);
        System.out.println(result);
    }

    @AfterSuite
    public void afterSuite() {
        ExcelUtils.writeBatch("/test_case.xlsx", "src/test/resources/test_case_bak.xlsx");
    }

}
