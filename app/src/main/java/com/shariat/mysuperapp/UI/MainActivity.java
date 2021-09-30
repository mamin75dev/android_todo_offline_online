package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shariat.mysuperapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  AppCompatButton onlineUseButton, offlineUseButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    onlineUseButton = findViewById(R.id.online);
    offlineUseButton = findViewById(R.id.offline);
    offlineUseButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.online:
        break;
      case R.id.offline:
        Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
        startActivity(intent);
    }
  }
}