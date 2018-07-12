package com.example.core.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.core.http.util.JsonArrayRequest;
import com.example.core.http.util.JsonObjectRequest;
import com.example.core.http.util.MultipartRequest;
import com.example.core.http.util.MyVolley;
import com.example.core.http.util.UTF8StringRequest;
import com.example.core.utils.JsonUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class HttpUtils {

    public static final int INITIAL_TIMEOUT_MS = 20 * 1000;

    public static String get(String url, Map<String, String> map) {
        // 打开浏览器
        HttpClient client = new DefaultHttpClient();
        // 输入网址
        if (!url.contains("?") && map != null) {
            url += "?";
            for (String key : map.keySet()) {
                String value = map.get(key);
                url += key + "=" + value + "&";
            }
        }
        HttpGet get = new HttpGet(url);

        HttpEntity reqEntity;
        try {
            HttpResponse response = client.execute(get);
            HttpEntity resEntity = response.getEntity();
            String content = EntityUtils.toString(resEntity);
            return content;
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String post(String url, Map<String, String> map) {
        // 打开浏览器
        HttpClient client = new DefaultHttpClient();
        // 输入网址
        HttpPost post = new HttpPost(url);
        Set<String> set = map.keySet();
        Iterator<String> itr = set.iterator();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        while (itr.hasNext()) {
            String key = itr.next();
            String value = map.get(key);
            NameValuePair pair1 = new
                    BasicNameValuePair(key, value);
            params.add(pair1);
        }

        HttpEntity reqEntity;
        try {
            reqEntity = new UrlEncodedFormEntity(params, "utf-8");
            post.setEntity(reqEntity);
            // 回车
            // 浏览器显示内容
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            String content = EntityUtils.toString(resEntity);
            return content;
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void getVolley(Context context, String url, final String token,
                                 final com.example.core.http.HttpGet listener) {
        RequestQueue queue = MyVolley.getRequestQueue(context);
        StringRequest myReq = new UTF8StringRequest(Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", token);
                headers.put("postUniqueToken", UUID.randomUUID().toString());
                headers.put("Cookie", token);
                return headers;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, 0, 0f));
        queue.add(myReq);
    }

    public static void postVolley(Context context, String url, final String token,
                                  final Map<String, String> map, final com.example.core.http.HttpPost listener) {
        RequestQueue queue = MyVolley.getRequestQueue(context);
        Response.Listener<String> SuccListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        };
        StringRequest myReq = new UTF8StringRequest(Method.POST, url,
                SuccListener, errorListener) {
            @Override
            protected Map<String, String> getParams()
                    throws AuthFailureError {
                return map;
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", token);
                headers.put("postUniqueToken", UUID.randomUUID().toString());
                headers.put("Cookie", token);
                return headers;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, 0, 0f));
        queue.add(myReq);
    }

    public static void postBytesVolley(Context context, String url, final String token, File file,
                                       final Map<String, String> map, final HttpPostBytes listener) {
        RequestQueue queue = MyVolley.getRequestQueue(context);
        Response.Listener<String> succListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onResponse(response);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        };
        MultipartRequest myReq = new MultipartRequest(url, succListener, errorListener);
        myReq.addHeader("token", token);
        myReq.getMultiPartEntity().addFilePart("file", file);
        myReq.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, 0, 0f));
        queue.add(myReq);
    }



    public static void jsonVolley(Context context, String url, final String token,
                                  final Map<String, String> map, final HttpJsonObject listener) {
        RequestQueue requestQueue = MyVolley.getRequestQueue(context);
        String json = JsonUtil.getInstance().serialize(map);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("token", token);
                headers.put("postUniqueToken", UUID.randomUUID().toString() + System.nanoTime());
                return headers;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, 0, 0f));
        requestQueue.add(jsonRequest);
    }

    public static void jsonArrayVolley(Context context, String url, final String token,
                                       final Map<String, String> map, final HttpJsonArray listener) {
        RequestQueue requestQueue = MyVolley.getRequestQueue(context);
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(map);
        JsonRequest<JSONObject> jsonRequest = new JsonArrayRequest(Method.POST, url, JsonUtil.getInstance().serialize(list),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                headers.put("token", token);
                headers.put("postUniqueToken", UUID.randomUUID().toString() + System.nanoTime());
                return headers;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(INITIAL_TIMEOUT_MS, 0, 0f));
        requestQueue.add(jsonRequest);
    }

    private HttpUtils() {
    }

}
