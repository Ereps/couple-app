package com.llm;

public class Main {
  public static void main(String[] args) {
    LlmClient client = new LlmClient();
    try {

      System.out.println(client.GenerateText("Fait un message rigolo"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
