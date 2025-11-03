package com.coupleapp.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.util.Date;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class HttpClient {
    private static String endpoint = "localhost:8000/api";
    private Context context;

    public HttpClient(Context context) {
        this.context = context;
    }

    public void getQuestion(Date date, HttpCallback callback) {

        try {
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET,
                    endpoint,
                    null,
                    response -> {
                        try {
                            String question = response.getString("question");
                            // String date = response.getString("date");
                            callback.onSuccess(question);
                        } catch (Exception e) {
                            callback.onError(e);
                        }
                    },
                    error -> {
                        Log.e("HttpClient", "getQuestion error" + error.getLocalizedMessage());
                        callback.onError(error);
                    }
            ) {
                @Override
                public java.util.Map<String, String> getHeaders() {
                    java.util.Map<String, String> headers = new java.util.HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(request);

        } catch (Exception e) {
            callback.onError(e);
        }
    }

    public interface HttpCallback {
        void onSuccess(String text);
        void onError(Exception e);
    }
}

