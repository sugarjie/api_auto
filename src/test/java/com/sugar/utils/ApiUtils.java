package com.sugar.utils;

import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.sugar.pojo.ApiCase;
import com.sugar.pojo.CellData;
import com.sugar.pojo.ExtractRespData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiUtils {
    private static List<CellData> cellDataList = new ArrayList<>();
    private static Map<String, Object> globalParamMap = new HashMap<>();

    public static void addCellData(CellData cellData) {
        cellDataList.add(cellData);
    }

    public static List<CellData> getCellDatas() {
        return cellDataList;
    }


    public static void addGlobalData(String paramName, Object paramValue) {
        globalParamMap.put(paramName, paramValue);
    }

    public static Object getGlobalParamData(String paramName) {
        return globalParamMap.get(paramName);
    }

    public static void extractRespData(String result, ApiCase apiCase) {
        List<ExtractRespData> extractRespDataList = JSONObject.parseArray(apiCase.getExtractRespData(), ExtractRespData.class);
        if (extractRespDataList != null) {
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(result);
            for (ExtractRespData extractRespData : extractRespDataList) {
                String jsonPath = extractRespData.getJsonPath();
                String paramName = extractRespData.getParamName();
                Object paramValue = JsonPath.read(document, jsonPath);
                ApiUtils.addGlobalData(paramName, paramValue);
            }
        }
    }
}
