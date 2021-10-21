package com.shariat.mysuperapp.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.shariat.mysuperapp.Components.WeatherTextView;
import com.shariat.mysuperapp.Data.RequestFetchWeatherData;
import com.shariat.mysuperapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

public class WeatherFragment extends Fragment {

  TextView cityTV, degreeTV, humidityTV, timeTV, descTV, windSpeedTV;
  WeatherTextView weatherTV;
  ImageView windImage, humidityImage, weatherImage;
  ProgressBar progressBar;

  private final String LOG_TAG = this.getClass().getSimpleName();
  private String city;
  private RequestQueue queue;

  public static WeatherFragment newInstance(String city) {
    WeatherFragment fragment = new WeatherFragment();
    Bundle args = new Bundle();
    args.putString("city", city);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.city = getArguments().getString("city");
    if (city == null) {
      this.city = "Tehran";
    }
    queue = Volley.newRequestQueue(getContext());
    requestData(this.city);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState
  ) {
    View view = inflater.inflate(R.layout.weather_card, container, false);
    cityTV = view.findViewById(R.id.city_tv);
//    weatherTV = view.findViewById(R.id.weather_tv);
    weatherImage = view.findViewById(R.id.weather_img);
    degreeTV = view.findViewById(R.id.degree_tv);
    humidityTV = view.findViewById(R.id.humidity_tv);
//    timeTV = view.findViewById(R.id.time_tv);
    descTV = view.findViewById(R.id.desc_tv);
    windSpeedTV = view.findViewById(R.id.wind_speed_tv);
    windImage = view.findViewById(R.id.wind_img);
    humidityImage = view.findViewById(R.id.humidity_img);
    progressBar = view.findViewById(R.id.progress_bar);
    return view;
  }

  private void setData(JSONObject response) {
    try {
      cityTV.setText(response.getString("name") + ", " + response.getJSONObject("sys").getString("country"));
      windSpeedTV.setText(response.getJSONObject("wind").getInt("speed") + " m/s");
      JSONObject weatherCondition = response.getJSONArray("weather").getJSONObject(0);
      JSONObject mainCondition = response.getJSONObject("main");
      descTV.setText(weatherCondition.getString("main"));
//      weatherTV.setWeatherIcon(weatherCondition.getInt("id"));
      new DownloadImageTask(weatherImage).execute(String.format(RequestFetchWeatherData.ICON_URL_FORMAT, weatherCondition.getString("icon")));
      humidityTV.setText(mainCondition.getInt("humidity") + "%");
      degreeTV.setText(mainCondition.getInt("temp") + " " + Html.fromHtml("&#8451;"));
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void requestData(String city) {
    RequestFetchWeatherData request = new RequestFetchWeatherData(
        Request.Method.GET,
        city,
        null,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            windImage.setVisibility(View.VISIBLE);
            humidityImage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            setData(response);
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

  private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
      this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
      String urlDisplay = urls[0];
      Bitmap icon = null;
      try {
        InputStream in = new java.net.URL(urlDisplay).openStream();
        icon = BitmapFactory.decodeStream(in);
      } catch (Exception e) {
        Log.e("Error", e.getMessage());
        e.printStackTrace();
      }
      return icon;
    }

    protected void onPostExecute(Bitmap result) {
      bmImage.setImageBitmap(result);
    }
  }

}
