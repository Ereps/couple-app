package com.llm;

import org.junit.jupiter.api.*;

public class MainTest {

  @Test
  public void fakeTest() {

  }

  public static void main(String[] args) {
    try {
      LlmClient client = new LlmClient();
      System.out.println(client.GenerateText("Hello world"));
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

}
