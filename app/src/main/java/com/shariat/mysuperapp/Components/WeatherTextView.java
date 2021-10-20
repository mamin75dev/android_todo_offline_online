package com.shariat.mysuperapp.Components;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

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
}
