package com.llm;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

import java.net.URISyntaxException;
import com.llm.exception.LlmExceptions.InvalidPromtException;
import com.llm.exception.LlmExceptions.LlmException;
import org.json.JSONArray;
import org.json.JSONObject;

public class LlmClient {
  private String endpoint;
  private String key;
  private String model;

  /**
   * Default constructor
   **/
  LlmClient() {

    endpoint = "https://models.github.ai/inference/chat/completions";
    model = "openai/gpt-4o";
    key = System.getenv("API_KEY");
  }

  String GenerateText(String prompt) throws LlmException, URISyntaxException, InterruptedException, IOException {
    if (key == null) {
      throw new LlmException("ERROR : api key not found");
    }

    JSONObject message = new JSONObject();

    message.put("role", "user");
    message.put("content", prompt);

    JSONArray messages = new JSONArray();
    messages.put(message);

    JSONObject jsonBody = new JSONObject();

    jsonBody.put("model", model);
    jsonBody.put("messages", messages);

    HttpRequest postRequest = HttpRequest.newBuilder()
        .uri(new URI(endpoint))
        .header("Authorization", "Bearer " + key)
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
        .build();
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

    System.out.println(postResponse.body());
    // JSONObject rawResponse = new JSONObject(postResponse.body());
    //
    // if (rawResponse.has("error")) {
    // throw new InvalidPromtException("Invalid Prompt " +
    // rawResponse.getJSONObject("error"));
    // }
    // JSONObject parsedJson = rawResponse.getJSONArray("choices").getJSONObject(0);
    // JSONObject messageResponse = parsedJson.getJSONObject("message");
    // String content = messageResponse.getString("content");
    // return content;
    return "toto";
  }
}
