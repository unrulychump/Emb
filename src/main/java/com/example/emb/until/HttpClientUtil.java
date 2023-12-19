package com.example.emb.until;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    /**
     * 发送GET请求
     * @param url 请求URL
     * @param params 请求参数
     * @return 响应结果
     * @throws IOException
     * @throws ClientProtocolException
     * @throws URISyntaxException
     */
    public static String doGet(String url, Map<String, String> params) throws ClientProtocolException, IOException, URISyntaxException {
        HttpClient httpClient = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }
        URI uri = uriBuilder.build();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity, "UTF-8");
        } else {
            return null;
        }
    }

    /**
     * 发送POST请求
     * @param url 请求URL
     * @param params 请求参数
     * @return 响应结果
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String doPost(String url, Map<String, String> params) throws ClientProtocolException, IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        if (params != null) {
            List<NameValuePair> nameValuePairs = createNameValuePair(params);
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        }
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            return EntityUtils.toString(entity, "UTF-8");
        } else {
            return null;
        }
    }

    /**
     * 创建NameValuePair列表
     * @param params 参数Map
     * @return NameValuePair列表
     */
    private static List<NameValuePair> createNameValuePair(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return nameValuePairs;
    }

    public static String doGet1(String url, String accessTokenParams) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 拼接accessTokenParams到url后面
            url += "?" + accessTokenParams;
            HttpGet httpGet = new HttpGet(url);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
