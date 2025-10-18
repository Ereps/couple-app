package com.coupleapp.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.coupleapp.llm.*;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(
        findViewById(R.id.main), (v, insets) -> {
          Insets systemBars =
              insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right,
                       systemBars.bottom);
          return insets;
        });
  }
  public void boutonTest(View view) {
      LlmClient client = new LlmClient(this,BuildConfig.API_KEY);
    TextView tv = findViewById(R.id.textViewMainPage);
    try{
        client.generateText("Donne moi une question de couple vraiment intéressante, pas de message en plus, juste la question. Je veux que la question soit positive ou bien mene a quelque chose de positif.", new LlmClient.LlmCallback(){

        @Override
        public void onSuccess(String text){
          tv.setText(text);
        }
        @Override
        public void onError(Exception e){
          Log.d("MainActivity",e.getLocalizedMessage());
        }

      });
    }
    catch (Exception e){
        Log.e("TOTO_TEST", Objects.requireNonNull(e.getLocalizedMessage()));
    }
  }
}
