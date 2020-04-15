package com.sugar.utils;

import com.alibaba.fastjson.JSONObject;
import com.sugar.pojo.ApiCase;
import com.sugar.pojo.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtils {
    public static String get(ApiCase apiCase) {
        String result = null;
        try {
            // 处理请求参数
            String url = apiCase.getApiInfo().getUrl();
            String requestData = apiCase.getRequestData();
            Map<String, Object> map = JSONObject.parseObject(requestData);
            Set<String> keys = map.keySet();
            List<BasicNameValuePair> params = new ArrayList<>();
            for (String key : keys) {
                params.add(new BasicNameValuePair(key, map.get(key).toString()));
            }
            String paramStr = URLEncodedUtils.format(params, "utf-8");
            url += "?" + paramStr;

            HttpGet get = new HttpGet(url);
            // 处理请求头
            String headersStr = apiCase.getApiInfo().getHeaders();
            List<Header> headers = JSONObject.parseArray(headersStr, Header.class);
            if (headers != null) {
                for (Header header : headers) {
                    get.setHeader(header.getName(), ParamUtils.getFinalStr(header.getValue()));
                }
            }
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(get);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String post(ApiCase apiCase) {
        String result = null;
        try {
            HttpPost post = new HttpPost(apiCase.getApiInfo().getUrl());
            StringEntity stringEntity = new StringEntity(apiCase.getRequestData(), ContentType.APPLICATION_JSON);
            stringEntity.setContentEncoding("utf-8");
            post.setEntity(stringEntity);
            // 处理请求头
            String headersStr = apiCase.getApiInfo().getHeaders();
            List<Header> headers = JSONObject.parseArray(headersStr, Header.class);
            if (headers != null) {
                for (Header header : headers) {
                    post.setHeader(header.getName(), ParamUtils.getFinalStr(header.getValue()));
                }
            }
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String execute(ApiCase apiCase) {
        apiCase.setRequestData(ParamUtils.getFinalStr(apiCase.getRequestData()));
        if ("get".equalsIgnoreCase(apiCase.getApiInfo().getType())) {
            return get(apiCase);
        } else if ("post".equalsIgnoreCase(apiCase.getApiInfo().getType())) {
            return post(apiCase);
        }
        return "";
    }
}
