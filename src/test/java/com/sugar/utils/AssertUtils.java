package com.sugar.utils;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.sugar.pojo.ApiCase;
import com.sugar.pojo.ExpectedRespKeyInfo;
import org.testng.Assert;

import java.util.List;

public class AssertUtils {
    public static void assertExpectedRespKeyInfo(String result, ApiCase apiCase) {
        List<ExpectedRespKeyInfo> expectedRespKeyInfos = JSONObject.parseArray(apiCase.getExpectedRespKeyInfo(), ExpectedRespKeyInfo.class);
        if (expectedRespKeyInfos != null) {
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(result);
            for (ExpectedRespKeyInfo expectedRespKeyInfo : expectedRespKeyInfos) {
                String jsonPath = expectedRespKeyInfo.getJsonPath();
                Object expected = expectedRespKeyInfo.getExpected();
                Object actual = JsonPath.read(document,jsonPath);
              Assert.assertEquals(actual,expected);
            }
        }
    }
}
