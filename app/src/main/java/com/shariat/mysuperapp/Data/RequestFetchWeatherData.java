package com.shariat.mysuperapp.Data;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Locale;

public class RequestFetchWeatherData extends JsonObjectRequest {

  public static final String APP_KEY = "734adc24ded4d6f5b88e0ec4c23dba8b";
  public static final String URL_FORMAT = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=" + APP_KEY;

  public RequestFetchWeatherData(
      int method,
      String cityName,
      @Nullable JSONObject jsonRequest,
      Response.Listener<JSONObject> listener,
      @Nullable Response.ErrorListener errorListener
  ) {
    super(method, String.format(Locale.getDefault(), URL_FORMAT, cityName), jsonRequest, listener, errorListener);
  }
}
