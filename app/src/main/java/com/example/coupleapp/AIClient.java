package com.example.coupleapp;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
public class AIClient {
  // To know the path to the api_key
  // Kinda weird TODO find a better solution
  private static Context context;
  private static String TAG = "AIClient";

  public AIClient(Context context) { this.context = context; }

  public static String query(String prompt) {
    String urlString = "https://api.openai.com/v1/chat/completions";
    String apiKey = readApiKey();
    String model = "gpt-3.5-turbo";
    try {
      // 1. Création de la connexion
      URL url = new URL(urlString);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("POST");
      conn.setRequestProperty("Authorization", "Bearer " + apiKey);
      conn.setRequestProperty("Content-Type", "application/json");
      conn.setDoOutput(true);

      // 2. Préparer le JSON de la requête
      JSONObject message = new JSONObject();
      message.put("role", "user");
      message.put("content", prompt);

      JSONArray messages = new JSONArray();
      messages.put(message);

      JSONObject jsonBody = new JSONObject();
      jsonBody.put("model", model);
      jsonBody.put("messages", messages);

      // 3. Envoyer la requête
      OutputStream os = conn.getOutputStream();
      byte[] input = jsonBody.toString().getBytes("utf-8");
      os.write(input, 0, input.length);

      // 4. Lire la réponse
      BufferedReader br = new BufferedReader(
          new InputStreamReader(conn.getInputStream(), "utf-8"));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
        response.append(line.trim());
      }

      br.close();
      conn.disconnect();

      // 5. Extraire le texte retourné par l'API
      JSONObject jsonResponse = new JSONObject(response.toString());
      String reply = jsonResponse.getJSONArray("choices")
                         .getJSONObject(0)
                         .getJSONObject("message")
                         .getString("content");

      Log.d(TAG, "AI reply : " + reply);
      return reply;

    } catch (Exception e) {
      Log.e(TAG, e.getMessage());
      return "Erreur : " + e.getMessage();
    }
  }

  private static String readApiKey() {

    InputStream inputStream =
        context.getResources().openRawResource(R.raw.api_key);
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(inputStream));
    // better string for concat i suppose
    StringBuilder sb = new StringBuilder();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
      reader.close();
    } catch (IOException e) {
      Log.e(TAG, "Reading file error " + e.getMessage());
    }

    String data = sb.toString();
    Log.d(TAG, "readApiKey : " + data);
    return data;
  }
}
