package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.shariat.mysuperapp.R;
import com.shariat.mysuperapp.UI.WeatherFragment;

public class WeatherDetailsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather_details);
    Bundle bundle = getIntent().getExtras();

    getSupportFragmentManager()
        .beginTransaction()
        .add(R.id.weather_fragment, WeatherFragment.newInstance(bundle.getString("name")))
        .commit();
  }
}