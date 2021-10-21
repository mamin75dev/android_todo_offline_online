package com.shariat.mysuperapp.Components;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.shariat.mysuperapp.R;

public class WeatherTextView extends AppCompatTextView {
  public WeatherTextView(Context context) {
    super(context);
    init(context);
  }

  public WeatherTextView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public WeatherTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/weather_icons.ttf");
    this.setTypeface(typeface);
  }

  public void setWeatherIcon(int weatherId) {
    String weatherIconCode = "";
    if (weatherId == 800) {
      weatherIconCode = getResources().getString(R.string.wi_forecast_io_clear_day);
    } else {
      switch (weatherId / 100) {
        case 2:
          weatherIconCode = getResources().getString(R.string.wi_day_thunderstorm);
        case 3:
        case 5:
          weatherIconCode = getResources().getString(R.string.wi_day_rain);
        case 6:
          weatherIconCode = getResources().getString(R.string.wi_day_snow);
        case 7:
          weatherIconCode = getResources().getString(R.string.wi_forecast_io_clear_day);
        case 8:
          weatherIconCode = getResources().getString(R.string.wi_day_cloudy);
      }
    }
    this.setText(Html.fromHtml(weatherIconCode));
  }

}
