package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shariat.mysuperapp.R;

public class WeatherActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather);

    getSupportFragmentManager().beginTransaction().add(R.id.weather_fragment, WeatherFragment.newInstance("London")).commit();
  }
}