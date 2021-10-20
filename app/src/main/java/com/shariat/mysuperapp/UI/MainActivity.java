package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shariat.mysuperapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.online).setOnClickListener(this);
    findViewById(R.id.offline).setOnClickListener(this);
    findViewById(R.id.test_button).setOnClickListener(this);
    findViewById(R.id.volley_button).setOnClickListener(this);
    findViewById(R.id.weather_button).setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.online:
        startActivity(new Intent(MainActivity.this, OnlineActivity.class));
        break;
      case R.id.offline:
        startActivity(new Intent(MainActivity.this, OfflineActivity.class));
        break;
      case R.id.test_button:
        startActivity(new Intent(MainActivity.this, HttpTestActivity.class));
        break;
      case R.id.volley_button:
        startActivity(new Intent(MainActivity.this, VolleyActivity.class));
        break;
      case R.id.weather_button:
        startActivity(new Intent(MainActivity.this, WeatherActivity.class));
        break;
    }
  }
}