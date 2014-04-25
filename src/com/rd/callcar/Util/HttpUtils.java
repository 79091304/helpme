package com.rd.callcar.Util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;
/**
 * http����
 * @author zhang_zhanhui
 *
 */
public class HttpUtils {
	
	private HttpParams httpParams = null;
	
    private HttpClient httpClient = null;
    

	  public String doGet(String url, Map params) {
	        /* ����HTTPGet���� */
	        String paramStr = "";
	        Iterator iter = params.entrySet().iterator();
	        while (iter.hasNext()) {
	            Map.Entry entry = (Map.Entry) iter.next();
	            Object key = entry.getKey();
	            Object val = entry.getValue();
	            paramStr += paramStr = "&" + key + "=" + val;
	        }
	        if (!paramStr.equals("")) {
	            paramStr = paramStr.replaceFirst("&", "?");
	            url += paramStr;
	        }
	        HttpGet httpRequest = new HttpGet(url);
	        String strResult = "doGetError";
	        try {
	            /* �������󲢵ȴ���Ӧ */
	            HttpResponse httpResponse = httpClient.execute(httpRequest);
	            /* ��״̬��Ϊ200 ok */
	            if (httpResponse.getStatusLine().getStatusCode() == 200) {
	                /* ���������� */
	                strResult = EntityUtils.toString(httpResponse.getEntity());
	            } else {
	                strResult = "Error Response: "
	                        + httpResponse.getStatusLine().toString();
	            }
	        } catch (ClientProtocolException e) {
	            strResult = e.getMessage().toString();
	            e.printStackTrace();
	        } catch (IOException e) {
	            strResult = e.getMessage().toString();
	            e.printStackTrace();
	        } catch (Exception e) {
	            strResult = e.getMessage().toString();
	            e.printStackTrace();
	        }
	        Log.v("strResult", strResult);
	        return strResult;
	    }
	  
		public String doPost(String url, List<NameValuePair> params) {
	        /* ����HTTPPost���� */
	        HttpPost httpRequest = new HttpPost(url);
	        String strResult = "doPostError";
	        try {
	            /* ������������������� */
	            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
	            /* �������󲢵ȴ���Ӧ */
	            HttpResponse httpResponse = httpClient.execute(httpRequest);
	            /* ��״̬��Ϊ200 ok */
	            if (httpResponse.getStatusLine().getStatusCode() == 200) {
	                /* ���������� */
	                strResult = EntityUtils.toString(httpResponse.getEntity());
	            } else {
	                strResult = "Error Response: "
	                        + httpResponse.getStatusLine().toString();
	            }
	        } catch (ClientProtocolException e) {
	            strResult = e.getMessage().toString();
	            e.printStackTrace();
	        } catch (IOException e) {
	            strResult = e.getMessage().toString();
	            e.printStackTrace();
	        } catch (Exception e) {
	            strResult = e.getMessage().toString();
	            e.printStackTrace();
	        }
	        Log.v("strResult", strResult);
	        return strResult;
	    }
	    
	    public HttpClient getHttpClient() {
	        // ���� HttpParams ���������� HTTP ��������һ���ֲ��Ǳ���ģ�
	        httpParams = new BasicHttpParams();
	        // �������ӳ�ʱ�� Socket ��ʱ���Լ� Socket �����С
	        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
	        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
	        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
	        // �����ض���ȱʡΪ true
	        HttpClientParams.setRedirecting(httpParams, true);
	        // ���� user agent
	        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
	        HttpProtocolParams.setUserAgent(httpParams, userAgent);
	        // ����һ�� HttpClient ʵ��
	        // ע�� HttpClient httpClient = new HttpClient(); ��Commons HttpClient
	        // �е��÷����� Android 1.5 ��������Ҫʹ�� Apache ��ȱʡʵ�� DefaultHttpClient
	        httpClient = new DefaultHttpClient(httpParams);
	        return httpClient;
	    }
}
