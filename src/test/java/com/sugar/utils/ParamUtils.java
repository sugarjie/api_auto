package com.sugar.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParamUtils {
    public static String getFinalStr(String reqStr) {
        String regex = "\\$\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(reqStr);
        while (matcher.find()) {
            String totalStr = matcher.group(0);
            String parmName = matcher.group(1);
            Object paramValue = ApiUtils.getGlobalParamData(parmName);
            if (paramValue != null) {
                reqStr = reqStr.replace(totalStr, paramValue.toString());
            }
        }
        return reqStr;
    }

    public static void main(String[] args) {
        System.out.println( getFinalStr("{ \"mobile_phone\": \"${mobile_phone}\",\"pwd\": \"12345678\"}"));
    }
}
