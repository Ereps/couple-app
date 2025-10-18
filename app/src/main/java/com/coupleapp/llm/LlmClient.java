package com.coupleapp.llm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.coupleapp.llm.exception.LlmExceptions.InvalidPromptException;
import com.coupleapp.llm.exception.LlmExceptions.LlmException;

import org.json.JSONArray;
import org.json.JSONObject;

public class LlmClient {
    private String endpoint;
    private String key;
    private String model;
    private Context context;

    public LlmClient(Context context, String key) {
        this.context = context;
        this.key = key;
        this.endpoint = "https://models.github.ai/inference/chat/completions";
        this.model = "openai/gpt-4.1";
    }

    public void generateText(String prompt, LlmCallback callback) {
        if (key == null) {
            callback.onError(new LlmException("ERROR: API key not found"));
            return;
        }

        try {
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);

            JSONArray messages = new JSONArray();
            messages.put(message);

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("model", model);
            jsonBody.put("messages", messages);

            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    endpoint,
                    jsonBody,
                    response -> {
                        try {
                            if (response.has("error")) {
                                callback.onError(new InvalidPromptException(
                                        "Invalid Prompt: " + response.getJSONObject("error")
                                ));
                                return;
                            }
                            JSONObject parsedJson = response.getJSONArray("choices").getJSONObject(0);
                            JSONObject messageResponse = parsedJson.getJSONObject("message");
                            String content = messageResponse.getString("content");
                            callback.onSuccess(content);
                        } catch (Exception e) {
                            callback.onError(e);
                        }
                    },
                    error -> {
                        Log.e("LlmClient", "generateText error: " + error.getLocalizedMessage());
                        callback.onError(error);
                    }
            ) {
                @Override
                public java.util.Map<String, String> getHeaders() {
                    java.util.Map<String, String> headers = new java.util.HashMap<>();
                    headers.put("Authorization", "Bearer " + key);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(request);

        } catch (Exception e) {
            callback.onError(e);
        }
    }

    public interface LlmCallback {
        void onSuccess(String text);
        void onError(Exception e);
    }
}

