package com.sugar.utils;

import com.alibaba.fastjson.JSONObject;
import com.sugar.pojo.ApiCase;
import com.sugar.pojo.CellData;
import com.sugar.pojo.SqlCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlCheckUtils {
    public static void bfCheck(ApiCase apiCase) {
        check(apiCase, 1);
    }

    public static void afCheck(ApiCase apiCase) {
        check(apiCase, 2);
    }

    public static void check(ApiCase apiCase, int type) {
        List<SqlCheck> sqlCheckList = new ArrayList<>();
        if (type == 1) {
            sqlCheckList = apiCase.getBfSqlChecks();
        } else {
            sqlCheckList = apiCase.getAfSqlChecks();
        }
        if (sqlCheckList != null) {
            for (SqlCheck sqlCheck : sqlCheckList) {
                String expected = sqlCheck.getExpected();
                String sql=sqlCheck.getSql();
                List<Map<String, Object>> lst = DBUtils.executeQuery(sql);
                String actual = JSONObject.toJSONString(lst);
                CellData cellData = new CellData(2, sqlCheck.getRowNo(), 6, actual);
                ApiUtils.addCellData(cellData);
                if (expected.equals(actual)) {
                    cellData = new CellData(2, sqlCheck.getRowNo(), 7, "P");
                    ApiUtils.addCellData(cellData);
                } else {
                    cellData = new CellData(2, sqlCheck.getRowNo(), 7, "N");
                    ApiUtils.addCellData(cellData);
                }
            }
        }


    }


}
