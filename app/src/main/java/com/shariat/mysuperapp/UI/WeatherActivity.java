package com.shariat.mysuperapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.shariat.mysuperapp.Models.City;
import com.shariat.mysuperapp.R;
import com.shariat.mysuperapp.Utils.CityRVAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {

  private static final String CITIES_BASE_URL = "http://10.0.2.2:8000/api/cities/";
  private ArrayList<City> cityList;
  private RequestQueue queue;
  private CityRVAdapter adapter;
  private RecyclerView cityRecyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_weather);
    cityRecyclerView = findViewById(R.id.cities_recycler_view);
    this.queue = Volley.newRequestQueue(this);
    cityList = new ArrayList<>();
    requestCitiesData();
    adapter = new CityRVAdapter(cityList, this);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    cityRecyclerView.setLayoutManager(linearLayoutManager);
    cityRecyclerView.setAdapter(adapter);
  }

  private void requestCitiesData() {
    String url = CITIES_BASE_URL + "all";
    JsonArrayRequest request = new JsonArrayRequest(
        Request.Method.GET,
        url,
        null,
        new Response.Listener<JSONArray>() {
          @Override
          public void onResponse(JSONArray response) {
            Log.i("cities_response2", response.toString());
            for (int i = 0; i < response.length(); i++) {
              try {
                JSONObject obj = response.getJSONObject(i);
                cityList.add(City.convertJsonObjectToCity(obj));
                adapter.notifyDataSetChanged();
              } catch (JSONException e) {
                e.printStackTrace();
              }
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            Log.i("cities_error", error.getMessage());
          }
        });
    queue.add(request);
  }
}