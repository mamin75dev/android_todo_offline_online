package com.shariat.mysuperapp.UI;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.shariat.mysuperapp.Data.RequestFetchWeatherData;
import com.shariat.mysuperapp.R;

import org.json.JSONObject;

public class WeatherFragment extends Fragment {

  TextView cityTV, weatherTV, degreeTV, humidityTV, timeTV;

  private final String LOG_TAG = this.getClass().getSimpleName();

  private RequestQueue queue;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.weather_card, container, false);
    cityTV = view.findViewById(R.id.city_tv);
    weatherTV = view.findViewById(R.id.weather_tv);
    degreeTV = view.findViewById(R.id.degree_tv);
    humidityTV = view.findViewById(R.id.humidity_tv);
    timeTV = view.findViewById(R.id.time_tv);
    queue = Volley.newRequestQueue(getContext());
//    setData();
    requestData("Tehran");
    return view;
  }

  private void setData() {
    weatherTV.setText(Html.fromHtml(getResources().getString(R.string.wi_forecast_io_clear_day)));
  }

  private void requestData(String city) {
    RequestFetchWeatherData request = new RequestFetchWeatherData(
        Request.Method.GET,
        city,
        null,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Log.i(LOG_TAG, "response: \n" + response);
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.i(LOG_TAG, error.getMessage());
          }
        }
    );
    queue.add(request);
  }
}
