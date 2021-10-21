package com.shariat.mysuperapp.UI;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFragment extends Fragment {

  TextView cityTV, weatherTV, degreeTV, humidityTV, timeTV, descTV, windSpeedTV;
  ImageView windImage, humidityImage;
  ProgressBar progressBar;

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
//    timeTV = view.findViewById(R.id.time_tv);
    descTV = view.findViewById(R.id.desc_tv);
    windSpeedTV = view.findViewById(R.id.wind_speed_tv);
    windImage = view.findViewById(R.id.wind_img);
    humidityImage = view.findViewById(R.id.humidity_img);
    progressBar = view.findViewById(R.id.progress_bar);
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
            try {
              windImage.setVisibility(View.VISIBLE);
              humidityImage.setVisibility(View.VISIBLE);
              progressBar.setVisibility(View.GONE);
              cityTV.setText(response.getString("name") + ", " + response.getJSONObject("sys").getString("country"));
              descTV.setText(response.getJSONArray("weather").getJSONObject(0).getString("main"));
              weatherTV.setText(
                  getWeatherConditionBaseOnWeatherID(
                      response.getJSONArray("weather").getJSONObject(0).getInt("id")
                  )
              );
              humidityTV.setText(response.getJSONObject("main").getInt("humidity") + "%");
              windSpeedTV.setText(response.getJSONObject("wind").getInt("speed") + " m/s");
              degreeTV.setText(
                  response.getJSONObject("main").getInt("temp") + " " + Html.fromHtml("&#8451;")
              );
            } catch (JSONException e) {
              e.printStackTrace();
            }
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

  private String getWeatherConditionBaseOnWeatherID(int weatherId) {
    if (weatherId == 800) {
      return getResources().getString(R.string.wi_forecast_io_clear_day);
    }
    switch ((int) Math.floor(weatherId / 100)) {
      case 2:
        return getResources().getString(R.string.wi_day_thunderstorm);
      case 3:
      case 5:
        return getResources().getString(R.string.wi_day_rain);
      case 6:
        return getResources().getString(R.string.wi_day_snow);
      case 7:
        return getResources().getString(R.string.wi_forecast_io_clear_day);
      case 8:
        return getResources().getString(R.string.wi_day_cloudy);
      default:
        return "";
    }
  }
}
